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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panda.steak.generator.model.GoodsTransfer;
import com.panda.steak.goods.service.IGoodsTransfer;
import com.panda.steak.utils.Constants;
import com.panda.steak.utils.vo.Page;
import com.panda.steak.utils.vo.ResultVO;
import com.panda.steak.utils.vo.model.GoodsExt;

@Controller
@RequestMapping("/goodsTransfer")
public class GoodsTransferController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private IGoodsTransfer goodsTransfer;

	@RequestMapping("/queryListByGoodsId")
	public void queryListByGoodsId(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String goodsId = request.getParameter("goodsId");
		if (StringUtils.isBlank(goodsId)) {
			return;
		}

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		List<GoodsTransfer> lst = null; 

		try {
			lst = this.goodsTransfer.queryListByGoodsId(Integer.parseInt(goodsId));
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
		
		JSONArray json =(JSONArray)  JSONArray.toJSON(lst);
		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ".json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
