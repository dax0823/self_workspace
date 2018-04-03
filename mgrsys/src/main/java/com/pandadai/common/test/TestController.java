package com.pandadai.common.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.bean.AdminUserBean;
import com.pandadai.common.dao.IAdminDao;
import com.pandadai.common.dao.impl.AdminDaoImpl;

@Controller
public class TestController {
	@RequestMapping("/test.do")
	public void login(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html; charset=UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		Entity ent  = new Entity();
		ent.setId(id);
		ent.setName(name);
		
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String tmp = "{\"id\":\"" + ent.getId() + "\", \"name\"：\"" + ent.getName() + "\"}";
			pw.print(tmp);
			System.out.println(this.getClass().getName() + ": tmp = " + tmp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("main is begin.");

		ApplicationContext aContext = new ClassPathXmlApplicationContext("file:D:/work/MyEclipse10/Workspaces/pandadai/src/main/webapp/WEB-INF/applicationContext.xml");
		IAdminDao login = (AdminDaoImpl) aContext.getBean("loginImpl");
		try {
			List lst = login.queryByCondition("丁丁", "54e23552b007adf95506be46ee25defb", "丁丁");
			System.out.println("lst.size = " + lst.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
//		ERROR: JDWP Unable to get JNI 1.2 environment, jvm->GetEnv() return code = -2
//				JDWP exit error AGENT_ERROR_NO_JNI_ENV(183):  [../../../src/share/back/util.c:820]
//		这个问题是因为debug超时导致的（我猜），
//		你若是debug跑得快貌似不会出现这个问题，
//		若是单步跳转太多次导致时间过程就会出现这个问题。
//Anyway,你可以在main方法里面最后追加System.exit(0);来解决这个问题。
		System.exit(0);
		
		System.out.println("main is end.");
	}

}
