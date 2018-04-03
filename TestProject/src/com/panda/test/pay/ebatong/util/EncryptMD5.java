/**
 * 
 */
package com.panda.test.pay.ebatong.util;

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
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
//		String test = "ABC";
//		String test = "amount=1.10&bank_code=BOCSH_D_B2C&card_bind_mobile_phone_no=13888888888&card_no=6217853560025230060&cert_no=420621199009023449&cert_type=01&customer_id=9bef20ab570340788fb8ba6e92b74685&input_charset=UTF-8&out_trade_no=20151009151916-23064&partner=201508191714122264&real_name=张三&service=ebatong_mp_dyncode&sign_type=MD52XYEF5RDNQ0U7H25WWSHM3IF8YK0YVvgyftw";
		String test = "升序排列且加上key后：amount=0.01&customer_id=44&input_charset=utf-8&out_trade_no=201511240924352432_775&partner=201511240924352432&service=ebatong_mp_dyncode&sign_type=MD5L9P5OOQFI0GB87Z2G5EDW08SRTYB73nbznmg";
		try {
			String testMd5 = EncryptMD5.getMd5String(test);
			System.out.println("md5 : " + testMd5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
	}

}
