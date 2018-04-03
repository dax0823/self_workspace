package com.panda.test.pay.ebatong.vo;

/**
 * 获取时间戳的参数
 * 
 * @author 仵作
 *
 */
public class ParamGetTimestampVO {
	private String service;
	private String partner;
	private String input_charset;
	private String sign_type;
	private String sign;
	
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}
	/**
	 * @param partner the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}
	/**
	 * @return the input_charset
	 */
	public String getInput_charset() {
		return input_charset;
	}
	/**
	 * @param input_charset the input_charset to set
	 */
	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}
	/**
	 * @return the sign_type
	 */
	public String getSign_type() {
		return sign_type;
	}
	/**
	 * @param sign_type the sign_type to set
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
}
