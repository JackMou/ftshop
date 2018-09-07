/*
 * Copyright 2008-2018 futengwl.com. All rights reserved.
 * Support: http://www.futengwl.com
 * License: http://www.futengwl.com/license
 * FileId: 9Ke3EKEdrvoMmqxDHtn764YbzK1qis3X
 */
package com.futengwl.dao;

import com.futengwl.Page;
import com.futengwl.Pageable;
import com.futengwl.entity.DistributionCommission;
import com.futengwl.entity.Distributor;

/**
 * Dao - 分销佣金
 * 
 * @author FTSHOP Team
 * @version 6.0
 */
public interface DistributionCommissionDao extends BaseDao<DistributionCommission, Long> {

	/**
	 * 查找分销佣金记录分页
	 * 
	 * @param distributor
	 *            分销员
	 * @param pageable
	 *            分页信息
	 * @return 分销佣金记录分页
	 */
	Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable);

}