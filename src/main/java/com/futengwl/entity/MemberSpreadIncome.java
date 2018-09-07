/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: La/CCcS2h+HjdSe7kVSxmEZHl4YiZOq9
 */
package com.futengwl.entity;

/**
 * Entity - 购物车项
 *
 * @author FTSHOP Team
 * @version 6.0
 */

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @title: memberSpreadIncome
 * @package: com.futengwl.entity
 * @description:
 * @author: hanbin
 * @date: 2018-06-04  9:11
 */
@Entity
public class MemberSpreadIncome extends BaseEntity<Long> {


    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_sn")
    private String orderSn;
    @Column(name = "sku_id")
    private Long skuId;
    @Column(name = "sku_name")
    private String skuName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "fee")
    private BigDecimal fee;
    @Column(name = "fee_amount")
    private BigDecimal feeAmount;

    @Column(name = "status", columnDefinition = "INT default 1")
    private Integer status;

    @Column(name = "recommender_id")
    private Long recommenderId;
    @Transient
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRecommenderId() {
        return recommenderId;
    }

    public void setRecommenderId(Long recommenderId) {
        this.recommenderId = recommenderId;
    }

}

