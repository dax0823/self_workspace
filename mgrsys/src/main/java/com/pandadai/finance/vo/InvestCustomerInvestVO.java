/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-4 下午9:04:12
 */
public class InvestCustomerInvestVO {
//	select bi.borrow_name, biv.investor_capital, FROM_UNIXTIME(biv.add_time) invest_time
	private String borrowId;
	private String borrowName;
	private int duration;
	private float investorCapital;
	private String investTime;
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
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
}
