/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InvestAdminUserVO;

/**
 * @author 仵作
 * 2014-9-5 上午9:49:33
 */
public interface IInvestAdminUserDao {
	
	/**
	 * 查询所有管理员用户相关信息
	 * @return
	 * @throws Exception
	 */
	List<InvestAdminUserVO> queryAdminUserList(String startDate, String endDate) throws Exception;
}
