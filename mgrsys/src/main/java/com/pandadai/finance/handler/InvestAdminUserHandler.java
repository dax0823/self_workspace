/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InvestAdminUserDaoImpl;
import com.pandadai.finance.vo.InvestAdminUserVO;

/**
 * @author 仵作
 * 2014-9-5 上午9:55:30
 */
public class InvestAdminUserHandler {

	/**
	 * 配置注入
	 */
	private InvestAdminUserDaoImpl investAdminUserImpl;

	/**
	 * @param investAdminUserImpl the investAdminUserImpl to set
	 */
	public void setInvestAdminUserImpl(InvestAdminUserDaoImpl investAdminUserImpl) {
		this.investAdminUserImpl = investAdminUserImpl;
	}
	
	/**
	 * 查询所有管理员用户信息
	 * @return
	 * @throws Exception
	 */
	public List<InvestAdminUserVO> queryAdminUserList(String startDate, String endDate) throws Exception {
		return investAdminUserImpl.queryAdminUserList(startDate, endDate);
	}
}
