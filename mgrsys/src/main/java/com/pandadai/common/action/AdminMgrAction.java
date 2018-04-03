package com.pandadai.common.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.common.dao.impl.AdminDaoImpl;
import com.pandadai.common.utils.Constants;
import com.pandadai.common.vo.AdminUserVO;
import com.pandadai.common.vo.LeftTreeVO;
import com.pandadai.common.vo.ResultVO;

@Controller
public class AdminMgrAction {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AdminDaoImpl impl;
	
	/**
	 * 查出所有管理员
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryall.do")
	public void queryAllAdmins(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
//		String username = request.getParameter("username");

		PrintWriter pw = null;
		ResultVO res = new ResultVO();
		try {
			pw = response.getWriter();
		} catch (IOException e) {
//			res.setCode(Constants.SYS_ERROR_CODE);
//			res.setMsg(Constants.SYS_ERROR_MSG);
			logger.error(e);
			e.printStackTrace();
		} ;

		LeftTreeVO tree = new LeftTreeVO();
		if (res.getCode() >=0 && res.getCode() < 100) {
			try {
				List<AdminUserVO> lst = impl.query();
				if (lst != null && lst.size() >= 0) {	//当查询的结果仅有一条时，才算是登录成功
//					res.setCode(Constants.SUCC_CODE);
//					res.setMsg(Constants.SUCC_MSG);
//					res.setObj(lst);
					tree.setSuccess(true);
					List<LeftTreeVO.TreeVO> voLst = new ArrayList();
					for (int i=0; i<lst.size(); i++) {
						AdminUserVO admin = lst.get(i);
						LeftTreeVO.TreeVO tr = new LeftTreeVO.TreeVO();
						tr.setId(admin.getId());
						tr.setName(admin.getUsername());
						tr.setLeaf(true);
						voLst.add(tr);
					}
					tree.setChildren(voLst);
					System.out.println("tree!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					logger.info(Constants.SUCC_MSG);
				}
			} catch (Exception e) {
//				res.setCode(Constants.SYS_ERROR_CODE);
//				res.setMsg(Constants.SYS_ERROR_MSG);
				logger.error(Constants.SYS_ERROR_MSG);
				e.printStackTrace();
			} finally {
			}
		}
		
		//将结果输出
//		JSONObject json= JSONObject.fromObject(res);
		JSONObject json= JSONObject.fromObject(tree);
		System.out.println("json = " + json.toString());
		if (pw != null) {
			pw.print(json.toString());
			logger.debug("login.json: " + json.toString() + ".");
			pw.flush();
			pw.close();
		}
	}
}
