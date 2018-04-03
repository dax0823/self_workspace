package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.TopBorrowVO;
import com.pandadai.finance.vo.TopInpourVO;
import com.pandadai.finance.vo.TopIntegralVO;
import com.pandadai.finance.vo.TopInvestedVO;
import com.pandadai.finance.vo.TopUncollectedVO;
import com.pandadai.finance.vo.TopWithdrawVO;

public interface ITopHomeDao {

	/**
	 * 查询用户总代收排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	List<TopUncollectedVO> queryTopUncollected(int rankNum) throws Exception;
	
	/**
	 * 查询用户累积投资排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	List<TopInvestedVO> queryTopInvested(int rankNum) throws Exception;
	
	/**
	 * 查询用户累积充值排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	List<TopInpourVO> queryTopInpour(int rankNum) throws Exception;
	
	/**
	 * 查询用户累积提现排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	List<TopWithdrawVO> queryTopWithdraw(int rankNum) throws Exception;
	
	/**
	 * 查询用户累积借款排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	List<TopBorrowVO> queryTopBorrow(int rankNum) throws Exception;
	
	/**
	 * 查询用户可用积分排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	List<TopIntegralVO> queryTopIntegral(int rankNum) throws Exception;
	
}
