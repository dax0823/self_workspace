package com.pandadai.finance.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pandadai.finance.dao.impl.ReportBorrowDaoImpl;
import com.pandadai.finance.vo.ReportBorrowVO;
import com.pandadai.finance.vo.ReportSecondVO;

public class ReportBorrowHandler {
	private ReportBorrowDaoImpl reportBorrowDaoImpl;
	
	public void setReportBorrowDaoImpl(ReportBorrowDaoImpl reportBorrowDaoImpl) {
		this.reportBorrowDaoImpl = reportBorrowDaoImpl;
	}
	
	/**
	 * 查询某月平台的发标数据
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public List<ReportBorrowVO> queryReportBorrowList(String month) throws Exception {
		List<ReportBorrowVO> lst = null;
		
		if (StringUtils.isBlank(month)) {
			lst = reportBorrowDaoImpl.queryReportBorrowList(null);
		} else {
			 //获取本月最后一天
			Calendar cal = Calendar.getInstance();
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DATE, maxDate);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dat = null;

			try {
				// 将时间格式化
				dat = sdf.parse(month);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (dat != null) {
				if (dat.after(cal.getTime())) { // 当用户输入时间是下月时（还未产生数据的月份），不做操作
					//
				} else {
					lst = reportBorrowDaoImpl.queryReportBorrowList(month);
				}
			}
		}

		return lst;
	}

	/**
	 * 查询某月平台的秒标数据
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public List<ReportSecondVO> queryReportSecondList(String month) throws Exception {
		List<ReportSecondVO> lst = null;
		
		if (StringUtils.isBlank(month)) {
			lst = reportBorrowDaoImpl.queryReportSecondList(null);
		} else {
			 //获取本月最后一天
			Calendar cal = Calendar.getInstance();
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DATE, maxDate);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dat = null;

			try {
				// 将时间格式化
				dat = sdf.parse(month);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (dat != null) {
				if (dat.after(cal.getTime())) { // 当用户输入时间是下月时（还未产生数据的月份），不做操作
					//
				} else {
					lst = reportBorrowDaoImpl.queryReportSecondList(month);
				}
			}
		}

		return lst;
	}
}
