/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: DT5WyIYEtIQRYNj41hTP8GT8ZTQk6fQK
 */
package com.futengwl.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import com.google.gson.JsonObject;
import com.futengwl.util.JsonUtils;
import com.futengwl.util.WebUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.MemberDao;
import com.futengwl.dao.MemberDepositLogDao;
import com.futengwl.dao.MemberRankDao;
import com.futengwl.dao.PointLogDao;
import com.futengwl.entity.Member;
import com.futengwl.entity.MemberDepositLog;
import com.futengwl.entity.MemberRank;
import com.futengwl.entity.PointLog;
import com.futengwl.entity.User;
import com.futengwl.service.MailService;
import com.futengwl.service.MemberService;
import com.futengwl.service.SmsService;

/**
 * Service - 会员
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

    /**
     * E-mail身份配比
     */
    private static final Pattern EMAIL_PRINCIPAL_PATTERN = Pattern.compile(".*@.*");

    /**
     * 手机身份配比
     */
    private static final Pattern MOBILE_PRINCIPAL_PATTERN = Pattern.compile("\\d+");

    @Inject
    private MemberDao memberDao;
    @Inject
    private MemberRankDao memberRankDao;
    @Inject
    private MemberDepositLogDao memberDepositLogDao;
    @Inject
    private PointLogDao pointLogDao;
    @Inject
    private MailService mailService;
    @Inject
    private SmsService smsService;

    @Override
    @Transactional(readOnly = true)
    public Member getUser(Object principal) {
        Assert.notNull(principal, "[Assertion failed] - principal is required; it must not be null");
        Assert.isInstanceOf(String.class, principal);

        String value = String.valueOf(principal);
        if (EMAIL_PRINCIPAL_PATTERN.matcher(value).matches()) {
            return findByEmail(value);
        } else if (MOBILE_PRINCIPAL_PATTERN.matcher(value).matches()) {
            return findByMobile(value);
        } else {
            return findByUsername(value);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getPermissions(User user) {
        Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
        Assert.isInstanceOf(Member.class, user);

        return Member.PERMISSIONS;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean supports(Class<?> userClass) {
        return userClass != null && Member.class.isAssignableFrom(userClass);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean usernameExists(String username) {
        return memberDao.exists("username", StringUtils.lowerCase(username));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean NameExists(String username) {
        Member member = memberDao.find("username", StringUtils.lowerCase(username));
        if (null != member && null != member.getName() && !member.getName().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        return memberDao.find("username", StringUtils.lowerCase(username));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return memberDao.exists("email", StringUtils.lowerCase(email));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailUnique(Long id, String email) {
        return memberDao.unique(id, "email", StringUtils.lowerCase(email));
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberDao.find("email", StringUtils.lowerCase(email));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean mobileExists(String mobile) {
        return memberDao.exists("mobile", StringUtils.lowerCase(mobile));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean mobileUnique(Long id, String mobile) {
        return memberDao.unique(id, "mobile", StringUtils.lowerCase(mobile));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> search(String keyword, Set<Member> excludes, Integer count) {
        return memberDao.search(keyword, excludes, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByMobile(String mobile) {
        return memberDao.find("mobile", StringUtils.lowerCase(mobile));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Member> findPage(Member.RankingType rankingType, Pageable pageable) {
        return memberDao.findPage(rankingType, pageable);
    }

    @Override
    public void addBalance(Member member, BigDecimal amount, MemberDepositLog.Type type, String memo) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
        Assert.notNull(amount, "[Assertion failed] - amount is required; it must not be null");
        Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
            memberDao.flush();
            memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
        }

        Assert.notNull(member.getBalance(), "[Assertion failed] - member balance is required; it must not be null");
        Assert.state(member.getBalance().add(amount).compareTo(BigDecimal.ZERO) >= 0, "[Assertion failed] - member balance must be equal or greater than 0");

        member.setBalance(member.getBalance().add(amount));
        memberDao.flush();

        MemberDepositLog memberDepositLog = new MemberDepositLog();
        memberDepositLog.setType(type);
        memberDepositLog.setCredit(amount.compareTo(BigDecimal.ZERO) > 0 ? amount : BigDecimal.ZERO);
        memberDepositLog.setDebit(amount.compareTo(BigDecimal.ZERO) < 0 ? amount.abs() : BigDecimal.ZERO);
        memberDepositLog.setBalance(member.getBalance());
        memberDepositLog.setMemo(memo);
        memberDepositLog.setMember(member);
        memberDepositLogDao.persist(memberDepositLog);
    }

    @Override
    public void addFrozenAmount(Member member, BigDecimal amount) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
        Assert.notNull(amount, "[Assertion failed] - amount is required; it must not be null");

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
            memberDao.flush();
            memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
        }

        Assert.notNull(member.getFrozenAmount(), "[Assertion failed] - member frozenAmount is required; it must not be null");
        Assert.state(member.getFrozenAmount().add(amount).compareTo(BigDecimal.ZERO) >= 0, "[Assertion failed] - member frozenAmount must be equal or greater than 0");

        member.setFrozenAmount(member.getFrozenAmount().add(amount));
        memberDao.flush();
    }

    @Override
    public void addPoint(Member member, long amount, PointLog.Type type, String memo) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
        Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

        if (amount == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
            memberDao.flush();
            memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
        }

        Assert.notNull(member.getPoint(), "[Assertion failed] - member point is required; it must not be null");
        Assert.state(member.getPoint() + amount >= 0, "[Assertion failed] - member point must be equal or greater than 0");

        member.setPoint(member.getPoint() + amount);
        memberDao.flush();

        PointLog pointLog = new PointLog();
        pointLog.setType(type);
        pointLog.setCredit(amount > 0 ? amount : 0L);
        pointLog.setDebit(amount < 0 ? Math.abs(amount) : 0L);
        pointLog.setBalance(member.getPoint());
        pointLog.setMemo(memo);
        pointLog.setMember(member);
        pointLogDao.persist(pointLog);
    }

    @Override
    public void addAmount(Member member, BigDecimal amount) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
        Assert.notNull(amount, "[Assertion failed] - amount is required; it must not be null");

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
            memberDao.flush();
            memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
        }

        Assert.notNull(member.getAmount(), "[Assertion failed] - member amount is required; it must not be null");
        Assert.state(member.getAmount().add(amount).compareTo(BigDecimal.ZERO) >= 0, "[Assertion failed] - member amount must be equal or greater than 0");

        member.setAmount(member.getAmount().add(amount));
        MemberRank memberRank = member.getMemberRank();
        if (memberRank != null && BooleanUtils.isFalse(memberRank.getIsSpecial())) {
            MemberRank newMemberRank = memberRankDao.findByAmount(member.getAmount());
            if (newMemberRank != null && newMemberRank.getAmount() != null && newMemberRank.getAmount().compareTo(memberRank.getAmount()) > 0) {
                member.setMemberRank(newMemberRank);
            }
        }
        memberDao.flush();
    }

    @Override
    @Transactional
    public Member save(Member member) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");

        Member pMember = super.save(member);
        mailService.sendRegisterMemberMail(pMember);
        smsService.sendRegisterMemberSms(pMember);
        return pMember;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal totalBalance() {
        return memberDao.totalBalance();
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Date beginDate, Date endDate) {
        return memberDao.count(beginDate, endDate);
    }

    @Value("${beauty.querymember.url}")
    String url;

    @Override
    public Map getBeauty(Long id) {
        Map params = new HashMap<String, Object>();
        params.put("memberId", id);
        try {
            String resultJson = WebUtils.get(url, params);
            if (null != resultJson && StringUtils.isNotEmpty(resultJson)) {
                System.out.println(resultJson);
                return JsonUtils.toObject(resultJson, Map.class);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
            throw ex;
        }
        return null;
    }

}