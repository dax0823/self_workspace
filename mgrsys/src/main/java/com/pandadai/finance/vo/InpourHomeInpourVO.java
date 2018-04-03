package com.pandadai.finance.vo;

public class InpourHomeInpourVO {
	private String userName;	//用户名
	private float money;	//充值金额
	private String inpourTime;	//充值时间
	private String way;	//充值方式
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public String getInpourTime() {
		return inpourTime;
	}
	public void setInpourTime(String inpourTime) {
		this.inpourTime = inpourTime;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	
}
