/** 
 * Project Name: steak 
 * File Name: BizTypeController.java 
 * Package Name: com.panda.steak.goods.controller 
 * Date: 2016年9月1日下午3:59:29 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.controller;  

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panda.steak.generator.model.BizType;
import com.panda.steak.goods.service.IBizType;
import com.panda.steak.utils.Constants;
import com.panda.steak.utils.vo.ResultVO;
import com.panda.steak.utils.vo.model.BizTypeExt;

/** 
 * ClassName: BizTypeController
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月1日 下午3:59:29
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Controller
@RequestMapping("/bizType")
public class BizTypeController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private IBizType bizType;

	@RequestMapping("/queryBizTypeByParentId")
	public void queryBizTypeByParentId(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String parentId = request.getParameter("parentId");
		//若未传值，则默认填入 0，即查询最顶级类别
		if (StringUtils.isBlank(parentId) || parentId.equalsIgnoreCase("root")) {
			parentId = "0";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
//		List<BizType> lst = null; 
		List<BizTypeExt> lst = null; 
		
		try {
//			lst = this.bizType.queryListByParentId(Integer.parseInt(parentId));
			lst = this.bizType.queryListByParentIdExt(Integer.parseInt(parentId));
			pw = response.getWriter();
			
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setObj(lst);
		} catch (IOException e) {
			res.setCode(Constants.SYS_ERROR_CODE);
			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		}
		
//		JSONObject json =(JSONObject)  JSONObject.toJSON(lst);
		JSONArray json =(JSONArray)  JSONArray.toJSON(lst);
		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ".json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
	
	@RequestMapping("/addBizTypeInfo")
	public void addBizTypeInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String name = request.getParameter("name");
		String parentId = request.getParameter("parentId");
		String price = request.getParameter("price");
		if (StringUtils.isBlank(name)) {
//			name = "未命名";
			return;
		}
		if (StringUtils.isBlank(parentId) || parentId.equalsIgnoreCase("root")) {
			parentId = "0";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizType param = new BizType(); 
		
		try {
			param.setName(name);
			param.setParentId(Integer.parseInt(parentId));
			param.setPrice(Float.parseFloat(price));
			int result = bizType.insertSelective(param);
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
	
	@RequestMapping("/deleteBizTypeInfo")
	public void deleteBizTypeInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return;
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		
		try {
			int result = bizType.deleteByPrimaryKey(Integer.parseInt(id));
			
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
	
	@RequestMapping("/modifyBizTypeInfo")
	public void modifyBizTypeInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String parentId = request.getParameter("parentId");
		String price = request.getParameter("price");
		if (StringUtils.isBlank(id)) {
			return;
		}
		if (StringUtils.isBlank(name)) {
			return;
		}
		if (StringUtils.isBlank(status)) {
			return;
		}
		if (StringUtils.isBlank(parentId) || parentId.equalsIgnoreCase("root")) {
			parentId = "0";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		BizType param = new BizType(); 
		
		try {
			param.setId(Integer.parseInt(id));
			param.setName(name);
			param.setParentId(Integer.parseInt(parentId));
			param.setStatus(Integer.parseInt(status));
			param.setPrice(Float.parseFloat(price));
			int result = bizType.updateByPrimaryKeySelective(param);
			
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
