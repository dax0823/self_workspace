/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InvestCustomerBackCapitalVO;
import com.pandadai.finance.vo.InvestCustomerInfoVO;
import com.pandadai.finance.vo.InvestCustomerInpourVO;
import com.pandadai.finance.vo.InvestCustomerInterestVO;
import com.pandadai.finance.vo.InvestCustomerInvestVO;
import com.pandadai.finance.vo.InvestCustomerInvitedVO;
import com.pandadai.finance.vo.InvestCustomerUncollectedDetailVO;
import com.pandadai.finance.vo.InvestCustomerUncollectedVO;
import com.pandadai.finance.vo.InvestCustomerVO;

/**
 * @author 仵作
 * 2014-9-3 下午8:30:07
 */
public interface IInvestCustomerDao {
	
	/**
	 * 查询客户相关的统计信息
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerVO> queryInvestCustomerList() throws Exception;
	
	List<InvestCustomerVO> queryInvestCustomerList(String userName, String realName, String recommendName, String startDate, String endDate) throws Exception;
	
	/**
	 * 查询用户当前总代收
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	InvestCustomerUncollectedVO queryInvestCustomerUncollected(String userId) throws Exception;
	
	/**
	 * 查询用户的待收详情
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerUncollectedDetailVO> queryInvestCustomerUncollectedDetail(String userId) throws Exception;
	
	/**
	 * 查询用户推荐人
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerInvitedVO> queryInvestCustomerInvited(String userId) throws Exception;
	
	/**
	 * 查询客户个人信息
	 * @return
	 * @throws Exception
	 */
	InvestCustomerInfoVO queryInvestCustomerInfo(String userId) throws Exception;
	
	/**
	 * 查询客户的充值记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerInpourVO> queryInvestCustomerInpour(String userId) throws Exception;

	/**
	 * 查询客户投资记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerInvestVO> queryInvestCustomerInvest(String userId) throws Exception;

	/**
	 * 查询客户利息发放记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerInterestVO> queryInvestCustomerInterest(String userId) throws Exception;
	
	/**
	 * 查询客户回款记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<InvestCustomerBackCapitalVO> queryInvestCustomerBackCapital(String userId) throws Exception;
}
