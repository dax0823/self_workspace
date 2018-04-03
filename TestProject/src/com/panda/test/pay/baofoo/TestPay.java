package com.panda.test.pay.baofoo;

//import it.sauronsoftware.base64.Base64;
//import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.panda.test.pay.baofoo.util.Constants;
import com.panda.test.pay.baofoo.util.SecurityUtil;
import com.panda.test.pay.baofoo.vo.ParamPayEncryptVO;
import com.panda.test.pay.baofoo.vo.ParamPayVO;
import com.panda.test.pay.ebatong.util.Utils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 宝付
 * @author 仵作
 *
 */
public class TestPay {
	
	/**
	 * 支付接口-业务处理
	 * @param payCode
	 * @param accNo
	 * @param IDCard
	 * @param IDHolder
	 * @param mobile
	 * @param txnAmt
	 * @return
	 * @throws Exception
	 */
	public static String pay(String payCode, String accNo, String IDCard, String IDHolder
			, String mobile, String txnAmt) throws Exception {
		if (StringUtils.isBlank(payCode)) {
			throw new Exception("银行编码 pay_code 不可为空。");
		}
		if (StringUtils.isBlank(accNo)) {
			throw new Exception("卡号 accNo 不可为空。");
		}
		if (StringUtils.isBlank(IDCard)) {
			throw new Exception("身份证号 IDCard 不可为空。");
		}
		if (StringUtils.isBlank(IDHolder)) {
			throw new Exception("持卡人姓名 IDHolder 不可为空。");
		}
		if (StringUtils.isBlank(mobile)) {
			throw new Exception("银行卡预留手机号 mobile 不可为空。");
		}
//		if (StringUtils.isBlank(validDate)) {
//			throw new Exception("卡有效期 validDate 不可为空。");
//		}
//		if (StringUtils.isBlank(validNo)) {
//			throw new Exception("卡安全码 validNo 不可为空。");
//		}
		if (StringUtils.isBlank(txnAmt)) {
			throw new Exception("交易金额 txnAmt 不可为空。");
		}
		
//		private String txn_sub_type;
//		private String biz_type;
//		private String terminal_id;
//		private String member_id;
//		private String pay_code;
//		private String acc_no;
//		private String id_card_type;
//		private String id_card;
//		private String id_holder;
//		private String mobile;
//		private String valid_date;
//		private String valid_no;
//		private String trans_id;
//		private String txn_amt;
//		private String trade_date;
//		private String commodity_name;
//		private String commodity_amount;
//		private String user_name;
//		private String page_url;
//		private String return_url;
//		private String additional_info;
//		private String req_reserved;
		
		ParamPayEncryptVO encryptVo = new ParamPayEncryptVO();
		encryptVo.setTxn_sub_type(Constants.TXN_SUB_TYPE);
		encryptVo.setBiz_type(Constants.BIZ_TYPE);
		encryptVo.setTerminal_id(Constants.TERMINAL_ID);
		encryptVo.setMember_id(Constants.MEMBER_ID);
		encryptVo.setPay_code(payCode);
		encryptVo.setAcc_no(accNo);
//		encryptVo.setId_card_type(Constants.ID_CARD_TYPE);
		encryptVo.setId_card(IDCard);
		encryptVo.setId_holder(IDHolder);
		encryptVo.setMobile(mobile);
//		encryptVo.setValid_date(validDate);
//		encryptVo.setValid_no(validNo);
		int randomTranlId = Utils.getRandom(1, 1000);
		encryptVo.setTrans_id(Constants.MEMBER_ID + "_" + randomTranlId);	//商户号 + "_" + 随机数[1，1000]
		encryptVo.setTxn_amt(txnAmt);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		encryptVo.setTrade_date(format.format(new Date()));
//		encryptVo.setCommodity_name(Constants.COMMODITY_NAME);
//		encryptVo.setCommodity_amount(Constants.COMMODITY_AMOUNT);
//		encryptVo.setUser_name("");
		encryptVo.setPage_url(Constants.PAGE_URL);
		encryptVo.setReturn_url(Constants.RETURN_URL);
//		encryptVo.setAdditional_info("");	//附加字段
//		encryptVo.setReq_reserved("");	//请求方保留域
		
		JSONObject encryptVoJson = JSONObject.fromObject(encryptVo);
		System.out.println("pay.ParamPayEncryptVO：" + encryptVoJson.toString());
		
//		private String version;
//		private String input_charset;
//		private String language;
//		private String terminal_id;
//		private String txn_type;
//		private String txn_sub_type;
//		private String member_id;
//		private String data_type;
//		private String data_content;
//		private String back_url;
		ParamPayVO vo = new ParamPayVO();
		vo.setVersion(Constants.VERSION);
		vo.setInput_charset(Constants.INPUT_CHARSET);
		vo.setLanguage(Constants.LANGUAGE);
		vo.setTerminal_id(Constants.TERMINAL_ID);
		vo.setTxn_type(Constants.TXN_TYPE);
		vo.setTxn_sub_type(Constants.TXN_SUB_TYPE);
		vo.setMember_id(Constants.MEMBER_ID);
		vo.setData_type(Constants.DATA_TYPE);
		vo.setData_content(SecurityUtil.Base64Encode(encryptVoJson.toString()));	//需加密内容
		vo.setBack_url(Constants.BACK_URL);

		JSONObject voJson = JSONObject.fromObject(vo);
		
		return voJson.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");

		final String payCode = Constants.USER_BANK_CODE;
		final String accNo = Constants.USER_BANK_CARD_NO;
		final String IDCard = Constants.USER_IDCARD_NO;
		final String IDHolder = Constants.USER_REAL_NAME;
		final String mobile = Constants.USER_PHONE;
//		final String validDate = "";
//		final String validNo = "";
		final String txnAmt = Constants.USER_MONEY;
		
		TestPay pay = new TestPay();
		String params = "";
		
		try {
			params = pay.pay(payCode, accNo, IDCard, IDHolder, mobile, txnAmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("baofoo.TestPay.pay：" + params);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}

}
