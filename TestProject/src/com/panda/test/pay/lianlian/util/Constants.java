package com.panda.test.pay.lianlian.util;

/**
 * 常量信息
 * @author 仵作
 *
 */
public class Constants {
		// MD5 KEY
//		final String MD5_KEY = "201408071000001543test_20140812";
		// 接收异步通知地址
//		final String NOTIFY_URL = "http://ip:port/wepdemo/notify.htm";
		// 支付结束后返回地址
//		final String URL_RETURN = "http://ip:port/wepdemo/urlReturn.jsp";
		// 接口版本号，固定1.0
//		public final static String VERSION = "1.0";
	
	//加密信息
	// 银通公钥
	public final static String YT_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
	// 商户私钥
	// final String TRADER_PRI_KEY ="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMlGNh/WsyZSYnQcHd9t5qUkhcOhuQmozrAY9DM4+7fhpbJenmYee4chREW4RB3m95+vsz9DqCq61/dIOoLK940/XmhKkuVjfPqHJpoyHJsHcMYy2bXCd2fI++rERdXtYm0Yj2lFbq1aEAckciutyVZcAIHQoZsFwF8l6oS6DmZRAgMBAAECgYAApq1+JN+nfBS9c2nVUzGvzxJvs5I5qcYhY7NGhySpT52NmijBA9A6e60Q3Ku7vQeICLV3uuxMVxZjwmQOEEIEvXqauyYUYTPgqGGcwYXQFVI7raHa0fNMfVWLMHgtTScoKVXRoU3re6HaXB2z5nUR//NE2OLdGCv0ApaJWEJMwQJBAPWoD/Cm/2LpZdfh7oXkCH+JQ9LoSWGpBDEKkTTzIqU9USNHOKjth9vWagsR55aAn2ImG+EPS+wa9xFTVDk/+WUCQQDRv8B/lYZD43KPi8AJuQxUzibDhpzqUrAcu5Xr3KMvcM4Us7QVzXqP7sFc7FJjZSTWgn3mQqJg1X0pqpdkQSB9AkBFs2jKbGe8BeM6rMVDwh7TKPxQhE4F4rHoxEnND0t+PPafnt6pt7O7oYu3Fl5yao5Oh+eTJQbyt/fwN4eHMuqtAkBx/ob+UCNyjhDbFxa9sgaTqJ7EsUpix6HTW9f1IirGQ8ac1bXQC6bKxvXsLLvyLSxCMRV/qUNa4Wxu0roI0KR5AkAZqsY48Uf/XsacJqRgIvwODstC03fgbml890R0LIdhnwAvE4sGnC9LKySRKmEMo8PuDhI0dTzaV0AbvXnsfDfp";
	public final static String TRADER_PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
	// 加密方式：rsa
	public final static String SIGN_TYPE_RSA = "RSA";
	// 签名方式 RSA或MD5
	public final static String SIGN_TYPE_MD5 = "MD5";
	
	//用户信息
	// 调试使用，用户在平台中的唯一标识id
	public final static String USER_ID = "44";
	// 调试使用，用户真实姓名
//	public final static String USER_REAL_NAME = "丁辉";
//	// 调试使用，用户身份证号
//	public final static String USER_IDCARD_NO = "110106198408230615";
//	// 调试使用，用户在平台中绑定的银行卡
//	public final static String USER_BANK_CARD_NO = "6230580000030050020";
//	// 调试使用，用户在平台中注册手机号
//	public final static String USER_PHONE = "13501320540";
	public final static String USER_REAL_NAME = "金大牙";
	public final static String USER_IDCARD_NO = "110106198012230011";
	public final static String USER_BANK_CARD_NO = "6230580000030050001";
	public final static String USER_PHONE = "13512345678";
	
	// 调试使用，用户在平台中注册时间
	public final static String USER_REGISTER_TIME = "20140625122006";
	// 调试使用，支付金额（单位：元）
	public final static String USER_MONEY = "0.01";
	// 调试使用，用户认证状态：
	//0：未认证；1：已认证
	public final static String USER_IDENTIFY_STATE= "1";
	// 调试使用，用户认证方式：
	//1：银行卡认证；2：现场认证；3：身份远程认证；4：其他认证；
	public final static String USER_IDENTIFY_TYPE = "3";
	
	// 商户编号
	public final static String OID_PARTNER = "201408071000001543";
	// 查询用户绑卡信息-偏移量
	public final static String QUERY_CARD_LIST_PAY_TYPE = "D";
	// 查询用户绑卡信息-支付方式
	public final static String QUERY_CARD_LIST_OFFSET = "0";
	// 支付-证件类型：身份证
	public final static String PAY_ID_TYPE = "0";
	// 支付-版本号：1.1
	public final static String PAY_VERSION = "1.1";
	// 请求应用标识：
	// 1：android；2：ios；3：wap；
	public final static String PAY_APP_REQUEST = "3";
	// 连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售）
	public final static String PAY_BUSI_PARTNER = "101001";
	// 连连支付异步调用的链接
	public final static String PAY_NOTIFY_URL = "www.pandadai.com";
	//商品类目：p2p小额贷款
	public final static String PAY_GOODS_TYPE_2009 = "2009";
	
}
