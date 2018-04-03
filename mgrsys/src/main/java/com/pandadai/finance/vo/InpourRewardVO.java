package com.pandadai.finance.vo;

/**
 * 线下充值奖励对象
 * @author DingDing
 *
 */
public class InpourRewardVO {
	private String userId;
	private String rewardMonth;
	private String userName;
	private float rewardSum;
	private float moneySum;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRewardMonth() {
		return rewardMonth;
	}
	public void setRewardMonth(String rewardMonth) {
		this.rewardMonth = rewardMonth;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getRewardSum() {
		return rewardSum;
	}
	public void setRewardSum(float rewardSum) {
		this.rewardSum = rewardSum;
	}
	public float getMoneySum() {
		return moneySum;
	}
	public void setMoneySum(float moneySum) {
		this.moneySum = moneySum;
	}
}
