/** 
 * Project Name: steak 
 * File Name: GoodsController.java 
 * Package Name: com.panda.steak.goods.controller 
 * Date: 2016年9月5日上午11:31:10 
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
import com.panda.steak.generator.model.Goods;
import com.panda.steak.goods.service.IGoods;
import com.panda.steak.utils.Constants;
import com.panda.steak.utils.vo.Page;
import com.panda.steak.utils.vo.ResultVO;
import com.panda.steak.utils.vo.model.GoodsExt;

/** 
 * ClassName: GoodsController
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月5日 上午11:31:10
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private IGoods goods;

	@RequestMapping("/queryList")
	public void queryBizTypeByParentId(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String index = request.getParameter("index");
		String size = request.getParameter("size");
		if (StringUtils.isBlank(index)) {
			index = "1";
		}
		if (StringUtils.isBlank(size)) {
			size = "10";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<GoodsExt> lst = null; 

		try {
			Page page = new Page();
			page.setIndex(Integer.parseInt(index) - 1);
			page.setSize(Integer.parseInt(size));
			
			lst = this.goods.queryList(page);
			pw = response.getWriter();
			
			res.setCode(Constants.SUCC_CODE);
			res.setMsg(Constants.SUCC_MSG);
			res.setPage(page);
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
	
	@RequestMapping("/addGoodsInfo")
	public void addGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String name = request.getParameter("name");
		String batch = request.getParameter("batch");
		String serial = request.getParameter("serial");
		String typeId = request.getParameter("typeId");
		String weight = request.getParameter("weight");
		String price = request.getParameter("price");
		String address = request.getParameter("address");
		String stock = request.getParameter("stock");
		String sum = request.getParameter("sum");
		String description = request.getParameter("description");
		
		if (StringUtils.isBlank(name)) {
			return;
		}
		if (StringUtils.isBlank(typeId)) {
			return;
		}
		if (StringUtils.isBlank(stock)) {
			stock = "0";
		}
		if (StringUtils.isBlank(sum)) {
			sum = "0";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		Goods param = new Goods(); 
		
		try {
			param.setName(name);
			param.setBatch(batch);
			param.setSerial(serial);
			param.setTypeId(Integer.parseInt(typeId));
			param.setWeight(Integer.parseInt(weight));
			param.setPrice(Float.parseFloat(price));
			param.setAddress(address);
			param.setStock(Integer.parseInt(stock));
			param.setSum(Integer.parseInt(sum));
			param.setDescription(description);
			int result = goods.insertSelective(param);
			
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
	
	@RequestMapping("/deleteGoodsInfo")
	public void deleteGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return;
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		
		try {
			int result = goods.deleteByPrimaryKey(Integer.parseInt(id));
			
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
	
	@RequestMapping("/modifyGoodsInfo")
	public void modifyGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String batch = request.getParameter("batch");
		String serial = request.getParameter("serial");
		String typeId = request.getParameter("typeId");
		String weight = request.getParameter("weight");
		String price = request.getParameter("price");
		String address = request.getParameter("address");
		String stock = request.getParameter("stock");
		String sum = request.getParameter("sum");
		String description = request.getParameter("description");
		
		if (StringUtils.isBlank(id)) {
			return;
		}
		if (StringUtils.isBlank(name)) {
			return;
		}
		if (StringUtils.isBlank(typeId)) {
			return;
		}
		if (StringUtils.isBlank(stock)) {
			stock = "0";
		}
		if (StringUtils.isBlank(sum)) {
			sum = "0";
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		Goods param = new Goods(); 
		
		try {
			param.setId(Integer.parseInt(id));
			param.setName(name);
			param.setBatch(batch);
			param.setSerial(serial);
			param.setTypeId(Integer.parseInt(typeId));
			param.setWeight(Integer.parseInt(weight));
			param.setPrice(Float.parseFloat(price));
			param.setAddress(address);
			param.setStock(Integer.parseInt(stock));
			param.setSum(Integer.parseInt(sum));
			param.setDescription(description);
			int result = goods.updateByPrimaryKeySelective(param);
			
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
