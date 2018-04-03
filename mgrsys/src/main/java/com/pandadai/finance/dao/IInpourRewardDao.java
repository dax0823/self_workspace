package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourRewardCusVO;
import com.pandadai.finance.vo.InpourRewardVO;

public interface IInpourRewardDao {
	/**
	 * 查询客户的下线奖励记录
	 * 由于数据库充值的充值记录（LZH_MEMBER_PAYONLINE）与奖励记录（lzh_member_moneylog）分别存在了不同的表中
	 * ，而这两个表的记录又没有规范的关联字段可以参考 所以目前只能以充值记录表为准，查得结果后，将充值金额乘以汇率，得到奖励记录
	 * 
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<InpourRewardCusVO> queryCusRewardList(String id, String startDate,
			String endDate);

	/**
	 * 查询客户当月的下线充值奖励明细
	 * 
	 * @param id
	 * @return
	 */
	List<InpourRewardCusVO> queryCusRewardList(String id);

	/**
	 * 查询所有客户每月的下线充值奖励金额信息（默认搜索最近三个月内数据）
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<InpourRewardVO> queryRewardList(String startDate, String endDate);
}
