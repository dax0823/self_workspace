package com.pandadai.finance.vo;

/**
 * 
 * @author 仵作
 *
 */
public class InpourDetailVO {
	private String inpourTime;	//充值时间
	private String userName;	//用户名
	private float summy;	//当天充值总额
	private String userId;	//用户 id
	private String way;	//充值方式
	private float money;	//单笔充值金额
	
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getInpourTime() {
		return inpourTime;
	}
	public void setInpourTime(String inpourTime) {
		this.inpourTime = inpourTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getSummy() {
		return summy;
	}
	public void setSummy(float summy) {
		this.summy = summy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
