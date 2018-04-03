package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.ReportBorrowVO;
import com.pandadai.finance.vo.ReportSecondVO;


public interface IReportBorrowDao {
	
	/**
	 * 根据所传年月获取平台运营数据-发标列表
	 * @param month YYYY-MM
	 * @return
	 * @throws Exception
	 */
	List<ReportBorrowVO> queryReportBorrowList(String month) throws Exception;

	/**
	 * 根据所传年月获取平台运营数据-秒标列表
	 * @param month
	 * @return
	 * @throws Exception
	 */
	List<ReportSecondVO> queryReportSecondList(String month) throws Exception;
	
}
