/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InvestBorroworDaoImpl;
import com.pandadai.finance.vo.InvestBorroworDetailVO;
import com.pandadai.finance.vo.InvestBorroworVO;

/**
 * @author 仵作
 * 2014-9-7 下午9:48:54
 */
public class InvestBorroworHandler {

	/**
	 * 配置注入
	 */
	private InvestBorroworDaoImpl investBorroworImpl;
	
	/**
	 * @param investBorroworImpl the investBorroworImpl to set
	 */
	public void setInvestBorroworImpl(InvestBorroworDaoImpl investBorroworImpl) {
		this.investBorroworImpl = investBorroworImpl;
	}

	/**
	 * 查询所有借款人所借款项的汇总信息
	 * @return
	 * @throws Exception
	 */
	public List<InvestBorroworVO> queryInvestBorroworList() throws Exception {
		return investBorroworImpl.queryInvestBorroworList();
	}
	
	/**
	 * 查询某个借款人所借款项明细信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<InvestBorroworDetailVO> queryInvestBorroworDetailList(String userId) throws Exception {
		return investBorroworImpl.queryInvestBorroworDetailList(userId);
	}
}
