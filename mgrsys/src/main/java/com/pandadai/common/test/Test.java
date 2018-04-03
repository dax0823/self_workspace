package com.pandadai.common.test;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.pandadai.finance.vo.InvestHomeSumInterestVO;

/**
 * 测试类
 * 
 * @author 仵作
 * 
 */
public class Test {
	
//	private final String test = ...;
	
	/**
	 * 毫秒转时间
	 */
	private static void test() {
		// long sd = 1345185923140L;
		long sd = 1408456004l;

		Date dat = new Date(sd * 1000);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dat);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String sb = format.format(gc.getTime());
		System.out.println(sb);
	}

	/**
	 * 时间转毫秒
	 */
	private static void test2() {
		String str = "2009-1-22";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		long millionSeconds = 0;
		try {
			millionSeconds = sdf.parse(str).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 毫秒

		System.out.println(millionSeconds);
	}

	private static void test3() throws Exception {
		final int MAX_LENGTH = 155;
		String fileName = "fin_20140829001657_2014-06-25至2014-08-28_days.csv";
//		int length = HttpUtility.UrlEncode(fileName).Length;
		String code = URLEncoder.encode(fileName, "UTF-8");
		int len = code.length();
		while (len > MAX_LENGTH) {
			int index = fileName.lastIndexOf(".");
			if (index > 0) {
				fileName = fileName.substring(0, index - 1)
						+ fileName.substring(index);
			} else {
				fileName = fileName.substring(0, fileName.length() - 1);
			}
//			len = HttpUtility.UrlEncode(fileName).Length;
			len = URLEncoder.encode(fileName, "UTF-8").length();
		}
	}
	
	private static void test4() throws Exception {
		final int NUM = 5;
		
		InvestHomeSumInterestVO vo = new InvestHomeSumInterestVO();
		for (int i=0; i<NUM; i++) {
			float tmp = (float) i;
			Method mt = vo.getClass().getMethod("setMonth" + (i + 1), float.class);
			mt.invoke(vo, tmp);
		}
	}

	private static void test5(String ...str) {
		if (str != null && str.getClass().isArray()) {
			if (str.length == 0) {
				System.out.println("str is empty.");
			}
			for (int i=0; i<str.length; i++) {
				System.out.println("str[" + i + "] = " + str[i]);
			}
		} else {
			System.out.println("str.else = " + str);
		}
	}
	
	private static void test6() {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		// String dateStr = formatter.format(new Date());
		// System.out.println("dateStr = " + dateStr);
		Calendar c = Calendar.getInstance();
		int maxDate = c.getActualMaximum(Calendar.DATE);
		int minDate = c.getActualMinimum(Calendar.DATE);
		c.set(Calendar.DATE, maxDate);
		System.out.println("maxDate: " + c.getTime());
		
		String myString = "2015-08";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date d = null;
		try {
			d = sdf.parse(myString);
			System.out.println("d: " + c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

//		boolean flag = d.before(c.getTime());
		boolean flag = d.after(c.getTime());
		
		System.out.println("flag = " + flag);
		
		// System.out.println(c.getTime());
		// c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("下个月的第一天: " + c.getTime());
		// c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
		// c.set(Calendar.DAY_OF_MONTH, 0);
		// System.out.println("下个月的最后一天11: " + c.getTime());
		// // c.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("下个月的最后一天22: " + c.getTime());
	}
	
	private static void test7() throws ParseException {
		String month = "";
		Calendar cc = Calendar.getInstance();
//		month = cc.get(Calendar.YEAR) + "-" + cc.get(Calendar.MONTH);
		month = "2015-07";
		
		System.out.println("month = " + month);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dat = sdf.parse(month);

		System.out.println("date = " + dat);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String dateStr = formatter.format(dat);
		System.out.println("dateStr = " + dateStr);
	}
	
	private static void test8() {
		int interestRate = 18;
		double rewardRate = 2.75;
		int duration = 5;
		final double NUM = 3 * 0.01;
		int money = 50000;
		
		double test = (NUM - (interestRate * 0.01 / 12 + rewardRate * 0.01 / duration)) * money;
		System.out.println("test = " + test);
	}
	
	public static void main(String[] tt) {
		System.out.println("@@@ begin.");
		
//		StringBuffer buf = new StringBuffer();
//		buf.append("");
//		test();
//		test2();
		try {
//			test3();
//			test4();
//			test5();
//			test5(null);
//			test5("123");
//			test5("hello", "world", "13sd", "china", "cum", "ict");
//			test6();
//			test7();
			test8();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("@@@ end.");
	}
}
