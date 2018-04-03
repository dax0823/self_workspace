/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-8-31 下午8:55:15
 */
public class InvestHomeVO {
	private String id;
	private String borrowName;
	private float borrowMoney;
	private float borrowInterest;
	private float borrowInterestRate;
	private float rewardNum;
//	private String collectDay;
	private String borrowDuration;
//	private String collectTime;
	private String deadline;
	
	/**
	 * @return the deadline
	 */
	public String getDeadline() {
		return deadline;
	}
	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	/**
	 * @return the borrowDuration
	 */
	public String getBorrowDuration() {
		return borrowDuration;
	}
	/**
	 * @param borrowDuration the borrowDuration to set
	 */
	public void setBorrowDuration(String borrowDuration) {
		this.borrowDuration = borrowDuration;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the borrowName
	 */
	public String getBorrowName() {
		return borrowName;
	}
	/**
	 * @param borrowName the borrowName to set
	 */
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	/**
	 * @return the borrowMoney
	 */
	public float getBorrowMoney() {
		return borrowMoney;
	}
	/**
	 * @param borrowMoney the borrowMoney to set
	 */
	public void setBorrowMoney(float borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	/**
	 * @return the borrowInterest
	 */
	public float getBorrowInterest() {
		return borrowInterest;
	}
	/**
	 * @param borrowInterest the borrowInterest to set
	 */
	public void setBorrowInterest(float borrowInterest) {
		this.borrowInterest = borrowInterest;
	}
	/**
	 * @return the borrowInterestRate
	 */
	public float getBorrowInterestRate() {
		return borrowInterestRate;
	}
	/**
	 * @param borrowInterestRate the borrowInterestRate to set
	 */
	public void setBorrowInterestRate(float borrowInterestRate) {
		this.borrowInterestRate = borrowInterestRate;
	}
	/**
	 * @return the rewardNum
	 */
	public float getRewardNum() {
		return rewardNum;
	}
	/**
	 * @param rewardNum the rewardNum to set
	 */
	public void setRewardNum(float rewardNum) {
		this.rewardNum = rewardNum;
	}
//	/**
//	 * @return the collectDay
//	 */
//	public String getCollectDay() {
//		return collectDay;
//	}
//	/**
//	 * @param collectDay the collectDay to set
//	 */
//	public void setCollectDay(String collectDay) {
//		this.collectDay = collectDay;
//	}
//	/**
//	 * @return the collectTime
//	 */
//	public String getCollectTime() {
//		return collectTime;
//	}
//	/**
//	 * @param collectTime the collectTime to set
//	 */
//	public void setCollectTime(String collectTime) {
//		this.collectTime = collectTime;
//	}
}
