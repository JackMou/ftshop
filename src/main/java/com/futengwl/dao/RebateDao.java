package com.futengwl.dao;

import java.util.Date;
import java.util.List;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.Rebate;

/**
 * 返利
 * @author Coco
 *
 */
public interface RebateDao extends BaseDao<Rebate, Long> {
	/**
	 * 检查是否购买3980
	 * @param telephone
	 * @return
	 */
	Long checkIsbuy(String telephone);
	
	
	
	
	
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
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	Page<Rebate> findPage(Date beginDate, Date endDate, Pageable pageable);
	
}
