package com.pandadai.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.common.handler.ExportFileHandler;

/***
 * 专门的文件导出入口
 * 
 * @author 仵作
 * 
 */
@Controller
public class ExportFileAction {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ExportFileHandler exportHandler;
	
	/**
	 * 充值总览页-天统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinInpourHomeDetailDays.do")
	public void exportInpourHomeDetailDays(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String way = request.getParameter("way");

		try {
//			String fileName = exportHandler.exportInpourHomeDetail("days", startDate, endDate, way, response);
			exportHandler.exportInpourHomeDetail("days", startDate, endDate, way, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 充值总览页-月统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinInpourHomeDetailMonths.do")
	public void exportInpourHomeDetailMonths(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String way = request.getParameter("way");

		try {
//			String fileName = exportHandler.exportInpourHomeDetail("months", startDate, endDate, way, response);
			exportHandler.exportInpourHomeDetail("months", startDate, endDate, way, response);
			//设置输出文件名称
//			response.setHeader("Content-disposition",  "attachment;filename=" + fileName);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 线下充值奖励页
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinInpourReward.do")
	public void exportInpourReward(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String id = request.getParameter("id");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {
			exportHandler.exportInpourRewardList(startDate, endDate, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 充值提现记录页
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinInpourWithdraw.do")
	public void exportInpourWithdraw(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String type = request.getParameter("type");

		try {
			exportHandler.exportInpourWithdraw(startDate, endDate, type, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 投资总览页
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportInvestHome.do")
	public void exportInvestHome(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {
			exportHandler.exportInvestHome(startDate, endDate, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 投标明细汇总
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportInvestHomeDetail.do")
	public void exportInvestHomeDetail(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String borrowId = request.getParameter("id");

		try {
			exportHandler.exportInvestHomeDetail(borrowId, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 投标明细汇总
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportInvestHomeSumInterest.do")
	public void exportInvestHomeSumInterest(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String borrowId = request.getParameter("id");

		try {
			exportHandler.exportInvestHomeSumInterest(borrowId, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 管理员统计信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportInvestAdmin.do")
	public void exportInvestAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {
			exportHandler.exportInvestAdmin(startDate, endDate, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 抽奖奖品记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportBizLotteryLog.do")
	public void exportBizLotteryLog(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String productName = request.getParameter("productName");

		try {
			exportHandler.exportBizLotteryLog(userName, realName, productName, startDate, endDate, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 时间段内充值
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinInpourDetail.do")
	public void exportFinInpourDetail(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {
			exportHandler.exportInpourDetail(startDate, endDate, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 某月发标情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinReportBorrow.do")
	public void exportFinReportBorrow(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		
		String month = request.getParameter("month");

		try {
			exportHandler.exportReportBorrowList(month, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 某月秒标情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinReportSecond.do")
	public void exportFinReportSecond(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		
		String month = request.getParameter("month");

		try {
			exportHandler.exportReportSecondList(month, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 某个标中投资人相关信息，用于归档
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportFinReportFiling.do")
	public void exportFinReportFiling(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/csv;charset=UTF-8");
		String id = request.getParameter("id");

		try {
			exportHandler.exportFinReportFiling(id, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
}
