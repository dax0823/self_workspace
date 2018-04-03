/**
 * 
 */
package com.pandadai.biz.vo;

/**
 * @author 仵作
 * 2014-10-9 上午10:01:12
 */
public class BizHomeInvestVO {
//	select bi.borrow_name, FROM_UNIXTIME(biv.add_time) invest_time, biv.investor_capital, FROM_UNIXTIME(bi.deadline) end_time
	private String borrowName;
	private String investTime;
	private float investorCapital;
	private String endTime;
	
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
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
