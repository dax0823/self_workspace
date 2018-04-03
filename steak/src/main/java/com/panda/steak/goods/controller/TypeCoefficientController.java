/** 
 * Project Name: steak 
 * File Name: TypeCoefficientController.java 
 * Package Name: com.panda.steak.goods.controller 
 * Date: 2016年9月4日下午5:08:26 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.controller;  

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.panda.steak.generator.model.TypeCoefficient;
import com.panda.steak.goods.service.ITypeCoefficient;
import com.panda.steak.utils.Constants;
import com.panda.steak.utils.vo.ResultVO;

/** 
 * ClassName: TypeCoefficientController
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月4日 下午5:08:26
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Controller
@RequestMapping("/coefficient")
public class TypeCoefficientController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private ITypeCoefficient typeCoefficient;

	@RequestMapping("/addCoefficientInfo")
	public void addCoefficientInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String typeId = request.getParameter("typeId");
		String coefficient = request.getParameter("coefficient");
		if (StringUtils.isBlank(typeId)) {
			return;
		}
		if (StringUtils.isBlank(coefficient)) {
			coefficient = "100";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		TypeCoefficient param = new TypeCoefficient(); 
		
		try {
			param.setTypeId(Integer.parseInt(typeId));
			param.setCoefficient(Integer.parseInt(coefficient));
			int result = typeCoefficient.insertSelective(param);
//			System.out.println("bizType.addBizTypeInfo.result = " + result);
			
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
		} catch (IOException e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		}
		
		JSONObject json =(JSONObject)  JSONObject.toJSON(res);
		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ".json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	@RequestMapping("/modifyCoefficientInfo")
	public void modifyCoefficientInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		String typeId = request.getParameter("typeId");
		String coefficient = request.getParameter("coefficient");
		if (StringUtils.isBlank(id)) {
			return;
		}
		if (StringUtils.isBlank(typeId)) {
			return;
		}
		if (StringUtils.isBlank(coefficient)) {
			coefficient = "100";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		TypeCoefficient param = new TypeCoefficient(); 
		
		try {
			param.setId(Integer.parseInt(id));
			param.setTypeId(Integer.parseInt(typeId));
			param.setCoefficient(Integer.parseInt(coefficient));
			int result = typeCoefficient.insertSelective(param);
//			System.out.println("bizType.addBizTypeInfo.result = " + result);
			
			pw = response.getWriter();
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
		} catch (IOException e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		}
		
		JSONObject json =(JSONObject)  JSONObject.toJSON(res);
		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ".json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
