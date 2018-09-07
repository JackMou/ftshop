/**
 * Copyright © 2012, hsclyn Co., LTD
 *
 * All Rights Reserved.
 */
package com.futengwl.pay.vo;

import java.io.Serializable;

/**
 * Description		: Json header类
 *
 */
public class Header implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private String statusCode = "000";
	private String statusCode = "1";
	private String msg = "操作成功";
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}