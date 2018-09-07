/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 1zJGyxrzwxVFjH1te2xbA5z23Vg9TGcH
 */
package com.futengwl.service;

import com.futengwl.audit.AuditorProvider;
import com.futengwl.entity.User;
import com.futengwl.entity.UserAccount;
import org.apache.shiro.authc.AuthenticationToken;

import javax.persistence.LockModeType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Service - 用户账户
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface UserAccountService extends BaseService<UserAccount, Long> {


    /**
     * 获取用户账户信息
     * @param map
     * @return
     */
    UserAccount getUserAccount(Map map);

    /**
     * 更新用户账户信息
     * @param map
     * @return
     */
    UserAccount updateUserAccount(Long userId,BigDecimal amount);
}