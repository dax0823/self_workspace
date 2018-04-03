package com.panda.test.pay.baofoo.vo;

/***
 * 充值接口-系统级参数对象
 * @author 仵作
 *
 */
public class ParamPayVO {
	private String version;
	private String input_charset;
	private String language;
	private String terminal_id;
	private String txn_type;
	private String txn_sub_type;
	private String member_id;
	private String data_type;
	private String data_content;
	private String back_url;
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the terminal_id
	 */
	public String getTerminal_id() {
		return terminal_id;
	}
	/**
	 * @param terminal_id the terminal_id to set
	 */
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	/**
	 * @return the txn_type
	 */
	public String getTxn_type() {
		return txn_type;
	}
	/**
	 * @param txn_type the txn_type to set
	 */
	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}
	/**
	 * @return the txn_sub_type
	 */
	public String getTxn_sub_type() {
		return txn_sub_type;
	}
	/**
	 * @param txn_sub_type the txn_sub_type to set
	 */
	public void setTxn_sub_type(String txn_sub_type) {
		this.txn_sub_type = txn_sub_type;
	}
	/**
	 * @return the member_id
	 */
	public String getMember_id() {
		return member_id;
	}
	/**
	 * @param member_id the member_id to set
	 */
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	/**
	 * @return the data_type
	 */
	public String getData_type() {
		return data_type;
	}
	/**
	 * @param data_type the data_type to set
	 */
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	/**
	 * @return the data_content
	 */
	public String getData_content() {
		return data_content;
	}
	/**
	 * @param data_content the data_content to set
	 */
	public void setData_content(String data_content) {
		this.data_content = data_content;
	}
	/**
	 * @return the back_url
	 */
	public String getBack_url() {
		return back_url;
	}
	/**
	 * @param back_url the back_url to set
	 */
	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}
}
