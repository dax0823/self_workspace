/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InvestRemindDaoImpl;
import com.pandadai.finance.vo.InvestRemindCapitalDetailVO;
import com.pandadai.finance.vo.InvestRemindCapitalVO;
import com.pandadai.finance.vo.InvestRemindDetailCustomerInfoVO;
import com.pandadai.finance.vo.InvestRemindInterestDetailVO;
import com.pandadai.finance.vo.InvestRemindInterestVO;
import com.pandadai.finance.vo.InvestRemindVO;

/**
 * @author 仵作
 * 2014-9-7 上午9:55:01
 */
public class InvestRemindHandler {

	/**
	 * 配置注入
	 */
	private InvestRemindDaoImpl investRemindImpl;
	
	/**
	 * @param investRemindImpl the investRemindImpl to set
	 */
	public void setInvestRemindImpl(InvestRemindDaoImpl investRemindImpl) {
		this.investRemindImpl = investRemindImpl;
	}

	/**
	 * 查询某月利息日历提醒
	 * @return
	 * @throws Exception
	 */
	public List<InvestRemindVO> queryRemindList(String date) throws Exception {
		return investRemindImpl.queryRemindList(date);
	}

	/**
	 * 查询某天内所有投标的本金回款信息列表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<InvestRemindCapitalVO> queryRemindBorrowCapitalOneDayList(String date) throws Exception {
		return investRemindImpl.queryRemindBorrowCapitalOneDayList(date);
	}

	/**
	 * 查询某天内所有投标的利息回款信息列表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<InvestRemindInterestVO> queryRemindBorrowInterestOneDayList(String date) throws Exception {
		return investRemindImpl.queryRemindBorrowInterestOneDayList(date);
	}
	
	/**
	 * 查询某天内所有投标的本金回款明细信息列表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<InvestRemindCapitalDetailVO> queryRemindBorrowCapitalDetailOneDayList(String date, String borrowId) throws Exception {
		return investRemindImpl.queryRemindBorrowCapitalDetailOneDayList(date, borrowId);
	}

	/**
	 * 查询某天内所有投标的利息回款明细信息列表
	 * @param date
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public List<InvestRemindInterestDetailVO> queryRemindBorrowInterestDetailOneDayList(String date, String borrowId) throws Exception {
		return investRemindImpl.queryRemindBorrowInterestDetailOneDayList(date, borrowId);
	}
	
	/**
	 * 查询某天内投标回款明细中客户详细信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public InvestRemindDetailCustomerInfoVO queryRemindBorrowDetailCustomerInfo(String userId) throws Exception {
		return investRemindImpl.queryRemindBorrowDetailCustomerInfo(userId);
	}
}
