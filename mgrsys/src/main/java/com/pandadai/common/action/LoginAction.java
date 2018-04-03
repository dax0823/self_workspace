package com.pandadai.common.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.common.dao.impl.AdminDaoImpl;
import com.pandadai.common.utils.Constants;
import com.pandadai.common.utils.EncryptMD5;
import com.pandadai.common.utils.SendMsgUtil;
import com.pandadai.common.vo.AdminUserVO;
import com.pandadai.common.vo.ResultVO;

@Controller
public class LoginAction {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private AdminDaoImpl impl;
	
	@RequestMapping("/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String userword = request.getParameter("userword");
		String captchaCode = request.getParameter("captcha");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
//		String code = (String) request.getSession().getAttribute(Constants.CAPTCHA_LOGIN_STR);

		HttpSession session = request.getSession();
		System.out.println("@@@ session.flag = " + session.isNew());
		System.out.println("@@@ session.id = " + session.getId());
		
		String code = (String) request.getSession().getAttribute(Constants.CAPTCHA_LOGIN_STR);
		String msg = (String) request.getSession().getAttribute("testLoginSms");
		System.out.println("@@@ session.code = " + code);
		System.out.println("@@@ session.msg = " + msg);
		
		//参数合法性判断
		if (StringUtils.isBlank(captchaCode)) {	//验证码为空
			res.setCode(Constants.ERROR_LOGIN_CAPTCHA_NULL_CODE);
			res.setMsg(Constants.ERROR_LOGIN_CAPTCHA_NULL_MSG);
			logger.error(Constants.ERROR_LOGIN_CAPTCHA_NULL_MSG);
		} else if (!captchaCode.equalsIgnoreCase(code)) {
		 	//验证码有误
			res.setCode(Constants.ERROR_LOGIN_CAPTCHA_ERROR_CODE);
			res.setMsg(Constants.ERROR_LOGIN_CAPTCHA_ERROR_MSG + "; captcha_code = " + code);
			logger.error(Constants.ERROR_LOGIN_CAPTCHA_ERROR_MSG + "; captcha_code = " + code);
		} else if (StringUtils.isBlank(username)) {
			res.setCode(Constants.ERROR_LOGIN_USERNAME_NULL_CODE);
			res.setMsg(Constants.ERROR_LOGIN_USERNAME_NULL_MSG);
			logger.error(Constants.ERROR_LOGIN_USERNAME_NULL_MSG);
		} else if (StringUtils.isBlank(pwd)) {
			res.setCode(Constants.ERROR_LOGIN_PWD_NULL_CODE);
			res.setMsg(Constants.ERROR_LOGIN_PWD_NULL_MSG);
			logger.error(Constants.ERROR_LOGIN_PWD_NULL_MSG);
		} else if (StringUtils.isBlank(userword)) {
			res.setCode(Constants.ERROR_LOGIN_USERWORD_NULL_CODE);
			res.setMsg(Constants.ERROR_LOGIN_USERWORD_NULL_MSG);
			logger.error(Constants.ERROR_LOGIN_USERWORD_NULL_MSG);
		}

		try {
			pw = response.getWriter();
		} catch (IOException e) {
//			res.setCode(Constants.SYS_ERROR_CODE);
//			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;
		
		if (res.getCode() >=0 && res.getCode() < 100) {
			try {
				List<AdminUserVO> lst = impl.queryByCondition(username, EncryptMD5.getMd5String(pwd), userword);
				if (lst != null && lst.size() == 1) {	//当查询的结果仅有一条时，才算是登录成功
					res.setCode(Constants.SUCC_CODE);
					res.setMsg(Constants.SUCC_MSG);
					res.setObj(lst.get(0));
					logger.info(Constants.SUCC_MSG);
				} else {
					res.setCode(Constants.ERROR_LOGIN_PWD_ERROR_CODE);
					res.setMsg(Constants.ERROR_LOGIN_PWD_ERROR_MSG);
					logger.error(Constants.ERROR_LOGIN_PWD_ERROR_MSG);
				}
			} catch (Exception e) {
				res.setCode(Constants.SYS_ERROR_CODE);
				res.setMsg(Constants.SYS_ERROR_MSG);
				logger.error(Constants.SYS_ERROR_MSG);
				e.printStackTrace();
			} finally {
				
			}
		}
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("login.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	@RequestMapping("/loginTest.do")
	public void loginTest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		
		HttpSession session = request.getSession();
		System.out.println("@@@ session.flag = " + session.isNew());
		System.out.println("@@@ session.id = " + session.getId());
//		String code = (String) session.getAttribute(Constants.CAPTCHA_LOGIN_STR);
		
		String key = "testLoginSms";
		String code = "testLogin123";
		session.setAttribute(key, code);
		System.out.println("@@@ session.code = " + session.getAttribute(key));
		
		SendMsgUtil msg = new SendMsgUtil();
		
		try {
//			msg.sendMsg("13501320540", code);
			pw = response.getWriter();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} ;
		
		res.setCode(Constants.SUCC_CODE);
		res.setMsg(Constants.SUCC_MSG);
		res.setObj("testLogin");
		logger.info(Constants.SUCC_MSG);
		
		//将结果输出
		JSONObject json= JSONObject.fromObject(res);
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("loginTest.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
