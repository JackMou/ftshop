/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 3vftCgDfLH9VOkrKvXokQLqkiLrLuIk8
 */
package com.futengwl.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity - 用户账户
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Entity
@Table(name = "useraccount")
@Inheritance
public class UserAccount extends BaseEntity<Long> {

	private static final long serialVersionUID = -4000007477538421L;


	/**
	 * userId
	 */
	@NotNull
	@Column(nullable = false)
	private Long userId;

	/**
	 * 提现前金额
	 */
	@NotNull
	@Column(nullable = false)
	private BigDecimal balance;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


    public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}