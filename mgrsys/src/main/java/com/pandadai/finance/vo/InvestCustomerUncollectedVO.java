package com.pandadai.finance.vo;

/**
 * 
 * @author 仵作
 * 用户当前总待收
 */
public class InvestCustomerUncollectedVO {
//	select mm.id, mm.user_name, sum(idt.capital)
	private String id;
	private String userName;
	private float sumUncollected;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getSumUncollected() {
		return sumUncollected;
	}
	public void setSumUncollected(float sumUncollected) {
		this.sumUncollected = sumUncollected;
	}
}
