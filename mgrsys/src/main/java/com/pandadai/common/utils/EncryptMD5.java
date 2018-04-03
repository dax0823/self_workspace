/**
 * 
 */
package com.pandadai.common.utils;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 仵作
 * 2014-9-10 上午10:06:08
 */
public class EncryptMD5 {
	/**
	 * 转换进制 byte -> 16
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs;
	}

	/**
	 * 输出加密结果
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getMd5String(String str) throws Exception {
		//判断字符串合法性
		if (StringUtils.isBlank(str)) {
			throw new Exception("MD5 加密字符串为空。");
		}
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(str.getBytes("UTF-8"));

		byte[] guid = md5.digest();
		return (byte2hex(guid));
	}

	public static void main(String[] args) {
		System.out.println("MD5 begin.");
		String pwd = "dingding";	//54e23552b007adf95506be46ee25defb
		try {
			String pwdMd5 = EncryptMD5.getMd5String(pwd);
			System.out.println(pwdMd5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("MD5 end.");
	}

}
