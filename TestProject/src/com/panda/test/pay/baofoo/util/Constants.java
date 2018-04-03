package com.panda.test.pay.baofoo.util;

/***
 * 常量
 * 
 * @author 仵作
 * 
 */
public class Constants {
	/*
	 * java 常量
	 */
	// 编码格式 utf-8
	public final static String ENCODE_TYPE_UTF8 = "utf-8";
	//服务器地址
	//测试环境
	public final static String URL_DOMAIN = "https://tgw.baofoo.com/apipay/wap";
	//正式环境
//	public final static String URL_DOMAIN = "https://gw.baofoo.com/apipay/wap";
	
	/*
	 * 不加密常量
	 */
	//版本号
	public final static String VERSION = "4.0.0.0";
	// 字符集
	// 1：UTF-8;
	// 2：GBK;
	// 3：GB2312；
	public final static String INPUT_CHARSET = "1";
	// 网关页面显示语言种类
	// 固定值：1（代表中文）
	public final static String LANGUAGE = "1";
	// 终端号
	public final static String TERMINAL_ID = "100000916";
	// 交易类型
	public final static String TXN_TYPE = "03311";
	// 交易子类
	// 01：支付类交易
	public final static String TXN_SUB_TYPE = "01";
	// 商户号
	// 测试环境
	public final static String MEMBER_ID = "100000178";
	// 真实环境
	// public final static String MEMBER_ID = "125865 ";
	// 加密数据类型
	// xml/json
	// public final static String DATA_TYPE = "xml";
	public final static String DATA_TYPE = "json";
	// 商户返回页面地址（支付出现异常时返回的商户页面）
	public final static String BACK_URL = "";
	

	/*
	 * 需加密常量
	 */
	//接入类型
	//0000：储蓄卡
	public final static String BIZ_TYPE = "0000";
	
	// 身份证类型
	// 01：身份证号
	public final static String ID_CARD_TYPE = "01";
	// 商品名称
	public final static String COMMODITY_NAME = "【熊猫贷】线上充值";
	// 商品数量
	public final static String COMMODITY_AMOUNT = "1";
	// 页面通知地址（支付完成返回的页面）
	public final static String PAGE_URL = "";
	// 服务器通知地址（支付完成调用的接口）
	public final static String RETURN_URL = "";
	
	/*
	 * 调试数据
	 */
	// 调试使用，用户真实姓名
	// public final static String USER_REAL_NAME = "丁辉";
	// // 调试使用，用户身份证号
	// public final static String USER_IDCARD_NO = "110106198408230615";
	// // 调试使用，用户在平台中绑定的银行卡
	// public final static String USER_BANK_CARD_NO = "6230580000030050020";
	// // 调试使用，用户在平台中注册手机号
	// public final static String USER_PHONE = "13501320540";
	public final static String USER_REAL_NAME = "金大牙";
//	public final static String USER_REAL_NAME = "testName";
	public final static String USER_IDCARD_NO = "110106198012230011";
	public final static String USER_BANK_CARD_NO = "6230580000030050001";
	public final static String USER_PHONE = "13512345678";
	// 银行编码
	// ICBC 中国工商银行
	// ABC 中国农业银行
	// CCB 中国建设银行
	// BOC 中国银行
	// BOCOM 中国交通银行
	// CIB 兴业银行
	// CITIC 中信银行
	// CEB 中国光大银行
	// PAB 平安银行
	// PSBC 中国邮政储蓄银行
	// SHB 上海银行
	// SPDB 浦东发展银行
	// CMBC 中国民生
	// CMB 招商银行
	public final static String USER_BANK_CODE = "PAB";
	public final static String USER_MONEY = "1";
}
