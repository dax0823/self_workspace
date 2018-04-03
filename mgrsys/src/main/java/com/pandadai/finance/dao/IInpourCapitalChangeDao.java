package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourCapitalChangeVO;
import com.pandadai.finance.vo.InvestCustomerVO;

/***
 * 
 * @author 仵作
 *
 */
public interface IInpourCapitalChangeDao {
	/**
	 * 查询用户近期的资金变更记录
	 * @param userId
	 * @param realName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourCapitalChangeVO> queryCapitalChangeList() throws Exception;
	
	List<InpourCapitalChangeVO> queryCapitalChangeList(String userName, String realName, String startDate, String endDate) throws Exception;

}
