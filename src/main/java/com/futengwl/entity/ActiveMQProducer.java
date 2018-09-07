/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: +IQbekxFSFkXVTU1HJwCmZT/+lVpb0XI
 */
package com.futengwl.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.futengwl.BaseAttributeConverter;
import com.futengwl.BigDecimalNumericFieldBridge;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Entity - activemq生产数据
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
@Entity
public class ActiveMQProducer extends BaseEntity<Long> {

	private static final long serialVersionUID = -6977025562650112419L;

    @Column(nullable = false)
	private String orderSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}