/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-4 下午9:15:51
 */
public class InvestCustomerInterestVO {
//	select bi.borrow_name, id.total, id.sort_order, FROM_UNIXTIME(id.deadline) interest_time, id.interest
	private String borrowId;
	private int duration;
	private String borrowName;
	private String total;
	private String sortOrder;
	private String interestTime;
	private float interest;
	
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
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return the interestTime
	 */
	public String getInterestTime() {
		return interestTime;
	}
	/**
	 * @param interestTime the interestTime to set
	 */
	public void setInterestTime(String interestTime) {
		this.interestTime = interestTime;
	}
	/**
	 * @return the interest
	 */
	public float getInterest() {
		return interest;
	}
	/**
	 * @param interest the interest to set
	 */
	public void setInterest(float interest) {
		this.interest = interest;
	}
}
