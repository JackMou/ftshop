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
 * Entity - 提现记录
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Entity
@Table(name = "withdrawrecord")
@Inheritance
public class WithdrawRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = -4000007477538411L;

	/**
	 * openId
	 */
	@NotNull
	@Column(nullable = false)
	private String openId;

	/**
	 * 提现编号
	 */
	@NotNull
	@Column(nullable = false)
	private String withdrawNo;
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
	private BigDecimal preAmount;
	/**
	 * 提现金额
	 */
	@NotNull
	@Column(nullable = false)
	private BigDecimal amout;
	/**
	 * 状态
	 */
	@NotNull
	@Column(nullable = false)
	private String status;
	/**
	 * 姓名
	 */
	@NotNull
	@Column(nullable = false)
	private String name;
	/**
	 * 手机号
	 */
	@NotNull
	@Column(nullable = false)
	private String userName;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmout() {
		return amout;
	}

	public void setAmout(BigDecimal amout) {
		this.amout = amout;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(BigDecimal preAmount) {
		this.preAmount = preAmount;
	}

	public String getWithdrawNo() {
		return withdrawNo;
	}

	public void setWithdrawNo(String withdrawNo) {
		this.withdrawNo = withdrawNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}