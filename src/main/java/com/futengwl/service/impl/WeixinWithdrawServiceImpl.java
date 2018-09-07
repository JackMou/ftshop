/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 29bbKqbpd0NJoQgWMSyeahiCF1vBeQSv
 */
package com.futengwl.service.impl;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.dao.MemberDao;
import com.futengwl.dao.UserAccountDao;
import com.futengwl.dao.WeixinWithdrawDao;
import com.futengwl.entity.Member;
import com.futengwl.entity.SocialUser;
import com.futengwl.entity.UserAccount;
import com.futengwl.entity.WithdrawRecord;
import com.futengwl.pay.ConstantUtils;
import com.futengwl.pay.WeixinClientUtil;
import com.futengwl.service.UserAccountService;
import com.futengwl.service.WeixinWithdrawService;
import com.futengwl.util.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * Service - H5用户提现
 *
 * @author FTSHOP Team
 * @version 6.0
 */
@Service
public class WeixinWithdrawServiceImpl extends BaseServiceImpl<WithdrawRecord, Long> implements WeixinWithdrawService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private UserAccountDao userAccountDao;
    @Inject
    private UserAccountService userAccountService;

    @Inject
    private MemberDao memberDao;
    @Inject
    private WeixinWithdrawDao weixinWithdrawDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Map<String, String> doWithdraw(Map<String, String> map) throws Exception {
        Map<String, String> returnMap = new HashMap();
        Long userId =  Long.valueOf(map.get("userId"));
        logger.info("用户提现service开始" + map.toString());
        //根据用户查询账户余额判断是否够提
        UserAccount userAccount = userAccountDao.find("userId",userId);
        if (userAccount == null || userAccount.getBalance() == null || userAccount.getBalance().compareTo(new BigDecimal(0)) == 0) {
            returnMap.put("1", "账户余额不足！");
            return returnMap;
        }
        BigDecimal standard=new BigDecimal(100);
        BigDecimal widthdrawAmount = userAccount.getBalance().divideToIntegralValue(standard).multiply(standard); //只能整百提现
        if (userAccount.getBalance().compareTo(new BigDecimal(100)) < 0) {
            returnMap.put("1", "余额必须大于100才能提现！");
            return returnMap;
        }
        if (widthdrawAmount.compareTo(new BigDecimal(100)) < 0) {
            returnMap.put("1", "提现金额必须大于100！");
            return returnMap;
        }
        if (userAccount.getBalance().compareTo(widthdrawAmount) < 0) {
            returnMap.put("1", "提现金额不能大于账户余额！");
            return returnMap;
        }

        //查询用户信息
        Member member = memberDao.find(userId);
//        Set socialUsers = member.getSocialUsers();
        String openId = String.valueOf(map.get("openId"));;
//        if (socialUsers != null && socialUsers.size() > 0) {
//            Iterator iterator = socialUsers.iterator();
//            while (iterator.hasNext()) {
//                SocialUser socialUser = (SocialUser) iterator.next();
//                openId = socialUser.getUniqueId();
//            }
//        }
        logger.info("用户提现service  openId:" + openId);

        //新增提现记录
        WithdrawRecord withdrawRecord = new WithdrawRecord();
        withdrawRecord.setOpenId(openId);
        withdrawRecord.setUserId(userId);
        withdrawRecord.setPreAmount(userAccount.getBalance());
        withdrawRecord.setAmout(widthdrawAmount);
        withdrawRecord.setName(member.getName());
        withdrawRecord.setUserName(member.getUsername());

        //修改用户余额
        userAccount.setBalance(userAccount.getBalance().subtract(widthdrawAmount));
        userAccountService.update(userAccount);
        //调用微信提现接口
        int transferFee = widthdrawAmount.multiply(new BigDecimal(100d)).intValue();
        String ip = "127.0.0.1";
        String WithdrawNo = UUIDUtils.getId();
        withdrawRecord.setWithdrawNo(WithdrawNo);
        Map<String, String> xmlMap = WeixinClientUtil.sendTransfersRequest(transferFee, WithdrawNo,
                "美吖吖用户提现"+member.getUsername()+member.getName() , openId, member.getUsername()+member.getName(), ip);
        logger.info("用户提现service调用微信提现接口" + xmlMap.toString());
        if (xmlMap != null && ConstantUtils.SUCCESS.equals(xmlMap.get("return_code")) && ConstantUtils.SUCCESS.equals(xmlMap.get("result_code"))) {
            returnMap.put("0", "提现成功！");
            withdrawRecord.setStatus("2");
            super.save(withdrawRecord);
        } else {
            returnMap.put("1", "提现失败！");
        }
        logger.info("用户提现service结束" + returnMap.toString());
        return returnMap;
    }

    @Override
    public List<WithdrawRecord> queryWithdrawRecordList(Map map){
        List<WithdrawRecord> withdrawRecordList = weixinWithdrawDao.queryWithdrawRecordList(map);
        return withdrawRecordList;
    }


    @Override
    public Page<WithdrawRecord> withdrawRecord(String beginTime, String endTime, Pageable pageable) {
        return weixinWithdrawDao.withdrawRecord(beginTime, endTime, pageable);
    }

    @Override
    public BigDecimal sumWithdrawRecord(String beginTime, String endTime) {
        return weixinWithdrawDao.sumWithdrawRecord( beginTime,  endTime);
    }
}