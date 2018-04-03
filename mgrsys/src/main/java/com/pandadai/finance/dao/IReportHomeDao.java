package com.pandadai.finance.dao;

import com.pandadai.finance.vo.ReportHomeVO;

public interface IReportHomeDao {
	
	/**
	 * 根据所传年月获取平台运营数据-投资人数
	 * @param month YYYY-MM
	 * @return
	 * @throws Exception
	 */
	ReportHomeVO queryReportNumbers(String month) throws Exception;
	
	/**
	 * 获取上一个（完整）月平台运营数据
	 * @return
	 * @throws Exception
	 */
	ReportHomeVO queryReportNumbers() throws Exception;
	
	/**
	 * 根据所传年月获取平台运营数据-成交量
	 * @param month
	 * @return
	 * @throws Exception
	 */
	ReportHomeVO queryReportVolume(String month) throws Exception;
	
	/**
	 * 根据所传年月获取平台运营数据-回款信息
	 * @param month
	 * @return
	 * @throws Exception
	 */
	ReportHomeVO queryReportBack(String month) throws Exception;
	
	/**
	 * 根据所传年月获取平台运营数据-待收信息
	 * @param month
	 * @return
	 * @throws Exception
	 */
	ReportHomeVO queryReportUncollected(String month) throws Exception;
	
	/**
	 * 根据所传年月获取平台运营数据-当月新注册人数
	 * @param month
	 * @return
	 * @throws Exception
	 */
	int queryReportRegisterNew(String month) throws Exception;
	
	/**
	 * 根据所传年月获取平台运营数据-当前全部注册用户人数
	 * @return
	 * @throws Exception
	 */
	int queryReportRegisterSum() throws Exception;
}
