/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourPendingWithdrawVO;
import com.pandadai.finance.vo.InpourWithdrawVO;

/**
 * @author 仵作
 * 2014-8-30 下午3:43:10
 */
public interface IInpourWithdrawDao {

	/**
	 * 获取提现记录明细
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourWithdrawVO> queryWithdrawList(String startDate, String endDate, String type) throws Exception;
	
	/**
	 * 获取待审核提现手续费统计
	 * @return
	 * @throws Exception
	 */
	List<InpourPendingWithdrawVO> queryPendingWithdrawList() throws Exception;
}
