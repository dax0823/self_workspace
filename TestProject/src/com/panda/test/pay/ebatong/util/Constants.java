package com.panda.test.pay.ebatong.util;

/***
 * 常量
 * 
 * @author 仵作
 * 
 */
public class Constants {
	// 签名方式 RSA或MD5
	public final static String SIGN_TYPE_MD5 = "MD5";
	
	// 商户编号
	// 网关支付
	// public final static String PARTNER_NO = "201511240924462433";
	// 认证支付
	public final static String PARTNER_NO = "201511240924352432";
	// MD5 的 key
	
	// 网关支付
	// public final static String SIGN_TYPE_MD5_KEY =
	// "7I4MCACYEKDU9HSBASJBSR8M6Y1O1Omhhukf";
	// 认证支付
	public final static String SIGN_TYPE_MD5_KEY = "L9P5OOQFI0GB87Z2G5EDW08SRTYB73nbznmg";
	
	// 编码格式 utf-8
	public final static String ENCODE_TYPE_UTF8 = "utf-8";

	// 各银行编码
	// 工商银行 ICBC_D_B2C
	// 民生银行 CMBCD_D_B2C
	// 中国银行 BOCSH_D_B2C
	// 光大银行 CEB_D_B2C
	// 农业银行 ABC_D_B2C
	// 兴业银行 CIB_D_B2C
	// 平安银行 PINGAN_D_B2C
	// 建设银行 CCB_D_B2C
	public final static String BANK_CODE = "PINGAN_D_B2C";

	// 用户信息
	// 用户请求 ip
	public final static String USER_REQUEST_ID = "124.42.62.221";
	// 01：身份证；02：军官证；03：护照 ；04：户口簿 ；05：回乡证 ；06：其他
	public final static String USER_IDCARD_TYPE = "01";
	// 调试使用，用户在平台中的唯一标识id
	public final static String USER_ID = "44";
	// 调试使用，用户真实姓名
	public final static String USER_REAL_NAME = "丁辉";
	// 调试使用，用户身份证号
	public final static String USER_IDCARD_NO = "110106198408230615";
	// 调试使用，用户在平台中绑定的银行卡
	public final static String USER_BANK_CARD_NO = "6230580000030050020";
	// 调试使用，用户在平台中注册手机号
	public final static String USER_PHONE = "13501320540";
	// 调试使用，支付金额（单位：元）
	// 第一次需支付（最低金额）1.01元
//	public final static String USER_MONEY = "1.10";
	public final static String USER_MONEY = "0.01";

	// 服务接口
	// 查询用户绑卡
	public final static String URL_DOMAIN_QUERY_BIND_CARD_INFO = "https://www.ebatong.com/mobileFast/queryCardInfo.htm";
	// 验证码下发
	public final static String URL_DOMAIN_EBATONG_MP_DYNCODE = "https://www.ebatong.com/mobileFast/getDynNum.htm";
	// 获取时间戳
	public final static String URL_DOMAIN_QUERY_TIMESTAMP = "https://www.ebatong.com/gateway.htm";
	// 支付
	public final static String URL_DOMAIN_CREATE_DIRECT_PAY_BY_MP = "https://www.ebatong.com/mobileFast/pay.htm";
	// 查询支付结果
	public final static String URL_DOMAIN_SINGLE_DIRECT_QUERY = "https://www.ebatong.com/gateway.htm";
	// 银行卡解绑
	public final static String URL_DOMAIN_EBATONG_MP_UNBIND = "https://www.ebatong.com/mobileFast/unbind.htm";

	// 服务名称
	// 查询用户绑卡
	public final static String SERVICE_TYPE_QUERY_BIND_CARD_INFO = "query_bind_card_info";
	// 验证码下发
	public final static String SERVICE_TYPE_EBATONG_MP_DYNCODE = "ebatong_mp_dyncode";
	// 获取时间戳
	public final static String SERVICE_TYPE_QUERY_TIMESTAMP = "query_timestamp";
	// 支付
	public final static String SERVICE_TYPE_CREATE_DIRECT_PAY_BY_MP = "create_direct_pay_by_mp";
	// 查询支付结果
	public final static String SERVICE_TYPE_SINGLE_DIRECT_QUERY = "single_direct_query";
	// 银行卡解绑
	public final static String SERVICE_TYPE_EBATONG_MP_UNBIND = "ebatong_mp_unbind";

	// 支付成功后，贝付会向平台推送交易成功消息
	// 异步（向平台后端推送）
	public final static String PAY_CALLBACK_NOTIFY_URL = "www.pandadai.com";
	// 同步（向平台前端推送）
	public final static String PAY_CALLBACK_SHOW_URL = "";
	// 商品名称
	public final static String PAY_GOODS_NAME = "（贝付）充值";
	// 商品描述
	public final static String PAY_GOODS_DESCRIPTION = "平台线上（贝付）充值";
	// 支付方式
	public final static String PAY_PAY_METHOD = "";
	// 公共回传参数
	public final static String PAY_EXTRA_COMMON_PARAM = "";
	// 公用业务扩展参数
	public final static String PAY_EXTEND_PARAM = "";

	// 向平台推送解绑信息
	public final static String UNBIND_CALLBACK_NOTIFY_URL = "";
	// 向平台推送解绑信息
	public final static String UNBIND_DESCRIPTION = "银行卡解绑";

}
