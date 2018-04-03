package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InpourRewardDaoImpl;
import com.pandadai.finance.vo.InpourRewardCusVO;
import com.pandadai.finance.vo.InpourRewardVO;

/**
 * 
 * @author 仵作
 *
 */
public class InpourRewardHandler {

	/**
	 * 配置注入
	 */
	private InpourRewardDaoImpl inpourRewardImpl;
	
	public void setInpourRewardImpl(InpourRewardDaoImpl inpourRewardImpl) {
		this.inpourRewardImpl = inpourRewardImpl;
	}

	/**
	 * 获取所有客户的奖励记录（默认为最近三个月内数据）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourRewardVO> queryRewardList(String startDate, String endDate) throws Exception {
		return inpourRewardImpl.queryRewardList(startDate, endDate);
	} 
	
	/**
	 * 查询客户的线下充值奖励记录
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourRewardCusVO> queryCusRewardList(String id, String startDate, String endDate) throws Exception {
		return inpourRewardImpl.queryCusRewardList(id);
	} 
}
