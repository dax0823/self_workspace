package com.panda.test.pay.ebatong.vo;

/***
 * 查询用户绑卡情况的返回值
 * 
 * @author 仵作
 *
 */
public class ResultQueryUserCardListVO {
	private String result;
	private String bank_card_num;
	private String sign_type;
	private String error_message;
	private String service;
	private String input_charset;
	private String partner;
	private String customer_id;
	private String card_bind_list;
	
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the bank_card_num
	 */
	public String getBank_card_num() {
		return bank_card_num;
	}
	/**
	 * @param bank_card_num the bank_card_num to set
	 */
	public void setBank_card_num(String bank_card_num) {
		this.bank_card_num = bank_card_num;
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
	 * @return the error_message
	 */
	public String getError_message() {
		return error_message;
	}
	/**
	 * @param error_message the error_message to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
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
	 * @return the customer_id
	 */
	public String getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return the card_bind_list
	 */
	public String getCard_bind_list() {
		return card_bind_list;
	}
	/**
	 * @param card_bind_list the card_bind_list to set
	 */
	public void setCard_bind_list(String card_bind_list) {
		this.card_bind_list = card_bind_list;
	}
}
