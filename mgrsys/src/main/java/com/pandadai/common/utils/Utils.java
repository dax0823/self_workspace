package com.pandadai.common.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class Utils {

	private void test() {
		HashMap<String, Object> map = new HashMap<String, Object>();
	}
	
	/**
	 * 使用反射方式，将一个对象中的所有属性值解析出，并拼接为一个字符串，以“,”分割
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getObjectAttrValues(Object obj) throws Exception {
		Field[] fields = obj.getClass().getDeclaredFields();
		String values = "";
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
				
//				values += "," + field.get(obj);
				
				// 处理 excel 科学计数法显示问题
				String fd = field.get(obj).toString();
				if (StringUtils.isNumeric(fd) && fd.length() >= 12) {
					fd = "'" + fd;
				}
				values += "," + fd;
			}
		}
		if (values.startsWith(","))
			values = values.replaceFirst(",", "");
		return values;
	}

	/**
	 * 为文件导出拼接时间部分字符串，格式：YYYYMMDDHHMMSS
	 * 
	 * @return
	 */
	public static String getNowTime4FileName() {
		Calendar cal = Calendar.getInstance();	// 使用日历类
		String year = String.valueOf(cal.get(Calendar.YEAR));	// 得到年
		String month = (cal.get(Calendar.MONTH) + 1) < 10 ? ("0" + (cal.get(Calendar.MONTH) + 1)) : String.valueOf(cal.get(Calendar.MONTH) + 1);	// 得到月，因为从0开始的，所以要加1
		String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));	// 得到天
		String hour = cal.get(Calendar.HOUR) < 10 ? ("0" + cal.get(Calendar.HOUR)) : String.valueOf(cal.get(Calendar.HOUR));	// 得到小时
		String minute = cal.get(Calendar.MINUTE) < 10 ? ("0" + cal.get(Calendar.MINUTE)) : String.valueOf(cal.get(Calendar.MINUTE));	// 得到分钟
		String second = cal.get(Calendar.SECOND) < 10 ? ("0" + cal.get(Calendar.SECOND)) : String.valueOf(cal.get(Calendar.SECOND));	// 得到秒
		return year + month + day + hour + minute + second;
//		return year + month + day;
	}

	public static void main(String[] args) {
		System.out.println("@@ begin.");

//		String nowTime = Utils.getNowTime4FileName();
//		System.out.println("nowTime = " + nowTime);
		
		String path = "F:\\开发环境\\源码\\WWW\\panada\\UF/Uploads/Idcard/20140627093118352_572.jpg";
		path = path.replaceAll("/", "\\\\");
		System.out.println("path = " + path);
		System.out.println("" + File.separatorChar);
		System.out.println("@@ begin.");
	}
}
