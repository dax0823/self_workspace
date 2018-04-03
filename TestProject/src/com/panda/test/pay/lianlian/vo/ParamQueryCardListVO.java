package com.panda.test.pay.lianlian.vo;

/**
 * 查询用户绑卡情况
 * @author 仵作
 *
 */
public class ParamQueryCardListVO {
	// 商户提交参数
	private String oid_partner = "201408071000001543";	//商户号
	private String user_id = "44";	//用户唯一标识（用户在平台内的 id）
	private String pay_type = "D";	//支付方式，D：认证支付
	private String sign_type = "RSA";	//加密方式
	private String sign = "";
	private String offset = "0"; //偏移量
	
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
}
