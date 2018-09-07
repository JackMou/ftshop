/**
 * Copyright © 2012, hsclyn Co., LTD
 *
 * All Rights Reserved.
 */
package com.futengwl.pay.vo;

/**
 * 
 * @author hcl
 *
 */
public class StateUtil {
	
	/**
	 * 标识数据状态 - 启用
	 */
	public final static int STATE_ENABLE = 1;
	/**
	 * 标识数据状态 - 停用
	 */
	public final static int STATE_DISABLE = 2;
	/**
	 * 标识数据状态 - 删除
	 */
	public final static int STATE_DELETED = 3;
	
	
	/**
	 * 标识树行结构根节点ID
	 */
	public final static Long ROOT_ID = 0L;
	
	
	/**
	 * 标识树性别 - 男
	 */
	public final static int GENDER_MALE = 1;
	/**
	 * 标识树性别 - 女
	 */
	public final static int GENDER_FEMALE = 2;
	
	
	/**
	 * 商品分类类型 - 分类
	 */
	public final static int MERCHANDISE_CATEGORY = 1;
	/**
	 * 商品分类类型 - 品牌
	 */
	public final static int MERCHANDISE_BRAND = 2;
	
	
	/**
	 * 标识属性类别 - 基本属性
	 */
	public final static int PROP_BASE = 1;
	/**
	 * 标识属性类别 - 销售属性
	 */
	public final static int PROP_SALE = 2;
	
	
	/**
	 * 标识属性显示方式 - text
	 */
	public final static int PROP_SHOW_TEXT = 1;
	/**
	 * 标识属性显示方式 - radio
	 */
	public final static int PROP_SHOW_RADIO = 2;
	/**
	 * 标识属性显示方式 - checkbox
	 */
	public final static int PROP_SHOW_CHECKBOX = 3;
	/**
	 * 标识属性显示方式 - select
	 */
	public final static int PROP_SHOW_SELECT = 4;
	/**
	 * 标识属性显示方式 - selectbox
	 */
	public final static int PROP_SHOW_SELECTBOX = 5;
	
	
	/**
	 * 标识属性值类型 - 标准值
	 */
	public final static int PROP_VALUE_BASE = 1;
	/**
	 * 标识属性值类型 - 用户自定义值
	 */
	public final static int PROP_VALUE_EXTENSION = 2;
	
	
	/**
	 * 标识订单状态- 待支付
	 */
	public final static int ORDER_WAITPAY = 1;
	/**
	 * 标识订单状态 - 关闭
	 */
	public final static int ORDER_CLOSED = 0;
	/**
	 * 标识订单状态- 待审批
	 */
	public final static int ORDER_WAITAPPROVAL = 7;
	/**
	 * 标识订单状态- 待退款
	 */
	public final static int ORDER_WAITREFUND = 8;
	/**
	 * 标识订单状态- 待发货
	 */
	public final static int ORDER_WAITPOST = 2;
	/**
	 * 标识订单状态- 待签收
	 */
	public final static int ORDER_WAITSIGN = 3;
	/**
	 * 标识订单状态 - 完成交易/待评价
	 */
	public final static int ORDER_COMPLETED = 4;
	/**
	 * 标识订单状态 - 待退货
	 */
	public final static int ORDER_WAIT_RETURN = 6;
	/**
	 * 标识订单状态- 已退款
	 */
	public final static int ORDER_REFUNDED = 9;
	/**
	 * 标识订单状态 – 已归档
	 */
	public final static int ORDER_END = 5;
	
	
	/**
	 * 广告类型 - 美啊广告
	 */
	public static final String ADS_TYPE_MEIA = "1";
	/**
	 * 广告类型 - 美购广告
	 */
	public static final String ADS_TYPE_MEIG = "2";
	/**
	 * 广告类型 - 专区广告
	 */
	public static final String ADS_TYPE_AREA = "3";
	
	
	/**
	 * 商品 - 待审核
	 */
	public static final String ITEM_ADU_WAIT = "6";
	/**
	 * 商品 - 审核不通过
	 */
	public static final String ITEM_ADU_REJE = "7";
	/**
	 * 标识商品状态- 在售/已上架
	 */
	public final static int MERCHANDISE_STATE_SELLING = 1;
	/**
	 * 标识商品状态- 待售/已下架
	 */
	public final static int MERCHANDISE_STATE_WAITING = 2;
	/**
	 * 标识商品状态- 删除
	 */
	public final static int MERCHANDISE_STATE_DELETED = 0;
	
	
	/**
	 * 标识用户类型
	 */
	public final static Long TYPE_USER = 1L;
	
	public final static Long TYPE_SHOP = 2L;
	
	
	/**
	 * 支付方式--微信支付
	 */
	public final static String PAYMENT_WEIXIN = "3";
	
	/**
	 * 支付方式--支付宝支付
	 */
	public final static String PAYMENT_ALIPAY = "4";
	
	/**
	 * 支付方式--银联支付
	 */
	public final static String PAYMENT_YINLIAN = "5";
	
	/**
	 * header statusCode
	 */
	public static final String STATUS_CODE_TRUE = "1";// 正确
	
	public static final String STATUS_CODE_FALSE = "0";// 错误
	
	//退货订单状态
	/**
	 * 新建退货订单
	 */
	public final static String RETURN_ORDER_NEW = "00";
	
	/**
	 * 已提交 / 审核中
	 */
	public final static String RETURN_ORDER_WAITAPPROVAL = "01";
	
	/**
	 * 审核通过，等待退货
	 */
	public final static String RETURN_ORDER_WAITRETURN = "02";
	
	/**
	 * 已发货 / 退货中
	 */
	public final static String RETURN_ORDER_RETURNING = "03";
	
	/**
	 * 已收货 / 退款中
	 */
	public final static String RETURN_ORDER_WAITREFUND = "04";
	
	/**
	 * 已退款
	 */
	public final static String RETURN_ORDER_REFUNDED = "05";
	
	/**
	 * 审核不通过
	 */
	public final static String RETURN_ORDER_DISAPPROVED = "06";
	
	/**
	 * 已完成
	 */
	public final static String RETURN_ORDER_COMPLETED = "07";
	
	//退货运费承担
	/**
	 * 运费自理（客户承担）
	 */
	public final static String RETURN_SHIPMENT_FEE_PAYBYCUSTOMER = "01";
	/**
	 * 免运费（商家承担）
	 */
	public final static String RETURN_SHIPMENT_FEE_PAYBYSELLER = "02";
	
	/**
	 * 支付状态(0：未支付；1：已支付)
	 */
	public final static String PAY_STATUS_WZF = "0";
	/**
	 * 支付状态(0：未支付；1：已支付)
	 */
	public final static String PAY_STATUS_YZF = "1";
}
