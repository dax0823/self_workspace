/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-12-31 下午4:10:07
 */
public class InvestRemindInterestDetailVO {
//	select mm.id, mm.user_name, FROM_UNIXTIME(idt.deadline) deadline, idt.interest, idt.sort_order, idt.total
	private String userId;
	private String userName;
	private String deadline;
	private float interest;
	private int sortOrder;
	private int total;
	
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
	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
}
