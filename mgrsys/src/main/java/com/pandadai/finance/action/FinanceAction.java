package com.pandadai.finance.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.common.utils.Constants;
import com.pandadai.common.vo.ResultVO;
import com.pandadai.finance.handler.InpourCapitalChangeHandler;
import com.pandadai.finance.handler.InpourContinueHandler;
import com.pandadai.finance.handler.InpourDetailHandler;
import com.pandadai.finance.handler.InpourHomeHandler;
import com.pandadai.finance.handler.InpourReviseHandler;
import com.pandadai.finance.handler.InpourRewardHandler;
import com.pandadai.finance.handler.InpourWithdrawHandler;
import com.pandadai.finance.handler.InvestAdminUserHandler;
import com.pandadai.finance.handler.InvestBorroworHandler;
import com.pandadai.finance.handler.InvestCustomerHandler;
import com.pandadai.finance.handler.InvestHomeHandler;
import com.pandadai.finance.handler.InvestRemindHandler;
import com.pandadai.finance.handler.ReportBorrowHandler;
import com.pandadai.finance.handler.ReportFilingHandler;
import com.pandadai.finance.handler.ReportHomeHandler;
import com.pandadai.finance.handler.TopHomeHandler;
import com.pandadai.finance.vo.InpourCapitalChangeVO;
import com.pandadai.finance.vo.InpourContinueVO;
import com.pandadai.finance.vo.InpourDetailVO;
import com.pandadai.finance.vo.InpourHomeInpourVO;
import com.pandadai.finance.vo.InpourHomeVO;
import com.pandadai.finance.vo.InpourPendingWithdrawVO;
import com.pandadai.finance.vo.InpourReviseVO;
import com.pandadai.finance.vo.InpourRewardCusVO;
import com.pandadai.finance.vo.InpourRewardVO;
import com.pandadai.finance.vo.InpourWithdrawVO;
import com.pandadai.finance.vo.InvestAdminUserVO;
import com.pandadai.finance.vo.InvestBorroworDetailVO;
import com.pandadai.finance.vo.InvestBorroworVO;
import com.pandadai.finance.vo.InvestCustomerBackCapitalVO;
import com.pandadai.finance.vo.InvestCustomerInfoVO;
import com.pandadai.finance.vo.InvestCustomerInpourVO;
import com.pandadai.finance.vo.InvestCustomerInterestVO;
import com.pandadai.finance.vo.InvestCustomerInvestVO;
import com.pandadai.finance.vo.InvestCustomerUncollectedDetailVO;
import com.pandadai.finance.vo.InvestCustomerVO;
import com.pandadai.finance.vo.InvestHomeDetailVO;
import com.pandadai.finance.vo.InvestHomeSumInterestVO;
import com.pandadai.finance.vo.InvestHomeVO;
import com.pandadai.finance.vo.InvestRemindCapitalDetailVO;
import com.pandadai.finance.vo.InvestRemindCapitalVO;
import com.pandadai.finance.vo.InvestRemindDetailCustomerInfoVO;
import com.pandadai.finance.vo.InvestRemindInterestDetailVO;
import com.pandadai.finance.vo.InvestRemindInterestVO;
import com.pandadai.finance.vo.InvestRemindVO;
import com.pandadai.finance.vo.ReportBorrowVO;
import com.pandadai.finance.vo.ReportFilingBorrowVO;
import com.pandadai.finance.vo.ReportFilingVO;
import com.pandadai.finance.vo.ReportHomeVO;
import com.pandadai.finance.vo.ReportSecondVO;
import com.pandadai.finance.vo.TopBorrowVO;
import com.pandadai.finance.vo.TopInpourVO;
import com.pandadai.finance.vo.TopIntegralVO;
import com.pandadai.finance.vo.TopInvestedVO;
import com.pandadai.finance.vo.TopUncollectedVO;
import com.pandadai.finance.vo.TopWithdrawVO;

/**
 * 财务模块统一入口
 * 
 * @author 仵作
 * 
 */
@Controller
public class FinanceAction {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private InpourHomeHandler inpourHomeHandler;
	@Autowired
	private InpourDetailHandler inpourDetailHandler;
	@Autowired
	private InpourRewardHandler inpourRewardHandler;
	@Autowired
	private InpourWithdrawHandler inpourWithdrawHandler;
	@Autowired
	private InpourReviseHandler inpourReviseHandler;
	@Autowired
	private InpourContinueHandler inpourContinueHandler;
	@Autowired
	private InpourCapitalChangeHandler inpourCapitalChangeHandler;
	
	@Autowired
	private InvestHomeHandler investHomeHandler;
	@Autowired
	private InvestCustomerHandler investCustomerHandler;
	@Autowired
	private InvestAdminUserHandler investAdminUserHandler;
	@Autowired
	private InvestBorroworHandler investBorroworHandler;
	@Autowired
	private InvestRemindHandler investRemindHandler;
	@Autowired
	private TopHomeHandler topHomeHandler;
	@Autowired
	private ReportHomeHandler reportHomeHandler;
	@Autowired
	private ReportBorrowHandler reportBorrowHandler;
	@Autowired
	private ReportFilingHandler reportFilingHandler;
	
	/**
	 * 查询总览页-主列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourHome.do")
	public void showFinInpourHome(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourHomeVO> lst = null;
		try {
			lst = inpourHomeHandler.getHomeList();
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
//			res.setObj(lst);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
//		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourHome.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询总览页-按天统计列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourHomeEveryDay.do")
	public void showFinInpourHomeEveryDay(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String way = request.getParameter("way");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourHomeVO> lst = null;
		try {
//			lst = inpourHomeHandler.getHomeEveryDayList();
			lst = inpourHomeHandler.getHomeEveryDayList(startDate, endDate, way);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourHomeEveryDay.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询总览页-按月统计列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourHomeEveryMonth.do")
	public void showFinInpourHomeEveryMonth(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String way = request.getParameter("way");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourHomeVO> lst = null;
		try {
			lst = inpourHomeHandler.getHomeEveryMonthList(startDate, endDate, way);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourHomeEveryMonth.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询当天客户的充值情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourCurrDate.do")
	public void showFinInpourCurrDate(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourHomeInpourVO> lst = null;
		try {
			lst = inpourHomeHandler.queryInpourCurrDate();
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourCurrDate.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询最近 7 天客户的充值情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourLast7Day.do")
	public void showFinInpourLast7Day(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourHomeInpourVO> lst = null;
		try {
			lst = inpourHomeHandler.queryInpourLast7Day();
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourLast7Day.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询最近一个月客户的充值情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourLastMonth.do")
	public void showFinInpourLastMonth(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourHomeInpourVO> lst = null;
		try {
			lst = inpourHomeHandler.queryInpourLastMonth();
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourLastMonth.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 获取某个时间段内的所有客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourDetailAll.do")
	public void showFinInpourDetailAll(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourDetailVO> lst = null;
		try {
			lst = inpourDetailHandler.queryInpourAll(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourDetailAll.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 获取某个时间段内 baofoo 客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourDetailBaofoo.do")
	public void showFinInpourDetailBaofoo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourDetailVO> lst = null;
		try {
			lst = inpourDetailHandler.queryInpourByBaofoo(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourDetailBaofoo.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 获取某个时间段内的 easypay 客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourDetailEasypay.do")
	public void showFinInpourDetailEasypay(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourDetailVO> lst = null;
		try {
			lst = inpourDetailHandler.queryInpourByEasypay(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourDetailEasypay.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 获取某个时间段内的线下客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourDetailOff.do")
	public void showFinInpourDetailOff(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourDetailVO> lst = null;
		try {
			lst = inpourDetailHandler.queryInpourByOff(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourDetailOff.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 获取某个时间段内的某个客户的单笔充值记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourDetailCus.do")
	public void showFinInpourDetailCustomer(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
//		String startDate = request.getParameter("startDate");
//		String endDate = request.getParameter("endDate");
		String inpourTime = request.getParameter("inpourTime");
		String way = request.getParameter("way");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourDetailVO> lst = null;
		try {
//			lst = inpourDetailHandler.queryInpourCustomer(id, startDate, endDate, way);
			lst = inpourDetailHandler.queryInpourCustomer(id, inpourTime, way);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourDetailCustomer.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户的线下充值奖励记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourReward.do")
	public void showFinInpourReward(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourRewardVO> lst = null;
		try {
			lst = inpourRewardHandler.queryRewardList(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourReward.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询单个客户的线下充值奖励记录明细
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourRewardCus.do")
	public void showFinInpourRewardCustomer(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourRewardCusVO> lst = null;
		try {
			lst = inpourRewardHandler.queryCusRewardList(id, startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourRewardCustomer.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户的提现记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourWithdraw.do")
	public void showFinInpourWithdraw(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String type = request.getParameter("type");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourWithdrawVO> lst = null;
		try {
			lst = inpourWithdrawHandler.queryWithdrawList(startDate, endDate, type);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourWithdraw.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询当前所有待审核提现手续费列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourPendingWithdraw.do")
	public void showFinInpourPendingWithdraw(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourPendingWithdrawVO> lst = null;
		try {
			lst = inpourWithdrawHandler.queryPendingWithdrawList();
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourPendingWithdraw.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询管理员手动修改的记录（默认搜索最近一个月内数据）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourRevise.do")
	public void showFinInpourRevise(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourReviseVO> lst = null;
		try {
			lst = inpourReviseHandler.queryReviseList(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourRevise.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询续投奖励记录（默认搜索最近一个月内数据）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInpourContinue.do")
	public void showFinInpourContinue(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourContinueVO> lst = null;
		try {
			lst = inpourContinueHandler.queryContinueList(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourContinue.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	@RequestMapping("/showFinInpourCapitalChange.do")
	public void showFinInpourCapitalChange(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InpourCapitalChangeVO> lst = null;
		try {
			lst = inpourCapitalChangeHandler.queryInvestCustomer(userName, realName, startDate, endDate);
			lst = inpourCapitalChangeHandler.queryInvestCustomer(userName, realName, startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInpourCapitalChange.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询投资总览页
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestHome.do")
	public void showFinInvestHome(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestHomeVO> lst = null;
		try {
			lst = investHomeHandler.queryInvestHomeList(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestHome.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询投资总览页中某个投标的明细信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestHomeDetail.do")
	public void showFinInvestHomeDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String borrowId = request.getParameter("id");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestHomeDetailVO> lst = null;
		try {
			lst = investHomeHandler.queryInvestHomeDetailByBorrowId(borrowId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestHomeDetail.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 列出某个投标中需要返还每个人利息的情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestHomeSumInterest.do")
	public void showFinInvestHomeSumInterest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String borrowId = request.getParameter("id");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestHomeSumInterestVO> lst = null;
		try {
			lst = investHomeHandler.queryInvestHomeSumInterestByBorrorwId(borrowId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestHomeSumInterest.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户相关的统计信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomer.do")
	public void showFinInvestCustomer(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String recommendName = request.getParameter("recommendName");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestCustomerVO> lst = null;
		try {
//			lst = investCustomerHandler.queryInvestCustomer();
			lst = investCustomerHandler.queryInvestCustomer(userName, realName, recommendName, startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
//		System.out.println("@@@ time end. 耗时：" + (new Date().getTime() - begin) + "ms.");
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomer.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户个人的信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomerInfo.do")
	public void showFinInvestCustomerInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		InvestCustomerInfoVO info = null;
		try {
			info = investCustomerHandler.queryInvestCustomerInfo(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setObj(info);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomerInfo.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户的待收详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomerUncollectedDetail.do")
	public void showFinInvestCustomerUncollectedDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestCustomerUncollectedDetailVO> lst = null;
		try {
			lst = investCustomerHandler.queryInvestCustomerUncollectedDetail(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomerUncollectedDetail.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户充值记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomerInpour.do")
	public void showFinInvestCustomerInpour(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestCustomerInpourVO> lst = null;
		try {
			lst = investCustomerHandler.queryInvestCustomerInpour(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomerInpour.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户投标记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomerInvest.do")
	public void showFinInvestCustomerInvest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestCustomerInvestVO> lst = null;
		try {
			lst = investCustomerHandler.queryInvestCustomerInvest(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomerInvest.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户利息发放记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomerInterest.do")
	public void showFinInvestCustomerInterest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestCustomerInterestVO> lst = null;
		try {
			lst = investCustomerHandler.queryInvestCustomerInterest(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomerInterest.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户回款记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestCustomerBackCapital.do")
	public void showFinInvestCustomerBackCapital(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestCustomerBackCapitalVO> lst = null;
		try {
			lst = investCustomerHandler.queryInvestCustomerBackCapital(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
//		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestCustomerBackCapital.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询管理员信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestAdminUser.do")
	public void showFinInvestAdminUser(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestAdminUserVO> lst = null;
		try {
			lst = investAdminUserHandler.queryAdminUserList(startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestAdminUser.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询所有借款人借款汇总情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestBorrowor.do")
	public void showFinInvestBorrowor(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestBorroworVO> lst = null;
		try {
			lst = investBorroworHandler.queryInvestBorroworList();
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
			
			//将当前待收（借款）本金总额度 & 当前待收（借款）利息总额度计算出来
//			float sumCapital = 0f;
//			float sumInterest = 0f;
//			float sumFee = 0f;
//			if (lst != null && lst.size() > 0) {
//				for (int i=0; i<lst.size(); i++) {
//					InvestBorroworVO ivo = lst.get(i);
//					sumCapital += ivo.getSumBorrow();
//					sumInterest += ivo.getSumInterest();
//					sumFee += ivo.getSumFee();
//				}
//			}
//			float[] sum = {sumCapital, sumInterest, sumFee};
//			res.setObj(sum);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestBorrowor.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个借款人所借款项明细
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestBorroworDetail.do")
	public void showFinInvestBorroworDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestBorroworDetailVO> lst = null;
		try {
			lst = investBorroworHandler.queryInvestBorroworDetailList(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestBorroworDetail.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某月内的每天提醒信息
	 * @param request
	 * @param response
	 */
//	@ResponseBody
	@RequestMapping("/showFinInvestRemind.do")
	public void showFinInvestRemind(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String date = request.getParameter("date");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestRemindVO> lst = null;
		try {
			lst = investRemindHandler.queryRemindList(date);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setObj(lst);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestRemind.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某天内所有投标的本金回款信息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestRemindCapitalOneDay.do")
	public void showFinInvestRemindCapitalOneDay(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String date = request.getParameter("date");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestRemindCapitalVO> lst = null;
		try {
			lst = investRemindHandler.queryRemindBorrowCapitalOneDayList(date);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestRemindCapitalOneDay.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某天内所有投标的利息回款信息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestRemindInterestOneDay.do")
	public void showFinInvestRemindInterestOneDay(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String date = request.getParameter("date");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestRemindInterestVO> lst = null;
		try {
			lst = investRemindHandler.queryRemindBorrowInterestOneDayList(date);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestRemindInterestOneDay.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某天内所有投标的本金回款明细信息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestRemindCapitalDetailOneDay.do")
	public void showFinInvestRemindCapitalDetailOneDay(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String date = request.getParameter("date");
		String borrowId = request.getParameter("borrowId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestRemindCapitalDetailVO> lst = null;
		try {
			lst = investRemindHandler.queryRemindBorrowCapitalDetailOneDayList(date, borrowId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestRemindCapitalDetailOneDay.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某天内所有投标的利息回款信息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showFinInvestRemindInterestDetailOneDay.do")
	public void showFinInvestRemindInterestDetailOneDay(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String date = request.getParameter("date");
		String borrowId = request.getParameter("borrowId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<InvestRemindInterestDetailVO> lst = null;
		try {
			lst = investRemindHandler.queryRemindBorrowInterestDetailOneDayList(date, borrowId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showFinInvestRemindInterestDetailOneDay.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某天内投标回款明细中客户详细信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showRemindBorrowDetailCustomerInfo.do")
	public void showRemindBorrowDetailCustomerInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		InvestRemindDetailCustomerInfoVO vo = null;
		try {
			vo = investRemindHandler.queryRemindBorrowDetailCustomerInfo(userId);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setObj(vo);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showRemindBorrowDetailCustomerInfo.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 *  查询当前所有用户总代收排名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showTopHomeUncollected.do")
	public void showTopHomeUncollected(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String rankNum = request.getParameter("rankNum");	//需显示排名的个数
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<TopUncollectedVO> lst = null;
		try {
			lst = topHomeHandler.queryTopUncollected(Integer.parseInt(rankNum));
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showTopHomeUncollected.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 *  查询当前所有用户累积投资排名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showTopHomeInvested.do")
	public void showTopHomeInvested(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String rankNum = request.getParameter("rankNum");	//需显示排名的个数
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<TopInvestedVO> lst = null;
		try {
			lst = topHomeHandler.queryTopInvested(Integer.parseInt(rankNum));
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showTopHomeInvested.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询当前所有用户累积充值排名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showTopHomeInpour.do")
	public void showTopHomeInpour(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String rankNum = request.getParameter("rankNum");	//需显示排名的个数
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<TopInpourVO> lst = null;
		try {
			lst = topHomeHandler.queryTopInpour(Integer.parseInt(rankNum));
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showTopHomeInpour.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询当前所有用户累积充值排名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showTopHomeWithdraw.do")
	public void showTopHomeWithdraw(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String rankNum = request.getParameter("rankNum");	//需显示排名的个数
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<TopWithdrawVO> lst = null;
		try {
			lst = topHomeHandler.queryTopWithdraw(Integer.parseInt(rankNum));
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showTopHomeWithdraw.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询用户累积借款排名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showTopHomeBorrow.do")
	public void showTopHomeBorrow(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String rankNum = request.getParameter("rankNum");	//需显示排名的个数
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<TopBorrowVO> lst = null;
		try {
			lst = topHomeHandler.queryTopBorrow(Integer.parseInt(rankNum));
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showTopHomeBorrow.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询用户可用积分排名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showTopHomeIntegral.do")
	public void showTopHomeIntegral(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String rankNum = request.getParameter("rankNum");	//需显示排名的个数
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<TopIntegralVO> lst = null;
		try {
			lst = topHomeHandler.queryTopIntegral(Integer.parseInt(rankNum));
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showTopHomeIntegral.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某月运营数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showReportHome.do")
	public void showReportHome(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String month = request.getParameter("month");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		ReportHomeVO vo = null;
		try {
			vo = reportHomeHandler.queryReportInfo(month);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setObj(vo);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showReportHome.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 某月发标情况统计
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showReportBorrow.do")
	public void showReportBorrow(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String month = request.getParameter("month");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<ReportBorrowVO> lst = null;
		try {
			lst = reportBorrowHandler.queryReportBorrowList(month);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showReportBorrow.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 某月秒标情况统计
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showReportSecond.do")
	public void showReportSecond(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String month = request.getParameter("month");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<ReportSecondVO> lst = null;
		try {
			lst = reportBorrowHandler.queryReportSecondList(month);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showReportSecond.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 根据 id 查询发标情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showReportBorrowListById.do")
	public void showReportBorrowListById(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<ReportFilingBorrowVO> lst = null;
		try {
			lst = reportFilingHandler.queryReportBorrowList(id);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showReportBorrowListById.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 根据条件查询发标情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showReportBorrowListByCondition.do")
	public void showReportBorrowListByCondition(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userName = request.getParameter("userName");
		String borrowName = request.getParameter("borrowName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<ReportFilingBorrowVO> lst = null;
		try {
			lst = reportFilingHandler.queryReportBorrowList(userName, borrowName, startDate, endDate);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showReportBorrowListByCondition.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个标的投资人相关信息，以便归档
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showReportFiling.do")
	public void showReportFiling(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<ReportFilingVO> lst = null;
		try {
			lst = reportFilingHandler.queryReportFiling(id);
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(lst);
		} catch (Exception e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("showReportFiling.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
