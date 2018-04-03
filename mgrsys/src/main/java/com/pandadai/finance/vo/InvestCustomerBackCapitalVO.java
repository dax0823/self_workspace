/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-4 下午9:32:06
 */
public class InvestCustomerBackCapitalVO {
//	select bi.borrow_name, FROM_UNIXTIME(id.deadline) back_time, id.capital
	private String borrowId;
	private int duration;
	private String borrowName;
	private String backTime;
	private float capital;
	
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * @return the capital
	 */
	public float getCapital() {
		return capital;
	}
	/**
	 * @param capital the capital to set
	 */
	public void setCapital(float capital) {
		this.capital = capital;
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
	 * @return the backTime
	 */
	public String getBackTime() {
		return backTime;
	}
	/**
	 * @param backTime the backTime to set
	 */
	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}
}
