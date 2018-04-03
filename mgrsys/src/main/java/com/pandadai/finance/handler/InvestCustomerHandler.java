/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InvestCustomerDaoImpl;
import com.pandadai.finance.vo.InvestCustomerBackCapitalVO;
import com.pandadai.finance.vo.InvestCustomerInfoVO;
import com.pandadai.finance.vo.InvestCustomerInpourVO;
import com.pandadai.finance.vo.InvestCustomerInterestVO;
import com.pandadai.finance.vo.InvestCustomerInvestVO;
import com.pandadai.finance.vo.InvestCustomerInvitedVO;
import com.pandadai.finance.vo.InvestCustomerUncollectedDetailVO;
import com.pandadai.finance.vo.InvestCustomerVO;

/**
 * @author 仵作
 * 2014-9-3 下午8:42:03
 */
public class InvestCustomerHandler {
	/**
	 * 配置注入
	 */
	private InvestCustomerDaoImpl investCustomerImpl;
	
	/**
	 * @param investCustomerImpl the investCustomerImpl to set
	 */
	public void setInvestCustomerImpl(InvestCustomerDaoImpl investCustomerImpl) {
		this.investCustomerImpl = investCustomerImpl;
	}

	/**
	 * 查询客户相关的统计信息
	 * @return
	 * @throws Exception
	 */
	public List<InvestCustomerVO> queryInvestCustomer() throws Exception {
		return investCustomerImpl.queryInvestCustomerList();
	}
	
	public List<InvestCustomerVO> queryInvestCustomer(String userName, String realName, String recommendName, String startDate, String endDate) throws Exception {
		return investCustomerImpl.queryInvestCustomerList(userName, realName, recommendName, startDate, endDate);
	}
	
	/**
	 * 查询客户个人的信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public InvestCustomerInfoVO queryInvestCustomerInfo(String userId) throws Exception {
		InvestCustomerInfoVO vo = investCustomerImpl.queryInvestCustomerInfo(userId);
		vo.setSumUncollected(investCustomerImpl.queryInvestCustomerUncollected(userId).getSumUncollected());
		List<InvestCustomerInvitedVO> lst = investCustomerImpl.queryInvestCustomerInvited(userId);
		String[] invitedNames = new String[lst.size()];
		for (int i=0; i<lst.size(); i++) {
			InvestCustomerInvitedVO ivo = lst.get(i);
			invitedNames[i] = lst.get(i).getInvitedName();
		}
		vo.setInvitedName(invitedNames);
		return vo;
	}
	
	/**
	 * 查询客户的待收详情
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<InvestCustomerUncollectedDetailVO> queryInvestCustomerUncollectedDetail(String userId) throws Exception {
		return investCustomerImpl.queryInvestCustomerUncollectedDetail(userId);
	}
	
	/**
	 * 查询客户充值记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<InvestCustomerInpourVO> queryInvestCustomerInpour(String userId) throws Exception {
		return investCustomerImpl.queryInvestCustomerInpour(userId);
	}
	
	/**
	 * 查询客户投标记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<InvestCustomerInvestVO> queryInvestCustomerInvest(String userId) throws Exception {
		return investCustomerImpl.queryInvestCustomerInvest(userId);
	}
	
	/**
	 * 查询客户利息发放记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<InvestCustomerInterestVO> queryInvestCustomerInterest(String userId) throws Exception {
		return investCustomerImpl.queryInvestCustomerInterest(userId);
	}
	
	/**
	 * 查询客户回款记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<InvestCustomerBackCapitalVO> queryInvestCustomerBackCapital(String userId) throws Exception {
		return investCustomerImpl.queryInvestCustomerBackCapital(userId);
	}
	
}
