/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InpourContinueDaoImpl;
import com.pandadai.finance.vo.InpourContinueVO;


/**
 * @author 仵作
 * 2014-8-31 上午11:59:39
 */
public class InpourContinueHandler {

	/**
	 * 配置注入
	 */
	private InpourContinueDaoImpl inpourContinueImpl;
	
	public void setInpourContinueImpl(InpourContinueDaoImpl inpourContinueImpl) {
		this.inpourContinueImpl = inpourContinueImpl;
	}
	
	/**
	 * 获取续投奖励记录（默认为最近一个月内数据）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourContinueVO> queryContinueList(String startDate, String endDate) throws Exception {
		return inpourContinueImpl.queryContinueList(startDate, endDate);
	} 
}
