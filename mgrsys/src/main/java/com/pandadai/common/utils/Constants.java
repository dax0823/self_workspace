package com.pandadai.common.utils;

public class Constants {
	/**
	 * 执行成功返回信息
	 */
	public final static int SUCC_CODE = 0; 
	public final static String SUCC_MSG = "成功。"; 
	
	/**
	 * 系统异常返回信息	
	 */
	public final static int SYS_ERROR_CODE = -1; 
	public final static String SYS_ERROR_MSG = "系统错误。"; 
	
	/**
	 * 登录异常返回信息
	 */
	public final static int ERROR_LOGIN_USERNAME_NULL_CODE = 101; 
	public final static String ERROR_LOGIN_USERNAME_NULL_MSG = "登录账号为空。"; 
	public final static int ERROR_LOGIN_PWD_NULL_CODE = 102; 
	public final static String ERROR_LOGIN_PWD_NULL_MSG = "密码为空。" ;
	public final static int ERROR_LOGIN_USERWORD_NULL_CODE = 102; 
	public final static String ERROR_LOGIN_USERWORD_NULL_MSG = "登录口令为空。" ;
	public final static int ERROR_LOGIN_USERNAME_NOT_EXISTS_CODE = 103; 
	public final static String ERROR_LOGIN_USERNAME_NOT_EXISTS_MSG = "登录账号不存在。"; 
	public final static int ERROR_LOGIN_PWD_ERROR_CODE = 104; 
	public final static String ERROR_LOGIN_PWD_ERROR_MSG = "密码/登录口令错误。"; 
//	public final static int ERROR_LOGIN_USERWORD_ERROR_CODE = 105; 
//	public final static String ERROR_LOGIN_USERWORD_ERROR_MSG = "登录口令错误。"; 
	public final static int ERROR_LOGIN_CAPTCHA_NULL_CODE = 105; 
	public final static String ERROR_LOGIN_CAPTCHA_NULL_MSG = "验证码为空。"; 
	public final static int ERROR_LOGIN_CAPTCHA_ERROR_CODE = 106; 
	public final static String ERROR_LOGIN_CAPTCHA_ERROR_MSG = "验证码错误。"; 
	
	/**
	 * sql 常量
	 */
	//需替换的默认值字符串
	public final static String SQL_DEFAULT_CONDITION_1_EQ_1 = "and 1=1"; 
	public final static String SQL_DEFAULT_CONDITION_MULTIPLY_1 = "* 1";
	public final static String SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM = " limit "; 
	public final static String SQL_DEFAULT_CONDITION_LIMIT_NUM_10 = " limit 10"; 
	
	/**
	 * 线下充值奖励比率常量
	 */
	public final static int OFF_REWARD_5000 = 5000;
	public final static int OFF_REWARD_20000 = 20000;
	public final static int OFF_REWARD_50000 = 50000;
	//money < 20000 && money >= 5000
	public final static float OFF_REWARD_RATE_015 = 0.0015f;
	public final static String OFF_REWARD_RATE_015_STR = "0.15";
	//money < 50000 && money >= 20000
	public final static float OFF_REWARD_RATE_02 = 0.002f;
	public final static String OFF_REWARD_RATE_02_STR = "0.2";
	//money >= 50000
	public final static float OFF_REWARD_RATE_025 = 0.0025f;
	public final static String OFF_REWARD_RATE_025_STR = "0.25";
	
	/**
	 * 图形验证码类别
	 */
	public enum CaptchaType {
		LOGIN("captchaLogin");
		private final String sessionCode;

		private CaptchaType(String sessionCode) {
			this.sessionCode = sessionCode;
		}

		public String getCode() {
			return sessionCode;
		}
	}
	public static final String CAPTCHA_LOGIN_STR = "captchaLogin";

}
