/**
 * 
 */
package com.pandadai.biz.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.biz.handler.BizHomeHandler;
import com.pandadai.biz.handler.BizLotteryLogHandler;
import com.pandadai.biz.vo.BizHomeAutoDetailVO;
import com.pandadai.biz.vo.BizHomeBalanceDetailVO;
import com.pandadai.biz.vo.BizHomeCusotmerInfoBalanceVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoBaseVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoMoneyVO;
import com.pandadai.biz.vo.BizHomeInvestVO;
import com.pandadai.biz.vo.BizHomeMoneyLogVO;
import com.pandadai.biz.vo.BizHomeVO;
import com.pandadai.biz.vo.BizLotteryLogVO;
import com.pandadai.common.utils.Constants;
import com.pandadai.common.vo.ResultVO;
import com.pandadai.finance.handler.InpourHomeHandler;
import com.pandadai.finance.vo.InpourHomeVO;

/**
 * 业务模块统一入口
 * @author 仵作
 * 2014-9-14 下午4:40:04
 */
@Controller
public class BizAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private BizHomeHandler bizHomeHandler;
	
	@Autowired
	private BizLotteryLogHandler bizLotteryLogDaoHandler;
	
	/**
	 * 查询总览信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHome.do")
	public void showBizHome(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizHomeVO vo = new BizHomeVO();
		try {
			//获取所有已结款总额
			vo.setBorrowMoney(bizHomeHandler.querySumBorrowMoney());
			//获取当前可用总额
			vo.setUsableMoney(bizHomeHandler.queryUsableMoney());
			//获取自动投标总人数
//			vo.setAutoNum(bizHomeHandler.queryAutoNum());
			//获取自动投标总金额
//			vo.setAutoMoney(bizHomeHandler.querySumAutoMoney());
			Object[] obj = bizHomeHandler.queryAutoInfo();
			vo.setAutoNum((Integer) obj[0]);
			vo.setAutoMoney((Float) obj[1]);
			
			//获取当天充值总金额
			vo.setInpourMoney(bizHomeHandler.querySumInpourMoneyCurrDay());
			//获取当天充值总金额
			vo.setWithdrawMoney(bizHomeHandler.querySumWithdrawMoneyCurrDay());
			//获取当天进出值
			vo.setCurrDayBalance(bizHomeHandler.queryBalanceCurrDay());
			//获取昨天进出值
			vo.setLastDayBalance(bizHomeHandler.queryBalanceLastDay());
			//获取本周进出值
			vo.setCurrWeekBalance(bizHomeHandler.queryBalanceCurrWeek());
			//获取上周进出值
			vo.setLastWeekBalance(bizHomeHandler.queryBalanceLastWeek());
			
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
			logger.debug("showBizHome.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询自动投标排队详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeAutoDetail.do")
	public void showBizHomeAutoDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<BizHomeAutoDetailVO> lst = null; 
		try {
			//获取所有已结款总额
			lst = bizHomeHandler.queryAutoDetailList();
			
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
			logger.debug("showBizHomeAutoDetail.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个时间的充值明细记录详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeInpourMoneyDetail.do")
	public void showBizHomeInpourMoneyDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String type = request.getParameter("type");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<BizHomeBalanceDetailVO> lst = null; 
		try {
//			today、yesterday、currWeek、lastWeek
			if (type.equalsIgnoreCase("yesterday")) {
				lst = bizHomeHandler.queryInpourMoneyDetailLastDay();
			} else if (type.equalsIgnoreCase("currWeek")) {
				lst = bizHomeHandler.queryInpourMoneyDetailCurrWeek();
			} else if (type.equalsIgnoreCase("lastWeek")) {
				lst = bizHomeHandler.queryInpourMoneyDetailLastWeek();
			} else {
				lst = bizHomeHandler.queryInpourMoneyDetailCurrDay();
			}
			
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
			logger.debug("showBizHomeInpourMoneyDetail.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个时间的提现明细记录详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeWithdrawMoneyDetail.do")
	public void showBizHomeWithdrawMoneyDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String type = request.getParameter("type");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<BizHomeBalanceDetailVO> lst = null; 
		try {
//			today、yesterday、currWeek、lastWeek
			if (type.equalsIgnoreCase("yesterday")) {
				lst = bizHomeHandler.queryWithdrawMoneyDetailLastDay();
			} else if (type.equalsIgnoreCase("currWeek")) {
				lst = bizHomeHandler.queryWithdrawMoneyDetailCurrWeek();
			} else if (type.equalsIgnoreCase("lastWeek")) {
				lst = bizHomeHandler.queryWithdrawMoneyDetailLastWeek();
			} else {
				lst = bizHomeHandler.queryWithdrawMoneyDetailCurrDay();
			}
			
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
			logger.debug("showBizHomeWithdrawMoneyDetail.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户基础信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoBase.do")
	public void showBizHomeCustomerInfoBase(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizHomeCustomerInfoBaseVO vo = null;
		try {
			vo = bizHomeHandler.queryCustomerInfoBase(userId);
			
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
			logger.debug("showBizHomeCustomerInfoBase.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户当前资金状况信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoMoney.do")
	public void showBizHomeCustomerInfoMoney(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizHomeCustomerInfoMoneyVO vo = null;
		try {
			vo = bizHomeHandler.queryCustomerInfoMoney(userId);
			
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
			logger.debug("showBizHomeCustomerInfoMoney.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户充值概要信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoInpour.do")
	public void showBizHomeCustomerInfoInpour(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizHomeCusotmerInfoBalanceVO vo = null;
		try {
			vo = bizHomeHandler.queryCustomerInfoInpour(userId);
			
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
			logger.debug("showBizHomeCustomerInfoInpour.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户提现概要信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoWithdraw.do")
	public void showBizHomeCustomerInfoWithdraw(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizHomeCusotmerInfoBalanceVO vo = null;
		try {
			vo = bizHomeHandler.queryCustomerInfoWithdraw(userId);
			
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
			logger.debug("showBizHomeCustomerInfoWithdraw.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户投标信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoInvest.do")
	public void showBizHomeCustomerInfoInvest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<BizHomeInvestVO> lst = null;
		try {
			lst = bizHomeHandler.queryCustomerInfoInvest(userId);
			
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
			logger.debug("showBizHomeCustomerInfoInvest.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户资金变动记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoMoneyLog.do")
	public void showBizHomeCustomerInfoMoneyLog(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<BizHomeMoneyLogVO> lst = null;
		try {
			lst = bizHomeHandler.queryCustomerInfoMoneyLog(userId);
			
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
			logger.debug("showBizHomeCustomerInfoMoneyLog.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询某个用户资金变动记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizHomeCustomerInfoIDCardPath.do")
	public void showBizHomeCustomerInfoIDCardPath(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		String path = null;
		try {
			path = bizHomeHandler.getIDCardImgPath(userId);
			
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setObj(path);
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
			logger.debug("showBizHomeCustomerInfoIDCardPath.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询抽奖记录信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showBizLotteryLog.do")
	public void showBizLotteryLog(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String productName = request.getParameter("productName");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<BizLotteryLogVO> vo = null;
		try {
			vo = bizLotteryLogDaoHandler.queryIntegralLotteryLog(userName, realName, productName, startDate, endDate);
			
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setData(vo);
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
			logger.debug("showBizLotteryLog.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
