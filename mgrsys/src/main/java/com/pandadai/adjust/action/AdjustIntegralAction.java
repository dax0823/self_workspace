/**
 * 
 */
package com.pandadai.adjust.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.adjust.handler.AdjustIntegralHandler;
import com.pandadai.adjust.vo.AdjustIntegralCustomerVO;
import com.pandadai.adjust.vo.AdjustIntegralLogVO;
import com.pandadai.common.utils.Constants;
import com.pandadai.common.vo.ResultVO;

/**
 * 数据调整-投标积分修正
 * @author 仵作
 * 2014-10-9 下午7:22:54
 */
@Controller
public class AdjustIntegralAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AdjustIntegralHandler adjIntegralHandler;
	
	/**
	 * 查询客户积分列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showAdjIntegralCustomer.do")
	public void showAdjIntegralCustomer(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String userName = request.getParameter("userName");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<AdjustIntegralCustomerVO> lst = null; 
		try {
			//获取所有已结款总额
			lst = adjIntegralHandler.queryIntegralCustomer(startDate, endDate, userName);
			
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
			logger.debug("showAdjIntegralCustomer.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 查询客户积分日志
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showAdjIntegralLog.do")
	public void showAdjIntegralLog(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<AdjustIntegralLogVO> lst = null; 
		try {
			//获取所有已结款总额
			lst = adjIntegralHandler.queryIntegralLog(userId);
			
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
			logger.debug("showAdjIntegralLog.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 修改当前客户积分
	 * @param request
	 * @param response
	 */
	@RequestMapping("/modifyIntegral.do")
	public void modifyIntegral(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		String adjNum = request.getParameter("adjNum");
		String description = request.getParameter("description");
		String ip = request.getRemoteAddr();
		
		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		boolean flag = false; 
		try {
			//获取所有已结款总额
			flag = adjIntegralHandler.updateIntegral(userId, Integer.parseInt(adjNum), description, ip);
			
			pw = response.getWriter();
			if (flag) {
				res.setCode(Constants.SUCC_CODE);
				res.setMsg(Constants.SUCC_MSG);
			} else {
				res.setCode(Constants.SYS_ERROR_CODE);
				res.setMsg(Constants.SYS_ERROR_MSG);
			}
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
			logger.debug("modifyIntegral.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
