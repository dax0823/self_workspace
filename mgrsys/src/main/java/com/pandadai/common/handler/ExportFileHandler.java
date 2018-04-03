package com.pandadai.common.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.pandadai.biz.dao.impl.BizLotteryLogDaoImpl;
import com.pandadai.common.utils.ExportFile;
import com.pandadai.common.utils.Utils;
import com.pandadai.finance.dao.impl.InpourDetailDaoImpl;
import com.pandadai.finance.dao.impl.InpourHomeDaoImpl;
import com.pandadai.finance.dao.impl.InpourRewardDaoImpl;
import com.pandadai.finance.dao.impl.InpourWithdrawDaoImpl;
import com.pandadai.finance.dao.impl.InvestAdminUserDaoImpl;
import com.pandadai.finance.dao.impl.InvestHomeDaoImpl;
import com.pandadai.finance.dao.impl.ReportBorrowDaoImpl;
import com.pandadai.finance.dao.impl.ReportFilingDaoImpl;
import com.pandadai.finance.vo.InvestHomeSumInterestVO;

/***
 * 文件导出处理
 * 
 * @author 仵作
 * 
 */
public class ExportFileHandler {
	/**
	 * 配置注入
	 */
	//文件在本地的输出目录
//	private String outputPath;
	private InpourHomeDaoImpl inpourHomeImpl;
	private String inpourHomeDetailHeader;
	
	private InpourRewardDaoImpl inpourRewardImpl;
	private String inpourRewardHeader;
	
	private InpourWithdrawDaoImpl inpourWithdrawImpl;
	private String inpourWithdrawHeader;
	
	private InvestHomeDaoImpl investHomeImpl;
	private String investHomeHeader;
	private String investHomeDetailHeader;
	private String investHomeSumInterestHeader;

	private InvestAdminUserDaoImpl investAdminUserImpl;
	private String investAdminHeader;

	private BizLotteryLogDaoImpl BizLotteryLogImpl;
	private String bizLotteryLogHeader;
	
	private InpourDetailDaoImpl inpourDetailImpl;
	private String inpourDetailHeader;
	
	private ReportBorrowDaoImpl reportBorrowDaoImpl;
	private String reportBorrowHeader;
	private String reportSecondHeader;
	
	private ReportFilingDaoImpl reportFilingDaoImpl;
	private String reportFilingHeader;
	
	public void setReportSecondHeader(String reportSecondHeader) {
		this.reportSecondHeader = reportSecondHeader;
	}

	public void setReportFilingDaoImpl(ReportFilingDaoImpl reportFilingDaoImpl) {
		this.reportFilingDaoImpl = reportFilingDaoImpl;
	}

	public void setReportFilingHeader(String reportFilingHeader) {
		this.reportFilingHeader = reportFilingHeader;
	}

	public void setReportBorrowDaoImpl(ReportBorrowDaoImpl reportBorrowDaoImpl) {
		this.reportBorrowDaoImpl = reportBorrowDaoImpl;
	}

	public void setReportBorrowHeader(String reportBorrowHeader) {
		this.reportBorrowHeader = reportBorrowHeader;
	}

	/**
	 * @param inpourDetailImpl the inpourDetailImpl to set
	 */
	public void setInpourDetailImpl(InpourDetailDaoImpl inpourDetailImpl) {
		this.inpourDetailImpl = inpourDetailImpl;
	}

	/**
	 * @param inpourDetailHeader the inpourDetailHeader to set
	 */
	public void setInpourDetailHeader(String inpourDetailHeader) {
		this.inpourDetailHeader = inpourDetailHeader;
	}

	/**
	 * @param bizLotteryLogImpl the bizLotteryLogImpl to set
	 */
	public void setBizLotteryLogImpl(BizLotteryLogDaoImpl bizLotteryLogImpl) {
		BizLotteryLogImpl = bizLotteryLogImpl;
	}

	/**
	 * @param bizLotteryLogHeader the bizLotteryLogHeader to set
	 */
	public void setBizLotteryLogHeader(String bizLotteryLogHeader) {
		this.bizLotteryLogHeader = bizLotteryLogHeader;
	}

	/**
	 * @param investAdminHeader the investAdminHeader to set
	 */
	public void setInvestAdminHeader(String investAdminHeader) {
		this.investAdminHeader = investAdminHeader;
	}

	/**
	 * @param investAdminUserImpl the investAdminUserImpl to set
	 */
	public void setInvestAdminUserImpl(InvestAdminUserDaoImpl investAdminUserImpl) {
		this.investAdminUserImpl = investAdminUserImpl;
	}

	/**
	 * @param investHomeSumInterestHeader the investHomeSumInterestHeader to set
	 */
	public void setInvestHomeSumInterestHeader(String investHomeSumInterestHeader) {
		this.investHomeSumInterestHeader = investHomeSumInterestHeader;
	}

	/**
	 * @param investHomeDetailHeader the investHomeDetailHeader to set
	 */
	public void setInvestHomeDetailHeader(String investHomeDetailHeader) {
		this.investHomeDetailHeader = investHomeDetailHeader;
	}

	/**
	 * @param investHomeImpl the investHomeImpl to set
	 */
	public void setInvestHomeImpl(InvestHomeDaoImpl investHomeImpl) {
		this.investHomeImpl = investHomeImpl;
	}

	/**
	 * @param investHomeHeader the investHomeHeader to set
	 */
	public void setInvestHomeHeader(String investHomeHeader) {
		this.investHomeHeader = investHomeHeader;
	}

	public void setInpourWithdrawImpl(InpourWithdrawDaoImpl inpourWithdrawImpl) {
		this.inpourWithdrawImpl = inpourWithdrawImpl;
	}

	public void setInpourWithdrawHeader(String inpourWithdrawHeader) {
		this.inpourWithdrawHeader = inpourWithdrawHeader;
	}

	public void setInpourRewardImpl(InpourRewardDaoImpl inpourRewardImpl) {
		this.inpourRewardImpl = inpourRewardImpl;
	}

	public void setInpourRewardHeader(String inpourRewardHeader) {
		this.inpourRewardHeader = inpourRewardHeader;
	}

	public void setInpourHomeImpl(InpourHomeDaoImpl inpourHomeImpl) {
		this.inpourHomeImpl = inpourHomeImpl;
	}

//	public void setOutputPath(String outputPath) {
//		this.outputPath = outputPath;
//	}

	public void setInpourHomeDetailHeader(String inpourHomeDetailHeader) {
		this.inpourHomeDetailHeader = inpourHomeDetailHeader;
	}

	/**
	 * 拼装文件名的时间部分
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private String getTimePart(String startDate, String endDate) {
		StringBuffer sub = new StringBuffer();
		if (!(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate))) {
			sub = new StringBuffer();
			if (StringUtils.isBlank(startDate)) {
				sub.append("2014-06-25");
			} else {
				sub.append(startDate);
			}
			sub.append("_");
			if (StringUtils.isBlank(endDate)) {
				sub.append("now");
			} else {
				sub.append(endDate);
			}
			sub.append("_");
		}
		return sub.toString();
	}
	
	/**
	 * 处理财务总览明细的文件导出
	 * @return
	 * @throws Exception
	 */
//	public boolean exportInpourHomeDetail(String filename, String startDate, String endDate, String way) throws Exception{
//		if (outputPath != null && !outputPath.endsWith("\\")) {
//			outputPath = outputPath + "\\";
//		}
//		
//		//查询条件含有时间范围，则文件名补齐
//		String timePart = getTimePart(startDate, endDate);
//
//		//查询条件含有充值类型，则文件名补齐
//		String wayName = way;
//		
//		boolean flag = false;
//		//先导出按天统计文件
//		List dayLst = inpourHomeImpl.sumEveryDay(startDate, endDate, way);
//		flag = ExportFile.exportFile(outputPath, filename + "_" + Utils.getNowTime4FileName() + "_" 
//				+ wayName + "_" + timePart +"天统计.csv", inpourHomeDetailHeader, dayLst);
//		if (flag) {
//			//再导出按月统计文件
//			List monthLst = inpourHomeImpl.sumEveryMonth(startDate, endDate, way);
//			flag = ExportFile.exportFile(outputPath, filename + "_" + Utils.getNowTime4FileName() + "_" 
//					+ wayName + "_" + timePart + "月统计.csv", inpourHomeDetailHeader, monthLst);
//		}
//		return flag;
//	}
	
	/**
	 * 处理财务总览明细的数据流导出
	 * @param filename
	 * @param startDate
	 * @param endDate
	 * @param way
	 * @return
	 * @throws Exception
	 */
	public void exportInpourHomeDetail(String type, String startDate, String endDate, String way, HttpServletResponse response) throws Exception{
		//查询条件含有时间范围，则文件名补齐
		String timePart = getTimePart(startDate, endDate);
		
		//查询条件含有充值类型，则文件名补齐
		String wayName = way;
		
		String fileName = "";
		List lst = null;
		//判断本次导出类型
		if (type.equalsIgnoreCase("months")) {
			//按月
			lst = inpourHomeImpl.sumEveryMonth(startDate, endDate, way);
//			fileName = "财务总览明细" + "_" + Utils.getNowTime4FileName() + "_" + wayName + "_" + timePart + "月统计.csv";
			fileName = "inpour" + "_" + Utils.getNowTime4FileName() + "_" + wayName + "_" + timePart + "months.csv";
		} else if (type.equalsIgnoreCase("days")) {
			//按天
			lst = inpourHomeImpl.sumEveryDay(startDate, endDate, way);
//			fileName = "财务总览明细" + "_" + Utils.getNowTime4FileName() + "_" + wayName + "_" + timePart +"天统计csv";
			fileName = "inpour" + "_" + Utils.getNowTime4FileName() + "_" + wayName + "_" + timePart +"days.csv";
		} else {
			//error
		}
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(inpourHomeDetailHeader, lst, response);
//		return retFileName;
	}
	
	/**
	 * 线下充值奖励页数据流导出
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @throws Exception
	 */
	public void exportInpourRewardList(String startDate, String endDate,  HttpServletResponse response) throws Exception{
//		String timePart = getTimePart(startDate, endDate);
		List lst = inpourRewardImpl.queryRewardList(startDate, endDate);
//		List lst = inpourRewardImpl.queryCusRewardList(id, startDate, endDate);
		String fileName = "inpour_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"rewards.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(inpourRewardHeader, lst, response);
	}
	
	/**
	 * 提现记录数据流导出
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @throws Exception
	 */
	public void exportInpourWithdraw(String startDate, String endDate, String type, HttpServletResponse response) throws Exception{
		List lst = inpourWithdrawImpl.queryWithdrawList(startDate, endDate, type);
		String fileName = "inpour_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"withdraw.csv";
//		String fileName = "inpour_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"withdraw.txt";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(inpourWithdrawHeader, lst, response);
	}
	
	/**
	 * 投资总览页数据流导出
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @throws Exception
	 */
	public void exportInvestHome(String startDate, String endDate,  HttpServletResponse response) throws Exception{
		List lst = investHomeImpl.queryInvestHomeList(startDate, endDate);
		String fileName = "invest_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"home.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(investHomeHeader, lst, response);
	}
	
	/**
	 * 某个投标明细汇总的数据流导出
	 * @param borrowId
	 * @param response
	 * @throws Exception
	 */
	public void exportInvestHomeDetail(String borrowId,  HttpServletResponse response) throws Exception{
		List lst = investHomeImpl.queryInvestHomeDetailByBorrowId4Export(borrowId);
		String fileName = "invest_" + Utils.getNowTime4FileName() + "_" + borrowId +"_detail.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(investHomeDetailHeader, lst, response);
	}
	
	/**
	 * 某个投标利息返还情况数据流导出
	 * @param borrowId
	 * @param response
	 * @throws Exception
	 */
	public void exportInvestHomeSumInterest(String borrowId,  HttpServletResponse response) throws Exception{
		List lst = investHomeImpl.queryInvestHomeSumInterestByBorrorwId(borrowId);
		String fileName = "invest_" + Utils.getNowTime4FileName() + "_" + borrowId +"_detail.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		
		// 根据实际情况拼装列头
		StringBuffer header = new StringBuffer();
		header.append(investHomeSumInterestHeader);
		if (lst != null && lst.size() > 0) {
//			InvestHomeSumInterestVO ihs = (InvestHomeSumInterestVO) lst.get(0);
			int num = ((InvestHomeSumInterestVO) lst.get(0)).getBorrowDuration();
			for (int i=0; i<num; i++) {
				header.append(",第" + (i + 1) + "月");
			}
		}
		ExportFile.exportStream(header.toString(), lst, response);
	}
	
	/**
	 * 管理员统计信息数据流导出
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @throws Exception
	 */
	public void exportInvestAdmin(String startDate, String endDate, HttpServletResponse response) throws Exception{
		List lst = investAdminUserImpl.queryAdminUserList(startDate, endDate);
		String fileName = "invest_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"detail.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(investAdminHeader, lst, response);
	}
	
	/**
	 * 将抽奖奖品记录以文件形式导出
	 * @param userName
	 * @param realName
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @throws Exception
	 */
	public void exportBizLotteryLog(String userName, String realName, String name, String startDate, String endDate, HttpServletResponse response) throws Exception{
		List lst = BizLotteryLogImpl.queryIntegralLotteryLog(userName, realName, name, startDate, endDate);
		String fileName = "lottery_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"log.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(bizLotteryLogHeader, lst, response);
	}
	
	/**
	 * 将充值记录以文件形式导出
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @throws Exception
	 */
	public void exportInpourDetail(String startDate, String endDate, HttpServletResponse response) throws Exception{
		List lst = inpourDetailImpl.queryInpourAll(startDate, endDate);
		String fileName = "inpour_" + Utils.getNowTime4FileName() + "_" + getTimePart(startDate, endDate) +"inpourAll.csv";
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(inpourDetailHeader, lst, response);
	}
	
	/**
	 * 某月发标数据
	 * @param month
	 * @param response
	 * @throws Exception
	 */
	public void exportReportBorrowList(String month, HttpServletResponse response) throws Exception{
		List lst = reportBorrowDaoImpl.queryReportBorrowList(month);
		
		Calendar cal = Calendar.getInstance();
		if (StringUtils.isNotBlank(month)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dat = null;
			cal.setTime(sdf.parse(month));
		} else {
			cal.add(Calendar.MONTH, -1);
		}
		
//		month = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) < 10 ? "0"	+ cal.get(Calendar.MONTH) : cal.get(Calendar.MONTH));
		month = cal.get(Calendar.YEAR) + "" + ((cal.get(Calendar.MONTH) + 1) < 10 ? "0"	+ (cal.get(Calendar.MONTH) + 1) : (cal.get(Calendar.MONTH) + 1));
		String fileName = "report_" + Utils.getNowTime4FileName() + "_" + month +"_borrowList.csv";
		
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(reportBorrowHeader, lst, response);
	}
	
	/**
	 * 某月秒标数据
	 * @param month
	 * @param response
	 * @throws Exception
	 */
	public void exportReportSecondList(String month, HttpServletResponse response) throws Exception{
		List lst = reportBorrowDaoImpl.queryReportSecondList(month);
		
		Calendar cal = Calendar.getInstance();
		if (StringUtils.isNotBlank(month)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dat = null;
			cal.setTime(sdf.parse(month));
		} else {
			cal.add(Calendar.MONTH, -1);
		}
		
//		month = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) < 10 ? "0"	+ cal.get(Calendar.MONTH) : cal.get(Calendar.MONTH));
		month = cal.get(Calendar.YEAR) + "" + ((cal.get(Calendar.MONTH) + 1) < 10 ? "0"	+ (cal.get(Calendar.MONTH) + 1) : (cal.get(Calendar.MONTH) + 1));
		String fileName = "report_" + Utils.getNowTime4FileName() + "_" + month +"_secondList.csv";
		
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(reportSecondHeader, lst, response);
	}
	
	/**
	 * 某个标中投资人相关信息，用于归档
	 * @param response
	 * @throws Exception
	 */
	public void exportFinReportFiling(String id, HttpServletResponse response) throws Exception{
		List lst = reportFilingDaoImpl.queryReportFiling(id);
		
		String fileName = "report_filing_" + Utils.getNowTime4FileName() + "_" + id +".csv";
		
		response.setHeader("Content-disposition",  "attachment;filename=" +  fileName);
		ExportFile.exportStream(reportFilingHeader, lst, response);
	}
}
