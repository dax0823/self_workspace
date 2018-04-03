/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InvestBorroworDetailVO;
import com.pandadai.finance.vo.InvestBorroworVO;

/**
 * @author 仵作
 * 2014-9-7 下午9:36:59
 */
public interface IInvestBorroworDao {
	
	/**
	 * 获取所有借款人所借款项的汇总信息
	 * @return
	 * @throws Exception
	 */
	List<InvestBorroworVO> queryInvestBorroworList() throws Exception;
	
	/**
	 * 查询某个借款人所借款项明细信息
	 * @return
	 * @throws Exception
	 */
	List<InvestBorroworDetailVO> queryInvestBorroworDetailList(String userId) throws Exception;
}
