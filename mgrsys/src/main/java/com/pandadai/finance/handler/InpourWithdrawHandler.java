/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InpourWithdrawDaoImpl;
import com.pandadai.finance.vo.InpourPendingWithdrawVO;
import com.pandadai.finance.vo.InpourWithdrawVO;

/**
 * @author 仵作
 * 2014-8-30 下午3:51:51
 */
public class InpourWithdrawHandler {
	/**
	 * 配置注入
	 */
	private InpourWithdrawDaoImpl inpourWithdrawImpl;

	public void setInpourWithdrawImpl(InpourWithdrawDaoImpl inpourWithdrawImpl) {
		this.inpourWithdrawImpl = inpourWithdrawImpl;
	}

	/**
	 * 查询提现记录明细
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourWithdrawVO> queryWithdrawList(String startDate, String endDate, String type) throws Exception {
		return inpourWithdrawImpl.queryWithdrawList(startDate, endDate, type);
	} 
	
	/**
	 * 获取待审核提现手续费统计
	 * @return
	 * @throws Exception
	 */
	public List<InpourPendingWithdrawVO> queryPendingWithdrawList() throws Exception {
		return inpourWithdrawImpl.queryPendingWithdrawList();
	}
}
