/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InvestRemindCapitalDetailVO;
import com.pandadai.finance.vo.InvestRemindCapitalVO;
import com.pandadai.finance.vo.InvestRemindDetailCustomerInfoVO;
import com.pandadai.finance.vo.InvestRemindInterestDetailVO;
import com.pandadai.finance.vo.InvestRemindInterestVO;
import com.pandadai.finance.vo.InvestRemindVO;

/**
 * @author 仵作
 * 2014-9-7 上午9:45:34
 */
public interface IInvestRemindDao {
	
	/**
	 * 查询某个月内的提醒信息
	 * @param date：YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	List<InvestRemindVO> queryRemindList(String date) throws Exception;

	/**
	 * 查询某天内所有投标的本金回款信息列表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	List<InvestRemindCapitalVO> queryRemindBorrowCapitalOneDayList(String date) throws Exception;

	/**
	 * 查询某天内所有投标的利息回款信息列表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	List<InvestRemindInterestVO> queryRemindBorrowInterestOneDayList(String date) throws Exception;

	/**
	 * 查询某天内所有投标的本金回款明细信息列表
	 * @param date
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	List<InvestRemindCapitalDetailVO> queryRemindBorrowCapitalDetailOneDayList(String date, String borrowId) throws Exception;
	
	/**
	 * 查询某天内所有投标的利息回款明细信息列表
	 * @param date
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	List<InvestRemindInterestDetailVO> queryRemindBorrowInterestDetailOneDayList(String date, String borrowId) throws Exception;
	
	/**
	 * 查询某天内投标回款明细中客户详细信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	InvestRemindDetailCustomerInfoVO queryRemindBorrowDetailCustomerInfo(String userId) throws Exception;
}
