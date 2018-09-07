/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 29bbKqbpd0NJoQgWMSyeahiCF1vBeQSv
 */
package com.futengwl.service.impl;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.LockModeType;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import com.futengwl.entity.Member;
import com.futengwl.httpClient.HttpClientOperate;
import com.futengwl.httpClient.HttpResult;
import com.futengwl.service.AuthCodeService;
import com.futengwl.service.IUserService;
import com.futengwl.util.SpringContextUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.codehaus.groovy.util.HashCodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import com.futengwl.Setting;
import com.futengwl.dao.UserDao;
import com.futengwl.entity.User;
import com.futengwl.event.UserLoggedInEvent;
import com.futengwl.event.UserLoggedOutEvent;
import com.futengwl.event.UserRegisteredEvent;
import com.futengwl.security.AuthenticationProvider;
import com.futengwl.security.SocialUserAuthenticationToken;
import com.futengwl.security.UserAuthenticationToken;
import com.futengwl.service.UserService;
import com.futengwl.util.SystemUtils;

/**
 * Service - 用户
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	/**
	 * 认证Provider缓存
	 */
	private static final Map<Class<?>, AuthenticationProvider> AUTHENTICATION_PROVIDER_CACHE = new ConcurrentHashMap<>();

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;
	@Inject
	private List<AuthenticationProvider> authenticationProviders;
	@Inject
	private CacheManager cacheManager;
	@Inject
	private UserDao userDao;
	@Inject
	private AuthCodeService authCodeService;

	@Override
	@Transactional(readOnly = true)
	public User getCurrentAuditor() {
		return getCurrent();
	}

	@Override
	@Transactional(noRollbackFor = AuthenticationException.class)
	public User getUser(AuthenticationToken authenticationToken) {
		Assert.notNull(authenticationToken, "[Assertion failed] - authenticationToken is required; it must not be null");
		Assert.state(authenticationToken instanceof UserAuthenticationToken || authenticationToken instanceof SocialUserAuthenticationToken, "[Assertion failed] - authenticationToken must be instanceof UserAuthenticationToken or SocialUserAuthenticationToken");

		User user = null;
		if (authenticationToken instanceof UserAuthenticationToken) {
			UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authenticationToken;
			//userAuthenticationToken.setRememberMe(true);
			Class<?> userClass = userAuthenticationToken.getUserClass();
			Object principal = userAuthenticationToken.getPrincipal();

			if (userClass == null || principal == null) {
				throw new UnknownAccountException();
			}
			AuthenticationProvider authenticationProvider = getAuthenticationProvider(userClass);
			user = authenticationProvider != null ? authenticationProvider.getUser(principal) : null;
		} else if (authenticationToken instanceof SocialUserAuthenticationToken) {
			SocialUserAuthenticationToken socialUserAuthenticationToken = (SocialUserAuthenticationToken) authenticationToken;
			user = socialUserAuthenticationToken.getSocialUser() != null ? socialUserAuthenticationToken.getSocialUser().getUser() : null;
		}

		if (user == null) {
			throw new UnknownAccountException();
		}
		if (BooleanUtils.isNotTrue(user.getIsEnabled())) {
			throw new DisabledAccountException();
		}
		if (BooleanUtils.isTrue(user.getIsLocked()) && !tryUnlock(user)) {
			throw new LockedAccountException();
		}
		if (authenticationToken instanceof UserAuthenticationToken) {
			Object credentials = authenticationToken.getCredentials();

			//if (!user.isValidCredentials(credentials)) {
            if(((UserAuthenticationToken) authenticationToken).getUserClass().equals(Member.class)) {
                //验证短信验证码是否正确
                String authCode = credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials);
                if (!authCodeService.isValidAuthCode(String.valueOf(user.getPrincipal()), "register", authCode)) {
                    addFailedLoginAttempt(user);
                    tryLock(user);
                    throw new IncorrectCredentialsException();
                }
            }else{
                if (!user.isValidCredentials(credentials)) {
                    addFailedLoginAttempt(user);
                    tryLock(user);
                    throw new IncorrectCredentialsException();
                }
            }
		}
		if (authenticationToken instanceof HostAuthenticationToken) {
			HostAuthenticationToken hostAuthenticationToken = (HostAuthenticationToken) authenticationToken;
			user.setLastLoginIp(hostAuthenticationToken.getHost());
		}
		user.setLastLoginDate(new Date());
		resetFailedLoginAttempts(user);
		return user;
	}
    @Override
	@Transactional(noRollbackFor = AuthenticationException.class)
	public User getUser(String principal) {

        AuthenticationProvider authenticationProvider = getAuthenticationProvider(Member.class);
        User user = authenticationProvider != null ? authenticationProvider.getUser(principal) : null;

		if (user == null) {
			throw new UnknownAccountException();
		}
		if (BooleanUtils.isNotTrue(user.getIsEnabled())) {
			throw new DisabledAccountException();
		}
		if (BooleanUtils.isTrue(user.getIsLocked()) && !tryUnlock(user)) {
			throw new LockedAccountException();
		}

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<String> getPermissions(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");

		AuthenticationProvider authenticationProvider = getAuthenticationProvider(user.getClass());
		return authenticationProvider != null ? authenticationProvider.getPermissions(user) : null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void register(User user,String bpId) throws IOException {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(user.isNew(), "[Assertion failed] - user must be new");
		userDao.persist(user);
		applicationEventPublisher.publishEvent(new UserRegisteredEvent(this, user));

		//绑定美容院和用户关联关系
		updateRelation(String.valueOf(user.getId()),bpId);
	}
	@Resource
	private HttpClientOperate httpClientOperate;
	@Value("${shop.admin.url}")
	private String shopAdminUrl;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRelation(String userId,String bpId) throws IOException {
		//绑定美容院和用户关联关系
		if(!StringUtils.isEmpty(bpId)&&!"null".equals(bpId)){
			//获取用户保存ID
			Map<String,String> map = new HashMap<>();
			map.put("bpId",bpId);
			map.put("userId",userId);
			String json = com.alibaba.fastjson.JSON.toJSONString(map);
			//有参post请求
			httpClientOperate.doPostJson(shopAdminUrl, json);
		}
	}

	@Override
	public void login(AuthenticationToken authenticationToken) {
		Assert.notNull(authenticationToken, "[Assertion failed] - authenticationToken is required; it must not be null");

		Subject subject = SecurityUtils.getSubject();
		subject.login(authenticationToken);

		applicationEventPublisher.publishEvent(new UserLoggedInEvent(this, getCurrent()));
	}

	@Override
	public void logout() {
		applicationEventPublisher.publishEvent(new UserLoggedOutEvent(this, getCurrent()));

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	@Override
	@Transactional(readOnly = true)
	public User getCurrent() {
		return getCurrent(null, null);
	}

	@Override
	@Transactional(readOnly = true)
	public <T extends User> T getCurrent(Class<T> userClass) {
		return getCurrent(userClass, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <T extends User> T getCurrent(Class<T> userClass, LockModeType lockModeType) {
		Subject subject = SecurityUtils.getSubject();
		User principal = subject != null && subject.getPrincipal() instanceof User ? (User) subject.getPrincipal() : null;
		if (principal != null) {
			User user = userDao.find(principal.getId(), lockModeType);
			if (userClass == null || (user != null && userClass.isAssignableFrom(user.getClass()))) {
				return (T) user;
			}
        }
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public int getFailedLoginAttempts(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(!user.isNew(), "[Assertion failed] - user must not be new");

		Ehcache cache = cacheManager.getEhcache(User.FAILED_LOGIN_ATTEMPTS_CACHE_NAME);
		Element element = cache.get(user.getId());
		AtomicInteger failedLoginAttempts = element != null ? (AtomicInteger) element.getObjectValue() : null;
		return failedLoginAttempts != null ? failedLoginAttempts.get() : 0;
	}

	@Override
	@Transactional(readOnly = true)
	public void addFailedLoginAttempt(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(!user.isNew(), "[Assertion failed] - user must not be new");

		Long userId = user.getId();
		Ehcache cache = cacheManager.getEhcache(User.FAILED_LOGIN_ATTEMPTS_CACHE_NAME);
		cache.acquireWriteLockOnKey(userId);
		try {
			Element element = cache.get(userId);
			AtomicInteger failedLoginAttempts = element != null ? (AtomicInteger) element.getObjectValue() : null;
			if (failedLoginAttempts != null) {
				failedLoginAttempts.incrementAndGet();
			} else {
				cache.put(new Element(userId, new AtomicInteger(1)));
			}
		} finally {
			cache.releaseWriteLockOnKey(userId);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void resetFailedLoginAttempts(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(!user.isNew(), "[Assertion failed] - user must not be new");

		Ehcache cache = cacheManager.getEhcache(User.FAILED_LOGIN_ATTEMPTS_CACHE_NAME);
		cache.remove(user.getId());
	}

	@Override
	public boolean tryLock(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(!user.isNew(), "[Assertion failed] - user must not be new");

		if (BooleanUtils.isTrue(user.getIsLocked())) {
			return true;
		}

		Setting setting = SystemUtils.getSetting();
		if (setting.getMaxFailedLoginAttempts() != null) {
			int failedLoginAttempts = getFailedLoginAttempts(user);
			if (failedLoginAttempts >= setting.getMaxFailedLoginAttempts()) {
				user.setIsLocked(true);
				user.setLockDate(new Date());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean tryUnlock(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(!user.isNew(), "[Assertion failed] - user must not be new");

		if (BooleanUtils.isFalse(user.getIsLocked())) {
			return true;
		}

		Setting setting = SystemUtils.getSetting();
		if (setting.getPasswordLockTime() != null) {
			Date lockDate = user.getLockDate();
			Date unlockDate = DateUtils.addMinutes(lockDate, setting.getPasswordLockTime());
			if (new Date().after(unlockDate)) {
				unlock(user);
				return true;
			}
		}
		return false;
	}

	@Override
	public void unlock(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isTrue(!user.isNew(), "[Assertion failed] - user must not be new");

		if (BooleanUtils.isFalse(user.getIsLocked())) {
			return;
		}

		user.setIsLocked(false);
		user.setLockDate(null);
		resetFailedLoginAttempts(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User save(User user) {
		return super.save(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User user) {
		return super.update(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User user, String... ignoreProperties) {
		return super.update(user, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(User user) {
		super.delete(user);
	}

	/**
	 * 获取认证Provider
	 * 
	 * @param userClass
	 *            用户类型
	 * @return 认证Provider，若不存在则返回null
	 */
	private AuthenticationProvider getAuthenticationProvider(Class<?> userClass) {
		Assert.notNull(userClass, "[Assertion failed] - userClass is required; it must not be null");

		if (AUTHENTICATION_PROVIDER_CACHE.containsKey(userClass)) {
			return AUTHENTICATION_PROVIDER_CACHE.get(userClass);
		}

		if (CollectionUtils.isNotEmpty(authenticationProviders)) {
			for (AuthenticationProvider authenticationProvider : authenticationProviders) {
				if (authenticationProvider != null && authenticationProvider.supports(userClass)) {
					AUTHENTICATION_PROVIDER_CACHE.put(userClass, authenticationProvider);
					return authenticationProvider;
				}
			}
		}
		return null;
	}

}