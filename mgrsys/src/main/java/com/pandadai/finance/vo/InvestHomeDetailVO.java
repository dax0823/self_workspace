/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-1 下午2:38:38
 */
public class InvestHomeDetailVO {
	private String userName;
	private String investTime;
	private float investorCapital;
	private String borrowName;
//	private String collectDay;
	private String borrowDuration;
	private float borrowInterestRate;
	private float investorInterest;
	
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the investTime
	 */
	public String getInvestTime() {
		return investTime;
	}
	/**
	 * @param investTime the investTime to set
	 */
	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	/**
	 * @return the investorCapital
	 */
	public float getInvestorCapital() {
		return investorCapital;
	}
	/**
	 * @param investorCapital the investorCapital to set
	 */
	public void setInvestorCapital(float investorCapital) {
		this.investorCapital = investorCapital;
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
	 * @return the investorInterest
	 */
	public float getInvestorInterest() {
		return investorInterest;
	}
	/**
	 * @param investorInterest the investorInterest to set
	 */
	public void setInvestorInterest(float investorInterest) {
		this.investorInterest = investorInterest;
	}
}
