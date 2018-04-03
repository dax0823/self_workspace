package com.pandadai.finance.vo;

/**
 * 线下充值奖励单个客户对象
 * @author 仵作
 *
 */
public class InpourRewardCusVO {
	private String userId;
	private String rewardTime;
	private String userName;
	private float money;
	private float rewardMoney;
	private String rate;	//仅用于页面展示
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}
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
	public float getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(float rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
}
