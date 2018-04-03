package com.panda.test.pay.lianlian;

import net.sf.json.JSONObject;

import com.panda.test.pay.lianlian.util.Constants;
import com.panda.test.pay.lianlian.util.DateUtil;
import com.panda.test.pay.lianlian.util.FuncUtils;
import com.panda.test.pay.lianlian.util.RSAUtil;
import com.panda.test.pay.lianlian.vo.ParamCheckCardVO;
import com.panda.test.pay.lianlian.vo.ParamPayVO;
import com.panda.test.pay.lianlian.vo.ParamQueryCardListVO;
import com.panda.test.pay.lianlian.vo.ParamRiskItemVO;

public class TestPay {
	/**
	 * 查询用户已绑卡情况
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String queryCardList(String userId) throws Exception {
		if (FuncUtils.isNull(userId)) {
			throw new Exception("user_id 不可为空。");
		}

		StringBuilder bd = new StringBuilder();
		bd.append("oid_partner=").append(Constants.OID_PARTNER);
		bd.append("&user_id=").append(userId);
		bd.append("&pay_type=").append(Constants.QUERY_CARD_LIST_PAY_TYPE);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_RSA);
		bd.append("&offset=").append(Constants.QUERY_CARD_LIST_OFFSET);
		
//		System.out.println("before = " + bd.toString());
//		System.out.println("after = " + FuncUtils.sortParamStr(bd.toString()));
		
		ParamQueryCardListVO vo = new ParamQueryCardListVO();
		vo.setOid_partner(Constants.OID_PARTNER);
		vo.setUser_id(userId);
		vo.setPay_type(Constants.QUERY_CARD_LIST_PAY_TYPE);
		vo.setSign_type(Constants.SIGN_TYPE_RSA);
		vo.setOffset(Constants.QUERY_CARD_LIST_OFFSET);
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(RSAUtil.sign(Constants.TRADER_PRI_KEY, FuncUtils.sortParamStr(bd.toString())));
		
		JSONObject json = JSONObject.fromObject(vo);
		
		return json.toString();
	}

	/**
	 * 首次支付前，先验证用户银行卡是否有效
	 * @throws Exception
	 */
	public String checkCard(String cardNo) throws Exception {
		if (FuncUtils.isNull(cardNo)) {
			throw new Exception("card_no 不可为空。");
		}

		StringBuilder bd = new StringBuilder();
		bd.append("oid_partner=").append(Constants.OID_PARTNER);
		bd.append("&sign_type=").append(Constants.SIGN_TYPE_RSA);
		bd.append("&card_no=").append(Constants.USER_BANK_CARD_NO);
		
		System.out.println("before = " + bd.toString());
		System.out.println("after = " + FuncUtils.sortParamStr(bd.toString()));
		
		ParamCheckCardVO vo = new ParamCheckCardVO();
		vo.setOid_partner(Constants.OID_PARTNER);
		vo.setSign_type(Constants.SIGN_TYPE_RSA);
		vo.setCard_no(cardNo);
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(RSAUtil.sign(Constants.TRADER_PRI_KEY, FuncUtils.sortParamStr(bd.toString())));
		
		JSONObject json = JSONObject.fromObject(vo);
		
		return json.toString();
	}
	
	/**
	 * 支付
	 * @param userId
	 * @param realName
	 * @param cardNo
	 * @param idNo
	 * @param money
	 * @param noOrder
	 * @param nameGoods
	 * @param notifyUrl
	 * @param riskItem
	 * @return
	 * @throws Exception
	 */
	public String pay(String userId, String realName, String cardNo, String idNo, String money, String noOrder, String nameGoods,
			String notifyUrl, String registerTime, String phone, String identifyState, String identifyType) throws Exception {
		if (FuncUtils.isNull(userId)) {
			throw new Exception("userId 不可为空。");
		}
		if (FuncUtils.isNull(realName)) {
			throw new Exception("realName 不可为空。");
		}
		if (FuncUtils.isNull(cardNo)) {
			throw new Exception("card_no 不可为空。");
		}
		if (FuncUtils.isNull(money)) {
			throw new Exception("money 不可为空。");
		}
		if (FuncUtils.isNull(noOrder)) {
			throw new Exception("noOrder 不可为空。");
		}
		if (FuncUtils.isNull(nameGoods)) {
			throw new Exception("nameGoods 不可为空。");
		}
		if (FuncUtils.isNull(notifyUrl)) {
			throw new Exception("notifyUrl 不可为空。");
		}
		if (FuncUtils.isNull(registerTime)) {
			throw new Exception("registerTime 不可为空。");
		}
		if (FuncUtils.isNull(phone)) {
			throw new Exception("phone 不可为空。");
		}
		if (FuncUtils.isNull(identifyState)) {
			throw new Exception("identifyState 不可为空。");
		}
		if (FuncUtils.isNull(identifyType)) {
			throw new Exception("identifyType 不可为空。");
		}
		
		String dtOrder = DateUtil.getCurrentDateTimeStr1();
		StringBuilder bd = new StringBuilder();
		bd.append("user_id=").append(userId);
		bd.append("&acct_name=").append(realName);
		bd.append("&card_no=").append(cardNo);
		bd.append("&id_type=").append(Constants.PAY_ID_TYPE);
		bd.append("&id_no=").append(idNo);
		bd.append("&money_order=").append(money);
		bd.append("&version=").append(Constants.PAY_VERSION);
		bd.append("&oid_partner=").append(Constants.OID_PARTNER);
		bd.append("&app_request=").append(Constants.PAY_APP_REQUEST);
		bd.append("&no_order=").append(noOrder);
		bd.append("&dt_order=").append(dtOrder);
		bd.append("&name_goods=").append(nameGoods);
		bd.append("&notify_url=").append(notifyUrl);
		
		ParamPayVO vo = new ParamPayVO();
		vo.setUser_id(userId);
		vo.setAcct_name(realName);
		vo.setCard_no(cardNo);
		vo.setId_type(Constants.PAY_ID_TYPE);
		vo.setId_no(idNo);
		vo.setMoney_order(money);
		vo.setVersion(Constants.PAY_VERSION);
		vo.setOid_partner(Constants.OID_PARTNER);
		vo.setApp_request(Constants.PAY_APP_REQUEST);
		vo.setNo_order(noOrder);
		vo.setDt_order(dtOrder);
		vo.setName_goods(nameGoods);
		vo.setNotify_url(notifyUrl);
		
		//risk_item 内的参数不需要进行生序排序
		//risk_item 需要参与签名
//		ParamPayVO.RiskItem risk = vo.getRisk_item();
		ParamRiskItemVO risk = new ParamRiskItemVO();
		risk.setFrms_ware_category(Constants.PAY_GOODS_TYPE_2009);
		risk.setUser_info_bind_phone(phone);
		risk.setUser_info_dt_register(registerTime);
		risk.setUser_info_full_name(realName);
		risk.setUser_info_id_no(cardNo);
		risk.setUser_info_identify_state(identifyState);
		risk.setUser_info_identify_type(identifyType);
		risk.setUser_info_mercht_userno(userId);
		vo.setRisk_item(risk);

		//将风险控制参数填入加密字符串中
		bd.append("&risk_item=").append(JSONObject.fromObject(risk));
//		System.out.println("before = " + bd.toString());
//		System.out.println("after = " + FuncUtils.sortParamStr(bd.toString()));
		
		//先将所有参数进行升序排序，然后再进行加密
		vo.setSign(RSAUtil.sign(Constants.TRADER_PRI_KEY, FuncUtils.sortParamStr(bd.toString())));
		
		JSONObject json = JSONObject.fromObject(vo);
		
		return json.toString();
	}
	
	/**
	 * 调试排序
	 * @return
	 * @throws Exception
	 */
	public String test() throws Exception {
		StringBuilder bd = new StringBuilder();
		bd.append("d=").append("d");
		bd.append("&c=").append("c");
		bd.append("&b=").append("b");
		bd.append("&a=").append("a");
		
//		return RSAUtil.sign(Constants.TRADER_PRI_KEY, bd.toString());
		return FuncUtils.sortParamStr(bd.toString());
	}
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");

		String url = "";
		TestPay pay = new TestPay();
		try {
			//1. 查询用户绑卡情况
//			url = pay.queryCardList(Constants.USER_ID);
			//2. 首次支付，验卡
//			url = pay.checkCard(Constants.CARD_NO);
			//3. 支付（充值）
//			url = pay.pay(userId, realName, cardNo, idNo, money, noOrder, nameGoods, notifyUrl, riskItem, registerTime, phone, identifyState, identifyType)
			url = pay.pay(Constants.USER_ID, Constants.USER_REAL_NAME, Constants.USER_BANK_CARD_NO, Constants.USER_IDCARD_NO, Constants.USER_MONEY, "1", "金融产品充值调试", 
					Constants.PAY_NOTIFY_URL, Constants.USER_REGISTER_TIME, Constants.USER_PHONE, Constants.USER_IDENTIFY_STATE, Constants.USER_IDENTIFY_TYPE);
			//4. 查询处理结果
			
			
//			url = pay.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//打印结果
	    System.out.println("url = " + url);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
}
