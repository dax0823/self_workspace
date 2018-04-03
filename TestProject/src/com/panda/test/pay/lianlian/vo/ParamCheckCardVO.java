package com.panda.test.pay.lianlian.vo;

/**
 * 验卡
 * @author 仵作
 *
 */
public class ParamCheckCardVO {
	// 商户提交参数
	private String oid_partner = "201408071000001543";	//商户号
	private String sign_type = "RSA";	//加密方式
	private String sign = "";
	private String card_no = "";
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
}
