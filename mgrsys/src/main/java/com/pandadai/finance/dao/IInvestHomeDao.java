/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InvestHomeDetail4ExportVO;
import com.pandadai.finance.vo.InvestHomeDetailVO;
import com.pandadai.finance.vo.InvestHomeSumInterestVO;
import com.pandadai.finance.vo.InvestHomeVO;

/**
 * @author 仵作
 * 2014-8-31 下午9:02:55
 */
public interface IInvestHomeDao {
	/**
	 * 查询投资总览列表
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InvestHomeVO> queryInvestHomeList(String startDate, String endDate) throws Exception;
	
	/**
	 * 获取某个投标的明细信息
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	List<InvestHomeDetailVO> queryInvestHomeDetailByBorrowId(String borrowId) throws Exception;
	
	/**
	 * 某个投标明细的导出查询
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	List<InvestHomeDetail4ExportVO> queryInvestHomeDetailByBorrowId4Export(String borrowId) throws Exception;
	
	/**
	 * 根据借款 id 查询此投标的每个投资人的利息返还情况
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	List<InvestHomeSumInterestVO> queryInvestHomeSumInterestByBorrorwId(String borrowId) throws Exception;
}
