/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-7 下午10:40:35
 */
public class InvestBorroworDetailVO {
//	select m.id, m.user_name, bi.id bid, bi.borrow_name, FROM_UNIXTIME(bi.add_time) borrow_time, 
//	bi.borrow_interest, bi.borrow_fee, min(FROM_UNIXTIME(id.deadline)) recently_time
	private String userId;
	private String userName;
	private String borrowId;
	private String borrowName;
	private int duration;
	private String borrowTime;
	private float borrowInterest;
	private float borrowFee;
	private String recentlyTime;
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the borrowId
	 */
	public String getBorrowId() {
		return borrowId;
	}
	/**
	 * @param borrowId the borrowId to set
	 */
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	/**
	 * @return the borrowTime
	 */
	public String getBorrowTime() {
		return borrowTime;
	}
	/**
	 * @param borrowTime the borrowTime to set
	 */
	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
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
	 * @return the borrowFee
	 */
	public float getBorrowFee() {
		return borrowFee;
	}
	/**
	 * @param borrowFee the borrowFee to set
	 */
	public void setBorrowFee(float borrowFee) {
		this.borrowFee = borrowFee;
	}
	/**
	 * @return the recentlyTime
	 */
	public String getRecentlyTime() {
		return recentlyTime;
	}
	/**
	 * @param recentlyTime the recentlyTime to set
	 */
	public void setRecentlyTime(String recentlyTime) {
		this.recentlyTime = recentlyTime;
	}
}
