/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InvestHomeDaoImpl;
import com.pandadai.finance.vo.InvestHomeDetailVO;
import com.pandadai.finance.vo.InvestHomeSumInterestVO;
import com.pandadai.finance.vo.InvestHomeVO;

/**
 * @author 仵作
 * 2014-8-31 下午9:11:00
 */
public class InvestHomeHandler {
	/**
	 * 配置注入
	 */
	private InvestHomeDaoImpl investHomeImpl;

	/**
	 * @param investHomeImpl the investHomeImpl to set
	 */
	public void setInvestHomeImpl(InvestHomeDaoImpl investHomeImpl) {
		this.investHomeImpl = investHomeImpl;
	}

	/**
	 * 查询投资总览页列表
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InvestHomeVO> queryInvestHomeList(String startDate, String endDate) throws Exception {
		return investHomeImpl.queryInvestHomeList(startDate, endDate);
	}
	
	/**
	 * 查询某个投标的明细信息
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InvestHomeDetailVO> queryInvestHomeDetailByBorrowId(String borrowId) throws Exception {
		return investHomeImpl.queryInvestHomeDetailByBorrowId(borrowId);
	}
	
	/**
	 * 列出某个投标中需要返还每个人利息的情况
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public List<InvestHomeSumInterestVO> queryInvestHomeSumInterestByBorrorwId(String borrowId) throws Exception {
		return investHomeImpl.queryInvestHomeSumInterestByBorrorwId(borrowId);
	}
}
