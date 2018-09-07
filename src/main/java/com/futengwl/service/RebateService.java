package com.futengwl.service;

import java.util.Date;
import java.util.List;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Rebate;

public interface RebateService extends BaseService<Rebate, Long> {
	/**
	 * 检查是否购买3980
	 * @param telephone
	 * @return
	 */
	Long checkIsBuy(String telephone);
	
	/**
	 * 返利推荐信息列表
	 * @param beginDate
	 * @param endDate
	 * @param mobile
	 * @param linkmanTelephone
	 * @return
	 */
	List<Rebate> findRebate(Date beginDate, Date endDate, String mobile, String linkmanTelephone);
	
	
	/**
	 * 检查餐厅联系人是否存在
	 * @param linkmanTelephone
	 * @return
	 */
	boolean checkLinkman(String linkmanTelephone);
	
	/**
	 * 协议会员分页列表
	 * 
	 * @param beginDate
	 * 			起始日期
	 * @param endDate
	 * 			截止日期
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<Rebate> findPage(Date beginDate, Date endDate, Pageable pageable);

	
}
