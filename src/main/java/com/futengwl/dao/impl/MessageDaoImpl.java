/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: l2T6meg4fXo5wDwW90l3E+TbwK6eZO8e
 */
package com.futengwl.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.futengwl.dao.MessageDao;
import com.futengwl.entity.Message;
import com.futengwl.entity.MessageGroup;
import com.futengwl.entity.User;

/**
 * Dao - 消息
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, Long> implements MessageDao {

	@Override
	public List<Message> findList(MessageGroup messageGroup, User user) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> root = criteriaQuery.from(Message.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (messageGroup != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("messageGroup"), messageGroup));
		}
		if (user != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("fromUser"), user), criteriaBuilder.equal(root.get("fromUserMessageStatus").get("isDeleted"), false)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("toUser"), user), criteriaBuilder.equal(root.get("toUserMessageStatus").get("isDeleted"), false))));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
		return super.findList(criteriaQuery);
	}

	@Override
	public Long unreadMessageCount(MessageGroup messageGroup, User user) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> root = criteriaQuery.from(Message.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (messageGroup != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("messageGroup"), messageGroup));
		}
		if (user != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("toUser"), user));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("toUserMessageStatus").get("isRead"), false), criteriaBuilder.equal(root.get("toUserMessageStatus").get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}