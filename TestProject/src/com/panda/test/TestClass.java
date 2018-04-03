package com.panda.test;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class TestClass {

	/**
	 *  获取一组非负数字的最大组合
	 * @param numbers
	 */
	private static void getMaxCombination(int[] numbers) {
		if (numbers.length > 0) {
			int n = numbers[0];
			for (int i=0; i<numbers.length-1; i++) {
//				if (String.valueOf(n).compareTo(String.valueOf(numbers[i+1])) == 1) {
//					System.out.println(n + " > " +numbers[i+1] );
//				} else if (String.valueOf(n).compareTo(String.valueOf(numbers[i+1])) == 0) {
//					System.out.println(n + " = " +numbers[i+1] );
//				} else {
//					System.out.println(n + " < " +numbers[i+1] );
//				}
				char[] ch = String.valueOf(n).toCharArray();
				char[] ch2 = String.valueOf(numbers[i+1]).toCharArray();
				for (int j=0; j<ch.length-1; j++) {
					if (ch[j] < ch2[j]) {	//当数组中的某个数某一位比参照数对应位大时
						n = numbers[i];
						break;
					} else if (ch[j] == ch2[j]) {	//当数组中的某个数的某一位与参照数对应位相等时
						
					}
				}
			}
		} else {
			System.out.println("getMaxCombination:: numbers is null.");
//			return 
		}
	}
	

	/**
	 *  获取一组非负数字的最大组合
	 * @param numbers
	 */
	private static void getMaxCombination2(int[] numbers) {
		String[] arr = null;
		String combination = null;
		int[] nums = numbers.clone(); 
		System.out.println("numbers.length = " + numbers.length + ".");
		for (int i=0; i<numbers.length; i++) {
//			System.out.println("getMaxCombination2:: numbers[" + i + "] = " + numbers[i]);
			for (int a=i; a<numbers.length; a++) {
				System.out.println("getMaxCombination2:: i = " + i + ", a = " + a + ", numbers[" + a + "] = " + numbers[a]);
			}
		}
	}
	
	private static void sort(String prefix, int[] a) {
		if (a.length == 1) {
			System.out.println(prefix + a[0]);
		}
		for (int i = 0; i < a.length; i++) {
			sort(prefix + a[i], copy(a, i));
		}
	}
	
	private static int[] copy(int[] a,int index){
		int[] b = new int[a.length-1];
		System.out.println("111 ==== " + toString(b));
		System.arraycopy(a, 0, b, 0, index);
		System.out.println("222 ==== " + toString(b));
		System.arraycopy(a, index+1, b, index, a.length-index-1);
		System.out.println("333 ==== " + toString(b));
		return b;
	}
	
	private static String toString(int[] num) {
		String buf = "";
		for(int i=0; i<num.length; i++) {
			buf += num[i] + ", ";
		}
		return buf.toString();
	}
	
	public static void dateDeal() {
		String dat8 = "2015-08-31";
		String dat9 = "2015-9-31";
		String dat10 = "2015-10-31";
		String dat11 = "2015-11-31";
		String dat12 = "2015-12-31";
		String dat13 = "2015-13-31";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
//		Date date = new Date();
//		Date date2 = new Date();
//		long tmp = 0l;
		try {
//			date = format.parse(dat);
//			date2 = format.parse(dat2);
//			tmp = date2.getTime() - date.getTime();
			System.out.println("2015-08-31：" + format.parse(dat8));
			System.out.println("2015-09-31：" + format.parse(dat9));
			System.out.println("2015-10-31：" + format.parse(dat10));
			System.out.println("2015-11-31：" + format.parse(dat11));
			System.out.println("2015-12-31：" + format.parse(dat12));
			System.out.println("2015-13-31：" + format.parse(dat13));
			System.out.println("9 - 8 = " + (format.parse(dat9).getTime() - format.parse(dat8).getTime()) / 1000 + "s.");
			System.out.println("10 - 8 = " + (format.parse(dat10).getTime() - format.parse(dat8).getTime()) / 1000 + "s.");
			System.out.println("11 - 8 = " + (format.parse(dat11).getTime() - format.parse(dat8).getTime()) / 1000 + "s.");
			System.out.println("12 - 8 = " + (format.parse(dat12).getTime() - format.parse(dat8).getTime()) / 1000 + "s.");
			System.out.println("13 - 8 = " + (format.parse(dat13).getTime() - format.parse(dat8).getTime()) / 1000 + "s.");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void dateDeal2() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); //格式化当前系统日期
		System.out.println("dateDeal2.getCurrDate() = " + format.format(new Date()));
	}
	
	/**
	 * 根据用户 id 拼接用户头像图片相对路径
	 * @param userId
	 */
	public static String getUserId(int userId) {
//		http://www.pandadai.com/Style/header/customavatars/000/00/00/44_avatar_middle.jpg
//		 "Style\header\customavatars\000\00\00\";
		final String path = "http://www.pandadai.com/Style/header/customavatars";
		
		Formatter format = new Formatter();
		String formatUserId = format.format("%09d", userId).toString();
//		System.out.println("getUserId.format = " + format);
//		System.out.println("getUserId.formatUserId = " + formatUserId);
		
		String dir1 = formatUserId.substring(0, 3);
		String dir2 = formatUserId.substring(3, 5);
		String dir3 = formatUserId.substring(5, 7);
		
		//取用户 id 最后两位
		String tmpUserId = String.valueOf(userId);
		if (tmpUserId.length() < 2) {	//长度 1 的 id 前面补“0”
			tmpUserId = "0" + tmpUserId;
		}
		String userIdLast2 = tmpUserId.substring(tmpUserId.length() - 2);
//		System.out.println("userId 最后两位 = " + userIdLast2);
		
//		String picUrl = path + "/" + dir1 + "/" + dir2 + "/" + dir3 + "/" + userId + "_avatar_small.jpg";
		return path + "/" + dir1 + "/" + dir2 + "/" + dir3 + "/" + userIdLast2 + "_avatar_small.jpg";
	}
	
	/**
	 * 
	 * x = logaN（a^x = N）
	 * x：对数
	 * a：底数
	 * N：真数
	 * 
	 * @param a
	 * @param N
	 * @return
	 */
	public static double getLogNumber(double a, double N) {
		return Math.log(N) / Math.log(a);
	}
	
	/**
	 * 鸡蛋问题
	 * @return
	 */
	public static int getNumber() {
		int eggs = 0;
		while (!(
				eggs % 8 == 2
//				&& eggs % 9 == 0 
				&& eggs % 7 == 5
				&& eggs % 6 == 0
				&& eggs % 5 == 4
//				&& eggs % 4 == 2
//				&& eggs % 3 == 0
//				&& eggs % 2 == 0
//				&& eggs % 1 == 0
				)) {
			eggs += 9;
//			System.out.println("log.eggs = " + eggs);
		}
		return eggs;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("TestClass.begin.");
		
		// 编写一个能将给定非负整数列表中的数字排列成最大数字的函数。例如，给定[50，2，1,9]，最大数字为95021。
//		int[] nums = { 50, 2, 1, 9};
//		int[] nums = { 52, 2, 1, 9, 50};
//		getMaxCombination(nums);
//		getMaxCombination2(nums);
//		sort("", nums);
		
//		StringBuffer buf = new StringBuffer();
//		buf.append("");
		
//		dateDeal();
//		dateDeal2();
		
//		int userId = 1;
////		int userId = 3068;
//		String picUrl = getUserId(userId);
//		System.out.println("picUrl：" + picUrl);
		
//		URLEncode encode = new URLEncode();
//		URL url = new URL();
		
//		double a = 1.055;
//		double N = 2;
//		System.out.println("x = logaN.");
//		System.out.println("a：" + a + "； N = " + N);
//		System.out.println("x：" + getLogNumber(a, N));
		
		System.out.println("getNumber = " + getNumber());
		
		System.out.println("TestClass.end.");
	}
}
