package com.panda.test.pay.ebatong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.panda.test.pay.ebatong.util.Constants;
import com.panda.test.pay.ebatong.util.EncryptMD5;
import com.panda.test.pay.ebatong.util.RequestMethod;
import com.panda.test.pay.ebatong.util.Utils;
import com.panda.test.pay.ebatong.vo.ParamGetDynCodeVO;
import com.panda.test.pay.ebatong.vo.ParamGetTimestampVO;
import com.panda.test.pay.ebatong.vo.ParamPayVO;
import com.panda.test.pay.ebatong.vo.ParamQueryPayResultVO;
import com.panda.test.pay.ebatong.vo.ParamQueryUserCardListVO;
import com.panda.test.pay.ebatong.vo.ParamUnbindVO;

/***
 * 贝付支付相关请求调用
 * 
 * @author 仵作
 *
 */
public class TestPay {
	/**
	 * 查询用户当前绑卡情况
	 * @throws Exception
	 */
	public static String queryUserCardList() throws Exception {
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		bd.append("&customer_id=").append(Constants.USER_ID);
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamQueryUserCardListVO vo = new ParamQueryUserCardListVO();
		vo.setService(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setCustomer_id(Constants.USER_ID);
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject paramsJson = JSONObject.fromObject(vo);
		System.out.println("paramsJson : " + paramsJson);
		
		//发送请求
		RequestMethod req = new RequestMethod();
		String result = req.methodPost(Constants.URL_DOMAIN_QUERY_BIND_CARD_INFO + "?" + paramsJson.toString());

//		System.out.println("result : " + result);
//		JSONObject resJson = JSONObject.fromObject(result);
//		String rr = resJson.getString("result");
//		System.out.println("resJosn.result = " + rr);
		
//		resJson = (JSONObject) resJson.remove("sign");
		
//		Set<String> set = new TreeSet<String>(new Comparator<String>() {
//			public int compare(String obj1, String obj2) {
//				System.out.println("obj1 = " + obj1);
//				System.out.println("obj2 = " + obj2);
//				return obj1.compareTo(obj2);
//			}
//		});
//		Map<String, String> map = new TreeMap<String, String>(
//		new Comparator<String>() {
//			public int compare(String obj1, String obj2) {
//				return obj1.compareTo(obj2);
//			}
//		});
//		
//		Set set = resJson.entrySet();
//		for (Object str : set) {
//			if (!"".equals(str.toString().trim())) {
//				String[] kv = str.toString().split("=");
//				if (!"sign".equals(kv[0])) {
//					if (kv.length < 2) map.put(kv[0], "");
//					else map.put(kv[0], kv[1]);
//				}
//			}
//		}
//		
//		JSONObject jsonObject = JSONObject.fromObject(map);
		
//		System.out.println("map : " + map.toString());
//		System.out.println("jsonObject : " + jsonObject.toString() + " + " + Constants.SIGN_TYPE_MD5_KEY);
//		System.out.println("md5(jsonObject) : " + EncryptMD5.getMd5String(jsonObject.toString() + Constants.SIGN_TYPE_MD5_KEY));
		
		return result;
	}
	
	/**
	 * 验证码下发-首次支付
	 * @param cardNo
	 * @param money
	 * @param bankCode
	 * @return
	 * @throws Exception
	 */
	public static String getDynCode(String cardNo, String money, String bankCode) throws Exception {
		if (StringUtils.isBlank(cardNo)) {
			throw new Exception("card_no 不可为空。");
		}
		if (StringUtils.isBlank(money)) {
			throw new Exception("money 不可为空。");
		}
		if (StringUtils.isBlank(bankCode)) {
			throw new Exception("bank_code 不可为空。");
		}
		
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_EBATONG_MP_DYNCODE);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		bd.append("&customer_id=").append(Constants.USER_ID);
		bd.append("&card_no=").append(cardNo);
		bd.append("&real_name=").append(Constants.USER_REAL_NAME);
		bd.append("&cert_no=").append(Constants.USER_IDCARD_NO);
		bd.append("&cert_type=").append(Constants.USER_IDCARD_TYPE);
		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
		int tmpId = Utils.getRandom(1, 1000);
		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
		bd.append("&amount=").append(money);
		bd.append("&bank_code=").append(bankCode);
		bd.append("&card_bind_mobile_phone_no=").append(Constants.USER_PHONE);
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamGetDynCodeVO vo = new ParamGetDynCodeVO();
		vo.setService(Constants.SERVICE_TYPE_EBATONG_MP_DYNCODE);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setCustomer_id(Constants.USER_ID);
		vo.setCard_no(cardNo);
		vo.setReal_name(Constants.USER_REAL_NAME);
		vo.setCert_no(Constants.USER_IDCARD_NO);
		vo.setCert_type(Constants.USER_IDCARD_TYPE);
		vo.setOut_trade_no(Constants.PARTNER_NO + "_" + tmpId);
		vo.setAmount(money);
		vo.setBank_code(bankCode);
		vo.setCard_bind_mobile_phone_no(Constants.USER_PHONE);
		
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject paramsJson = JSONObject.fromObject(vo);
		System.out.println("paramsJson : " + paramsJson);
		
		//发送请求
		RequestMethod req = new RequestMethod();
		String result = req.methodPost(Constants.URL_DOMAIN_EBATONG_MP_DYNCODE + "?" + paramsJson.toString());

//		System.out.println("result : " + result);
//		JSONObject resJson = JSONObject.fromObject(result);
//		String rr = resJson.getString("result");
//		System.out.println("resJosn.result = " + rr);
		
		return result;
	}
	
	/**
	 * 验证码下发-再次支付
	 * @param money
	 * @return
	 * @throws Exception
	 */
	public static String getDynCode(String money) throws Exception {
		if (StringUtils.isBlank(money)) {
			throw new Exception("amount 不可为空。");
		}
		
//		StringBuilder bd = new StringBuilder();
//		bd.append("service=").append(Constants.SERVICE_TYPE_EBATONG_MP_DYNCODE);
//		bd.append("&partner=").append(Constants.PARTNER_NO);
//		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
//		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
//		bd.append("&customer_id=").append(Constants.USER_ID);
//		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
//		int tmpId = Utils.getRandom(1, 1000);
//		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
//		bd.append("&amount=").append(money);
		
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_EBATONG_MP_DYNCODE);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		bd.append("&customer_id=").append(Constants.USER_ID);
		//再次支付时，为空的字段也需参与加密
		bd.append("&card_no=").append("");
		bd.append("&real_name=").append("");
		bd.append("&cert_no=").append("");
		bd.append("&cert_type=").append("");
		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
		int tmpId = Utils.getRandom(1, 1000);
		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
		bd.append("&amount=").append(money);
		bd.append("&bank_code=").append("");
		bd.append("&card_bind_mobile_phone_no=").append("");
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamGetDynCodeVO vo = new ParamGetDynCodeVO();
		vo.setService(Constants.SERVICE_TYPE_EBATONG_MP_DYNCODE);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setCustomer_id(Constants.USER_ID);
		vo.setOut_trade_no(Constants.PARTNER_NO + "_" + tmpId);
		vo.setAmount(money);
		
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject paramsJson = JSONObject.fromObject(vo);
		System.out.println("paramsJson : " + paramsJson);

		//发送请求
		RequestMethod req = new RequestMethod();
		String result = req.methodPost(Constants.URL_DOMAIN_EBATONG_MP_DYNCODE + "?" + paramsJson.toString());
		
		return result;
	}
	
	/**
	 * 获取时间戳
	 * @return
	 * @throws Exception
	 */
	public static String getTimestamp() throws Exception {
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_QUERY_TIMESTAMP);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamGetTimestampVO vo = new ParamGetTimestampVO();
		vo.setService(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject json = JSONObject.fromObject(vo);
		return json.toString();
	}
	
	/**
	 * 支付-首次支付
	 * @param token
	 * @param msgCode
	 * @param cardNo
	 * @param money
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	public static String pay(String token, String msgCode, String cardNo, String money, String timestamp) throws Exception {
		if (StringUtils.isBlank(token)) {
			throw new Exception("token 不可为空。");
		}
		if (StringUtils.isBlank(msgCode)) {
			throw new Exception("msgCode 不可为空。");
		}
		if (StringUtils.isBlank(cardNo)) {
			throw new Exception("cardNo 不可为空。");
		}
		if (StringUtils.isBlank(money)) {
			throw new Exception("money 不可为空。");
		}
		if (StringUtils.isBlank(timestamp)) {
			throw new Exception("timestamp 不可为空。");
		}
		
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_CREATE_DIRECT_PAY_BY_MP);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		bd.append("&notify_url=").append(Constants.PAY_CALLBACK_NOTIFY_URL);
		bd.append("&customer_id=").append(Constants.USER_ID);
		bd.append("&dynamic_code_token=").append(token);	//“验证码下发”步骤获取的令牌
		bd.append("&dynamic_code=").append(msgCode);	//“验证码下发”步骤向用户手机发送的验证码
		bd.append("&bank_card_no=").append(cardNo);
		bd.append("&real_name=").append(Constants.USER_REAL_NAME);
		bd.append("&cert_no=").append(Constants.USER_IDCARD_NO);
		bd.append("&cert_type=").append(Constants.USER_IDCARD_TYPE);
		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
		int tmpId = Utils.getRandom(1, 1000);
		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
		bd.append("&card_bind_mobile_phone_no=").append(Constants.USER_PHONE);
		bd.append("&subject=").append(Constants.PAY_GOODS_NAME);
		bd.append("&total_fee=").append(money);
		bd.append("&body=").append(Constants.PAY_GOODS_DESCRIPTION);
		bd.append("&show_url=").append(Constants.PAY_CALLBACK_SHOW_URL);
		bd.append("&pay_method=").append(Constants.PAY_PAY_METHOD);
		bd.append("&exter_invoke_ip=").append(Constants.USER_REQUEST_ID);	//用户请求 ip 地址
		bd.append("&anti_phishing_key=").append(timestamp);	//时间戳
		bd.append("&extra_common_param=").append(Constants.PAY_EXTRA_COMMON_PARAM);
		bd.append("&extend_param=").append(Constants.PAY_EXTEND_PARAM);
		bd.append("&default_bank=").append(Constants.BANK_CODE);
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamPayVO vo = new ParamPayVO();
		vo.setService(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setNotify_url(Constants.PAY_CALLBACK_NOTIFY_URL);
		vo.setCustomer_id(Constants.USER_ID);
		vo.setDynamic_code_token(token);
		vo.setDynamic_code(msgCode);
		vo.setBank_card_no(cardNo);
		vo.setReal_name(Constants.USER_REAL_NAME);
		vo.setCert_no(Constants.USER_IDCARD_NO);
		vo.setCert_type(Constants.USER_IDCARD_TYPE);
		vo.setOut_trade_no(Constants.PARTNER_NO + "_" + tmpId);
		vo.setCard_bind_mobile_phone_no(Constants.USER_PHONE);
		vo.setSubject(Constants.PAY_GOODS_NAME);
		vo.setTotal_fee(money);
		vo.setBody(Constants.PAY_GOODS_DESCRIPTION);
		vo.setShow_url(Constants.PAY_CALLBACK_SHOW_URL);
		vo.setPay_method(Constants.PAY_PAY_METHOD);
		vo.setExter_invoke_ip(Constants.USER_REQUEST_ID);
		vo.setAnti_phishing_key(timestamp);
		vo.setExtra_common_param(Constants.PAY_EXTRA_COMMON_PARAM);
		vo.setExtend_param(Constants.PAY_EXTEND_PARAM);
		vo.setDefault_bank(Constants.BANK_CODE);
		
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject json = JSONObject.fromObject(vo);
		return json.toString();
	}
	
	/**
	 * 支付-再次支付
	 * @param token
	 * @param msgCode
	 * @param money
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	public static String pay(String token, String msgCode, String money, String timestamp) throws Exception {
		if (StringUtils.isBlank(token)) {
			throw new Exception("token 不可为空。");
		}
		if (StringUtils.isBlank(msgCode)) {
			throw new Exception("msgCode 不可为空。");
		}
		if (StringUtils.isBlank(money)) {
			throw new Exception("money 不可为空。");
		}
		if (StringUtils.isBlank(timestamp)) {
			throw new Exception("timestamp 不可为空。");
		}
		
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_CREATE_DIRECT_PAY_BY_MP);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		bd.append("&notify_url=").append(Constants.PAY_CALLBACK_NOTIFY_URL);
		bd.append("&customer_id=").append(Constants.USER_ID);
		bd.append("&dynamic_code_token=").append(token);	//“验证码下发”步骤获取的令牌
		bd.append("&dynamic_code=").append(msgCode);	//“验证码下发”步骤向用户手机发送的验证码
		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
		int tmpId = Utils.getRandom(1, 1000);
		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
		bd.append("&subject=").append(Constants.PAY_GOODS_NAME);
		bd.append("&total_fee=").append(money);
		bd.append("&exter_invoke_ip=").append(Constants.USER_REQUEST_ID);	//用户请求 ip 地址
		bd.append("&anti_phishing_key=").append(timestamp);	//时间戳
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamPayVO vo = new ParamPayVO();
		vo.setService(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setNotify_url(Constants.PAY_CALLBACK_NOTIFY_URL);
		vo.setCustomer_id(Constants.USER_ID);
		vo.setDynamic_code_token(token);
		vo.setDynamic_code(msgCode);
		vo.setOut_trade_no(Constants.PARTNER_NO + "_" + tmpId);
		vo.setSubject(Constants.PAY_GOODS_NAME);
		vo.setTotal_fee(money);
		vo.setExter_invoke_ip(Constants.USER_REQUEST_ID);
		vo.setAnti_phishing_key(timestamp);

		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject json = JSONObject.fromObject(vo);
		return json.toString();
	}
	
	/**
	 * 查询用户本次支付结果
	 * @param payId
	 * @return
	 * @throws Exception
	 */
	public static String queryResult(String payId) throws Exception {
		if (StringUtils.isBlank(payId)) {
			throw new Exception("payId 不可为空。");
		}
		
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_CREATE_DIRECT_PAY_BY_MP);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
		int tmpId = Utils.getRandom(1, 1000);
		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamQueryPayResultVO vo = new ParamQueryPayResultVO();
		vo.setService(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setOut_trade_no(Constants.PARTNER_NO + "_" + tmpId);

		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject json = JSONObject.fromObject(vo);
		return json.toString();
	}
	
	/**
	 * 银行卡解绑
	 * @param cardNo
	 * @return
	 * @throws Exception
	 */
	public static String unbindCard(String cardNo) throws Exception {
		if (StringUtils.isBlank(cardNo)) {
			throw new Exception("cardNo 不可为空。");
		}
		
		StringBuilder bd = new StringBuilder();
		bd.append("service=").append(Constants.SERVICE_TYPE_CREATE_DIRECT_PAY_BY_MP);
		bd.append("&partner=").append(Constants.PARTNER_NO);
		bd.append("&input_charset=").append(Constants.ENCODE_TYPE_UTF8);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_MD5);
		bd.append("&notify_url=").append(Constants.UNBIND_CALLBACK_NOTIFY_URL);
		bd.append("&customer_id=").append(Constants.USER_ID);
		bd.append("&bank_card_no=").append(cardNo);
		//商户号 + “_” + 随机数（真实调用时需修改为平台内部交易数据的id）
		int tmpId = Utils.getRandom(1, 1000);
		bd.append("&out_trade_no=").append(Constants.PARTNER_NO + "_" + tmpId);
		bd.append("&card_bind_mobile_phone_no=").append(Constants.USER_PHONE);
		bd.append("&subject=").append(Constants.UNBIND_DESCRIPTION);
		
		System.out.println("原始参数 ：" + bd.toString());
		System.out.println("MD5.key ：" + Constants.SIGN_TYPE_MD5_KEY);
		System.out.println("升序排列且加上key后：" + Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY);
		
		ParamUnbindVO vo = new ParamUnbindVO();
		vo.setService(Constants.SERVICE_TYPE_QUERY_BIND_CARD_INFO);
		vo.setPartner(Constants.PARTNER_NO);
		vo.setInput_charset(Constants.ENCODE_TYPE_UTF8);
		vo.setSign_type(Constants.SIGN_TYPE_MD5);
		vo.setNotify_url(Constants.UNBIND_CALLBACK_NOTIFY_URL);
		vo.setCustomer_id(Constants.USER_ID);
		vo.setBank_card_no(cardNo);
		vo.setOut_trade_no(Constants.PARTNER_NO + "_" + tmpId);
		vo.setCard_bind_mobile_phone_no(Constants.USER_PHONE);
		vo.setSubject(Constants.UNBIND_DESCRIPTION);

		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(EncryptMD5.getMd5String(Utils.sortParamStr(bd.toString()) + Constants.SIGN_TYPE_MD5_KEY));
		
		JSONObject json = JSONObject.fromObject(vo);
		return json.toString();
	}
	
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");

		String retStr = "";
		TestPay pay = new TestPay();
		try {
			//1. 查询用户绑卡情况
//			retStr = pay.queryUserCardList();
			
			//2. 下发验证码
			//2.1 首次支付
//			retStr = pay.getDynCode(Constants.USER_BANK_CARD_NO, Constants.USER_MONEY, Constants.BANK_CODE);
			//2.2 再次支付
			retStr = pay.getDynCode(Constants.USER_MONEY);
//			retStr = testGetDynCode();
			
			//3. 获取时间戳
//			retStr = pay.getTimestamp();
			
			//4. 充值
			//4.1 首次支付
//			retStr = pay.pay("token", "msgCode", Constants.USER_BANK_CARD_NO, Constants.USER_MONEY, "timestamp");
			//4.2 再次支付
//			retStr = pay.pay("token", "msgCode", Constants.USER_MONEY, "timestamp");
			
			//5. 查询处理结果
//			retStr = pay.queryResult(Constants.PARTNER_NO + "_" + 1);	//上次支付数据的id，由前台传递
			
			//6. 银行卡解绑
//			retStr = pay.unbindCard(Constants.USER_BANK_CARD_NO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//打印结果
	    System.out.println("返回： " + retStr);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
	
}
