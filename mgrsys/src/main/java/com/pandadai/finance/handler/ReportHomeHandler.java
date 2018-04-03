package com.pandadai.finance.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pandadai.finance.dao.impl.ReportHomeDaoImpl;
import com.pandadai.finance.vo.ReportHomeVO;

public class ReportHomeHandler {
	private ReportHomeDaoImpl reportHomeDaoImpl;

	public void setReportHomeDaoImpl(ReportHomeDaoImpl reportHomeDaoImpl) {
		this.reportHomeDaoImpl = reportHomeDaoImpl;
	}
	
	/**
	 * 查询某月平台的运营数据
	 * @param month
	 * @return
	 * @throws Exception
	 */
//	public ReportHomeVO queryReportInfo(String month) throws Exception {
//		ReportHomeVO vo = null;
//		//获取本月最后一天
//		Calendar cal = Calendar.getInstance();
//		int maxDate = cal.getActualMaximum(Calendar.DATE);
//		cal.set(Calendar.DATE, maxDate);
//		
//		//格式化用户输入的查询时间
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		Date dat = null;
//		Date dat2 = null;
//		String month2 = null;
//		if (StringUtils.isBlank(month)) {
//			Calendar cc = Calendar.getInstance();
//			month = cc.get(Calendar.YEAR) + "-" + (cc.get(Calendar.MONTH)  < 10 ? "0" + cc.get(Calendar.MONTH) : cc.get(Calendar.MONTH));
//			// 待收数据需用下月月份进行比较
//			month2 = cc.get(Calendar.YEAR) + "-" + ((cc.get(Calendar.MONTH) + 1) < 10 ? "0"	+ (cc.get(Calendar.MONTH) + 1) : (cc.get(Calendar.MONTH) + 1)); 
//			try {
//				//将时间格式化
//				dat = sdf.parse(month);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		} else {
//			try {
//				//将时间格式化
//				dat = sdf.parse(month);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			
//			Calendar cal2 = Calendar.getInstance();
//			cal2.setTime(dat);
//			cal2.add(Calendar.MONTH, 1);
//			month2 = cal2.get(Calendar.YEAR) + "-" + ((cal2.get(Calendar.MONTH) + 1) < 10 ? "0"	+ (cal2.get(Calendar.MONTH) + 1) : (cal2.get(Calendar.MONTH) + 1)); 
//		}
//		
//
//		if (dat != null) {
//			if (dat.after(cal.getTime())) {	//当用户输入时间是下月时（还未产生数据的月份），不做操作
//				//
//			} else {	//查询各数据项
//				vo = new ReportHomeVO();
//				
//				ReportHomeVO tmpVO = null;
//				//投资人数
//				tmpVO = reportHomeDaoImpl.queryReportNumbers(month);
//				vo.setMonth(month);
//				vo.setNumbers(tmpVO.getNumbers());
//				
//				//总成交量
//				tmpVO = reportHomeDaoImpl.queryReportVolume(month);
//				vo.setVolume(tmpVO.getVolume());
//				
//				//（本金、利息）回款金额
//				tmpVO = reportHomeDaoImpl.queryReportBack(month);
//				vo.setCapitalBack(tmpVO.getCapitalBack());
//				vo.setInterestBack(tmpVO.getInterestBack());
//				vo.setFee(tmpVO.getFee());
//				
//				//最终获利
//				vo.setProfit(vo.getInterestBack() - vo.getFee());
//				
//				//（本金、利息）待收金额
//				tmpVO = reportHomeDaoImpl.queryReportUncollected(month2);
//				vo.setCapitalUncollected(tmpVO.getCapitalUncollected());
//				vo.setInterestUncollected(tmpVO.getInterestUncollected());
//				vo.setFeeUncollected(tmpVO.getFeeUncollected());
//				
//				//总待收
//				vo.setSumUncollected(vo.getCapitalUncollected() + vo.getInterestUncollected());
//				
//				//平均每人收益
//				if (vo.getNumbers() > 0)
//					vo.setAverageProfit(vo.getProfit() / vo.getNumbers());
//				else vo.setAverageProfit(0);
//			}
//		}
//		
//		return vo;
//	}
	
	public ReportHomeVO queryReportInfo(String month) throws Exception {
		ReportHomeVO vo =  new ReportHomeVO();
		ReportHomeVO tmpVO = null;
		
		if (StringUtils.isBlank(month)) {
			//投资人数
			tmpVO = reportHomeDaoImpl.queryReportNumbers(null);
			vo.setMonth(tmpVO.getMonth());
			vo.setNumbers(tmpVO.getNumbers());
			
			//总成交量
			tmpVO = reportHomeDaoImpl.queryReportVolume(null);
			vo.setVolume(tmpVO.getVolume());
			
			//（本金、利息）回款金额
			tmpVO = reportHomeDaoImpl.queryReportBack(null);
			vo.setCapitalBack(tmpVO.getCapitalBack());
			vo.setInterestBack(tmpVO.getInterestBack());
			vo.setFee(tmpVO.getFee());
			
			//最终获利
//			vo.setProfit(vo.getInterestBack() - vo.getFee());
			
			//（本金、利息）待收金额
			tmpVO = reportHomeDaoImpl.queryReportUncollected(null);
			vo.setCapitalUncollected(tmpVO.getCapitalUncollected());
			vo.setInterestUncollected(tmpVO.getInterestUncollected());
			vo.setFeeUncollected(tmpVO.getFeeUncollected());
			
			//总待收
//			vo.setSumUncollected(vo.getCapitalUncollected() + vo.getInterestUncollected());
//			
//		//平均每人收益
//			if (vo.getNumbers() > 0)
//				vo.setAverageProfit(vo.getProfit() / vo.getNumbers());
//			else vo.setAverageProfit(0);
			
		} else {
			//获取本月最后一天
			Calendar cal = Calendar.getInstance();
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DATE, maxDate);
			
			//格式化用户输入的查询时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dat = null;
			dat = sdf.parse(month);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(dat);
			cal2.add(Calendar.MONTH, 1);
			String month2 = null;
			month2 = cal2.get(Calendar.YEAR) + "-" + ((cal2.get(Calendar.MONTH) + 1) < 10 ? "0"	+ (cal2.get(Calendar.MONTH) + 1) : (cal2.get(Calendar.MONTH) + 1));
			
			if (dat != null) {
				if (dat.after(cal.getTime())) {	//当用户输入时间是下月时（还未产生数据的月份），不做操作
					//
				} else {	//查询各数据项
					//投资人数
					tmpVO = reportHomeDaoImpl.queryReportNumbers(month);
					vo.setMonth(month);
					vo.setNumbers(tmpVO.getNumbers());
					
					//总成交量
					tmpVO = reportHomeDaoImpl.queryReportVolume(month);
					vo.setVolume(tmpVO.getVolume());
					
					//（本金、利息）回款金额
					tmpVO = reportHomeDaoImpl.queryReportBack(month);
					vo.setCapitalBack(tmpVO.getCapitalBack());
					vo.setInterestBack(tmpVO.getInterestBack());
					vo.setFee(tmpVO.getFee());
					
					//最终获利
//					vo.setProfit(vo.getInterestBack() - vo.getFee());
					
					//（本金、利息）待收金额
					tmpVO = reportHomeDaoImpl.queryReportUncollected(month2);
					vo.setCapitalUncollected(tmpVO.getCapitalUncollected());
					vo.setInterestUncollected(tmpVO.getInterestUncollected());
					vo.setFeeUncollected(tmpVO.getFeeUncollected());

					//总待收
//					vo.setSumUncollected(vo.getCapitalUncollected() + vo.getInterestUncollected());
//					
//					//平均每人收益
//					if (vo.getNumbers() > 0)
//						vo.setAverageProfit(vo.getProfit() / vo.getNumbers());
//					else vo.setAverageProfit(0);
//					
//					//新注册人数
//					vo.setRegisterNew(reportHomeDaoImpl.queryReportRegisterNew(month));
//					
//					//平台总注册人数
//					vo.setRegisterSum(reportHomeDaoImpl.queryReportRegisterSum());
				}
			}
		}

		//最终获利
		vo.setProfit(vo.getInterestBack() - vo.getFee());
		
		//总待收
		vo.setSumUncollected(vo.getCapitalUncollected() + vo.getInterestUncollected());
		
		//平均每人收益
		if (vo.getNumbers() > 0)
			vo.setAverageProfit(vo.getProfit() / vo.getNumbers());
		else vo.setAverageProfit(0);
		
		//新注册人数
		vo.setRegisterNew(reportHomeDaoImpl.queryReportRegisterNew(month));
		
		//平台总注册人数
		vo.setRegisterSum(reportHomeDaoImpl.queryReportRegisterSum());
		
		return vo;
	}
}
