package com.panda.test.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestProperties {

	Map<String, String> map = new HashMap<String, String>();
	Properties pt = new Properties();
	
	private String path = "D:\\WorkTools\\Workspaces\\MyEclipse 10\\TestProject\\src\\conf\\test.properties";
	
//	TestProperties(String path) {
//		if ("".equals(path)) {
//			this.path = path;
//		}
//		
//		InputStream in;
//		try {
//			pt.clear();
//			in = new BufferedInputStream(new FileInputStream(path));
//			this.pt.load(in);
//			in.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		Enumeration en = this.pt.propertyNames(); // 得到配置文件的名字
//		while (en.hasMoreElements()) {
//			String strKey = (String) en.nextElement();
//			String strValue = this.pt.getProperty(strKey);
//			this.map.put(strKey, strValue);
//		}
//	}
	
	/**
	 * 获取所有属性
	 * @return
	 */
	public Map<String, String> getProMap(String path) {
		if ("".equals(path)) {
			this.path = path;
		}

		InputStream in;
		try {
			pt.clear();
			in = new BufferedInputStream(new FileInputStream(path));
			this.pt.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Enumeration en = this.pt.propertyNames(); // 得到配置文件的名字
		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = this.pt.getProperty(strKey);
			this.map.put(strKey, strValue);
		}
		return this.map;
	}
	
	/**
	 * 添加属性
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public void addPro(String key, String value) throws Exception{
		pt.setProperty(key, value);
		//文件输出流
		FileOutputStream fos = new FileOutputStream(path);
		//将Properties集合保存到流中
		pt.store(fos, "test_dax");
//		pt.s
		fos.close();//关闭流
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");

		final String configFilePath = "D:/WorkTools/Workspaces/MyEclipse 10/TestProject/src/conf/test.properties";
		
		TestProperties pro = new TestProperties();
		try {
			Map<String, String> map = pro.getProMap(configFilePath);
		    for (Map.Entry<String, String> entry : map.entrySet()) {  
		        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		    }
		    
//			System.out.println("开始添加属性...");
//			pro.addPro("恒隆必信", "技术部");
//			System.out.println("添加属性完毕.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
}
