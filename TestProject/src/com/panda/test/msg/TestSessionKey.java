package com.panda.test.msg;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class TestSessionKey {
	
	/*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
	
    
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
//    public static String convertMD5(String inStr){  
//  
//        char[] a = inStr.toCharArray();  
//        for (int i = 0; i < a.length; i++){  
//            a[i] = (char) (a[i] ^ 't');  
//        }  
//        String s = new String(a);  
//        return s;  
//  
//    }  
    
    /**
     * 获取用户id
     * @param id
     * @return
     */
    public static String getUserId(String id) {
    	return id == null ? "0" : id;
    }
    
    /**
     * 获取当前时间的毫秒值
     * @return
     */
    public static long getCurrentTime() {
    	return new Date().getTime();
    }
    
    /**
     * 获取一个随机数
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
         Random random = new Random();
//         int s = random.nextInt(max)%(max-min+1) + min;
//         System.out.println(s);
         return random.nextInt(max)%(max-min+1) + min;
    }
    
    /**
     * 产生一个唯一的 sessionKey
     * 生成方法为：
     * 1. 原始字符串 = 用户id + “-” + 当前时间毫秒值 + “-” + 一个随机数
     * 2. 对原始字符串进行 md5 操作
     */
    public static void createSessionKey() {
		//随机数的取值范围
		final int MIN = 0;
		final int MAX = 100;

//		String s = new String("tangfuqiang");
//        System.out.println("原始：" + s);
//        System.out.println("MD5后：" + string2MD5(s));
//        System.out.println("加密的：" + convertMD5(s));
//        System.out.println("解密的：" + convertMD5(convertMD5(s)));
		
		StringBuilder sbu = new StringBuilder();
//		sbu.append(TestSessionKey.getUserId(null));
		sbu.append(TestSessionKey.getUserId("44"));
		sbu.append("-");
		sbu.append(TestSessionKey.getCurrentTime());
		sbu.append("-");
		sbu.append(TestSessionKey.getRandom(MIN, MAX));
		
		System.out.println("原始字符串：" + sbu.toString());
		System.out.println("md5后：" + TestSessionKey.string2MD5(sbu.toString()));
    }
    
    /**
     * test
     * @param args
     */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " begin.");
		
		TestSessionKey.createSessionKey();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " end.");
	}
}
