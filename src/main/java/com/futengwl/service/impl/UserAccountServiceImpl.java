/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 29bbKqbpd0NJoQgWMSyeahiCF1vBeQSv
 */
package com.futengwl.service.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import com.futengwl.Setting;
import com.futengwl.dao.UserAccountDao;
import com.futengwl.dao.UserDao;
import com.futengwl.entity.Member;
import com.futengwl.entity.User;
import com.futengwl.entity.UserAccount;
import com.futengwl.event.UserLoggedInEvent;
import com.futengwl.event.UserLoggedOutEvent;
import com.futengwl.event.UserRegisteredEvent;
import com.futengwl.httpClient.HttpClientOperate;
import com.futengwl.security.AuthenticationProvider;
import com.futengwl.security.SocialUserAuthenticationToken;
import com.futengwl.security.UserAuthenticationToken;
import com.futengwl.service.AuthCodeService;
import com.futengwl.service.UserAccountService;
import com.futengwl.service.UserService;
import com.futengwl.util.SystemUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service - 用户账户
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, Long> implements UserAccountService {


    @Inject
    private UserAccountDao userAccountDao;

    @Override
    public UserAccount getUserAccount(Map map) {
        return userAccountDao.find("userId",map.get("userId"));
    }

    @Override
    @Transactional
    public synchronized UserAccount updateUserAccount(Long userId,BigDecimal amount) {
        UserAccount userAccount = userAccountDao.find("userId",userId);
        if(null==userAccount){
            userAccount = new UserAccount();
            userAccount.setUserId(userId);
            userAccount.setBalance(amount);
            return super.save(userAccount);
        }else{
            BigDecimal balance = userAccount.getBalance().add(amount);
            userAccount.setBalance(balance);
            return super.update(userAccount);
        }

    }
}