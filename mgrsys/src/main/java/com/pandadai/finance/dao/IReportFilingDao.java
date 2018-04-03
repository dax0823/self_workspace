package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.ReportFilingBorrowVO;
import com.pandadai.finance.vo.ReportFilingVO;

public interface IReportFilingDao {
	
	/**
	 * 根据发标id，查询发标情况
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<ReportFilingBorrowVO> queryReportBorrowList(String id) throws Exception;

	/**
	 * 根据条件，查询发标情况
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<ReportFilingBorrowVO> queryReportBorrowList(String userName,String borrowName, String startDate, String endDate) throws Exception;
	
	/**
	 * 查询某个标中投资人相关信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<ReportFilingVO> queryReportFiling(String id) throws Exception;
}
