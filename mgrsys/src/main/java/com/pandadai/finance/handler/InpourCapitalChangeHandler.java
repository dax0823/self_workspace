package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InpourCapitalChangeDaoImpl;
import com.pandadai.finance.vo.InpourCapitalChangeVO;

/***
 * 
 * @author 仵作
 *
 */
public class InpourCapitalChangeHandler {

	/**
	 * 配置注入
	 */
	private InpourCapitalChangeDaoImpl inpourCapitalChangeDaoImpl;
	
	/**
	 * @param inpourCapitalChangeDaoImpl the inpourCapitalChangeDaoImpl to set
	 */
	public void setInpourCapitalChangeDaoImpl(
			InpourCapitalChangeDaoImpl inpourCapitalChangeDaoImpl) {
		this.inpourCapitalChangeDaoImpl = inpourCapitalChangeDaoImpl;
	}

	/**
	 * 查询用户近期资金变更记录
	 * @return
	 * @throws Exception
	 */
	public List<InpourCapitalChangeVO> queryInvestCustomer() throws Exception {
		return inpourCapitalChangeDaoImpl.queryCapitalChangeList();
	}
	
	public List<InpourCapitalChangeVO> queryInvestCustomer(String userName, String realName, String startDate, String endDate) throws Exception {
		return inpourCapitalChangeDaoImpl.queryCapitalChangeList(userName, realName, startDate, endDate);
	}
	
}
