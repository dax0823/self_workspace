package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.ReportFilingDaoImpl;
import com.pandadai.finance.vo.ReportFilingBorrowVO;
import com.pandadai.finance.vo.ReportFilingVO;

public class ReportFilingHandler {
	private ReportFilingDaoImpl reportFilingDaoImpl;

	public void setReportFilingDaoImpl(ReportFilingDaoImpl reportFilingDaoImpl) {
		this.reportFilingDaoImpl = reportFilingDaoImpl;
	}

	/**
	 * 根据发标id，查询发标情况
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ReportFilingBorrowVO> queryReportBorrowList(String id) throws Exception {
		return reportFilingDaoImpl.queryReportBorrowList(id);
	}
	
	/**
	 * 根据条件，查询发标情况
	 * @param userName
	 * @param borrowName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<ReportFilingBorrowVO> queryReportBorrowList(String userName,String borrowName, String startDate, String endDate) throws Exception {
		return reportFilingDaoImpl.queryReportBorrowList(userName, borrowName, startDate, endDate);
	}
	
	/**
	 * 查询某个标中投资人相关信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ReportFilingVO> queryReportFiling(String id) throws Exception {
		return reportFilingDaoImpl.queryReportFiling(id);
	}
}
