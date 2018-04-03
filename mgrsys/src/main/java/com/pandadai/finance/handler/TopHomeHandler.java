package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.TopHomeDaoImpl;
import com.pandadai.finance.vo.TopBorrowVO;
import com.pandadai.finance.vo.TopInpourVO;
import com.pandadai.finance.vo.TopIntegralVO;
import com.pandadai.finance.vo.TopInvestedVO;
import com.pandadai.finance.vo.TopUncollectedVO;
import com.pandadai.finance.vo.TopWithdrawVO;

public class TopHomeHandler {
	private TopHomeDaoImpl topHomeDaoImpl;
	
	public void setTopHomeDaoImpl(TopHomeDaoImpl topHomeDaoImpl) {
		this.topHomeDaoImpl = topHomeDaoImpl;
	}

	/**
	 * 查询用户总待收排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	public List<TopUncollectedVO> queryTopUncollected(int rankNum) throws Exception {
		return topHomeDaoImpl.queryTopUncollected(rankNum);
	}
	
	/**
	 * 查询用户累计投资排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	public List<TopInvestedVO> queryTopInvested(int rankNum) throws Exception {
		return topHomeDaoImpl.queryTopInvested(rankNum);
	}
	
	/**
	 * 查询用户累积充值排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	public List<TopInpourVO> queryTopInpour(int rankNum) throws Exception {
		return topHomeDaoImpl.queryTopInpour(rankNum);
	}
	
	/**
	 * 查询用户累积提现排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	public List<TopWithdrawVO> queryTopWithdraw(int rankNum) throws Exception {
		return topHomeDaoImpl.queryTopWithdraw(rankNum);
	}
	
	/**
	 * 查询用户累积借款排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	public List<TopBorrowVO> queryTopBorrow(int rankNum) throws Exception {
		return topHomeDaoImpl.queryTopBorrow(rankNum);
	}
	
	/**
	 * 查询用户可用积分排名
	 * @param rankNum
	 * @return
	 * @throws Exception
	 */
	public List<TopIntegralVO> queryTopIntegral(int rankNum) throws Exception {
		return topHomeDaoImpl.queryTopIntegral(rankNum);
	}
}
