/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-12-31 下午4:08:13
 */
public class InvestRemindCapitalDetailVO {
//	select mm.id, mm.user_name, FROM_UNIXTIME(idt.deadline) deadline, idt.capital
	private String userId;
	private String userName;
	private String deadline;
	private float capital;
	
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
}
