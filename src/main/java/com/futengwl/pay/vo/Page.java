/**
 * Copyright © 2012, hsclyn Co., LTD
 *
 * All Rights Reserved.
 */
package com.futengwl.pay.vo;

import java.io.Serializable;

/**
 * Description		: 分页类
 *
 */
public class Page implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long pagination = 1L;	//当前页码
	private long quantity = 10L;	//每页显示数据条数
	private Long recCount = null;		//总记录数
	private Long pageCount = null;		//总页数
	
	public Page() {
		super();
	}
	
	public Page(long pagination, long quantity) {
		super();
		this.pagination = pagination;
		this.quantity = quantity;
	}
	
	public long getPagination() {
		return pagination;
	}
	public void setPagination(long pagination) {
		this.pagination = pagination;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public void setRecCount(Long recCount) {
		this.recCount = recCount;
	}
	/**
	 * 获取总记录数
	 * @return
	 */
	public Long getRecCount() {
		return recCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * 获取总页数
	 * @return
	 */
	public Long getPageCount() {
		if(this.getRecCount() != null) {
			pageCount = this.getRecCount()/this.getQuantity() + (this.getRecCount()%this.getQuantity()==0?0:1);
		}
		return pageCount;
	}
	/**
	 * 获取第一条数据的序号
	 * @return
	 */
	public long getFirstIndex() {
		return (pagination-1) * quantity;
	}
	
//	public static void main(String[] args) {
//		Page page = new Page(1, 10);
//		page.setRecNum(93L);
//		System.out.println(page.getRecNum()/page.getQuantity());
//		System.out.println(page.getRecNum()%page.getQuantity());
//		System.out.println(page.getFirstIndex()+"  "+page.getPages());
//	}
	
}