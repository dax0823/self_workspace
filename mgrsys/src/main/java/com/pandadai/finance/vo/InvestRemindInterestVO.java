/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-12-31 上午10:48:33
 */
public class InvestRemindInterestVO {
//	select bif.id, bif.borrow_name, bif.borrow_uid, mm.user_name, bif.borrow_money, bif.borrow_interest, bif.borrow_interest_rate, bif.borrow_duration, sum(idt.interest) sum_receive_interest
	private String id;
	private String borrowName;
	private String borrowUserid;
	private String borrowUserName;
	private float borrowMoney;
	private float borrowInterest;
	private float borrowInterestRate;
	private String borrowDuration;
	private float sumReceiveInterest;
	
	/**
	 * @return the borrowUserid
	 */
	public String getBorrowUserid() {
		return borrowUserid;
	}
	/**
	 * @param borrowUserid the borrowUserid to set
	 */
	public void setBorrowUserid(String borrowUserid) {
		this.borrowUserid = borrowUserid;
	}
	/**
	 * @return the borrowUserName
	 */
	public String getBorrowUserName() {
		return borrowUserName;
	}
	/**
	 * @param borrowUserName the borrowUserName to set
	 */
	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
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
	 * @return the sumReceiveInterest
	 */
	public float getSumReceiveInterest() {
		return sumReceiveInterest;
	}
	/**
	 * @param sumReceiveInterest the sumReceiveInterest to set
	 */
	public void setSumReceiveInterest(float sumReceiveInterest) {
		this.sumReceiveInterest = sumReceiveInterest;
	}
}
