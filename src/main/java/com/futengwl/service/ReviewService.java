/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 82pcCGeIvLLMyvdtgW6430YoV5L4ksq8
 */
package com.futengwl.service;

import java.util.List;

import com.futengwl.Filter;
import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Member;
import com.futengwl.entity.Order;
import com.futengwl.entity.Product;
import com.futengwl.entity.Review;
import com.futengwl.entity.Review.Entry;
import com.futengwl.entity.Store;

/**
 * Service - 评论
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface ReviewService extends BaseService<Review, Long> {

	/**
	 * 查找评论
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 评论
	 */
	List<Review> findList(Member member, Product product, Review.Type type, Boolean isShow, Integer count, List<Filter> filters, List<com.futengwl.Order> orders);

	/**
	 * 查找评论
	 * 
	 * @param memberId
	 *            会员ID
	 * @param productId
	 *            商品ID
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 评论
	 */
	List<Review> findList(Long memberId, Long productId, Review.Type type, Boolean isShow, Integer count, List<Filter> filters, List<com.futengwl.Order> orders, boolean useCache);

	/**
	 * 查找评论分页
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 评论分页
	 */
	Page<Review> findPage(Member member, Product product, Store store, Review.Type type, Boolean isShow, Pageable pageable);

	/**
	 * 查找评论数量
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @return 评论数量
	 */
	Long count(Member member, Product product, Review.Type type, Boolean isShow);

	/**
	 * 查找评论数量
	 * 
	 * @param memberId
	 *            会员ID
	 * @param productId
	 *            商品ID
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @return 评论数量
	 */
	Long count(Long memberId, Long productId, Review.Type type, Boolean isShow);

	/**
	 * 评论创建
	 * 
	 * @param order
	 *            订单
	 * @param reviewEntries
	 *            评论条目
	 * @param ip
	 *            ip
	 * @param memebr
	 *            会员
	 */
	void create(Order order, List<Entry> reviewEntries, String ip, Member memebr);

	/**
	 * 评论回复
	 * 
	 * @param review
	 *            评论
	 * @param replyReview
	 *            回复评论
	 */
	void reply(Review review, Review replyReview);

}