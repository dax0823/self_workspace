/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-8-30 下午11:41:39
 */
public class InpourReviseVO {
	private String userId;
	private String userName;
	private String realName;
	private String reviseTime;
	private float reviseMoney;
	private String reason;
	
	/**
	 * @return the reviseMoney
	 */
	public float getReviseMoney() {
		return reviseMoney;
	}
	/**
	 * @param reviseMoney the reviseMoney to set
	 */
	public void setReviseMoney(float reviseMoney) {
		this.reviseMoney = reviseMoney;
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
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * @return the reviseTime
	 */
	public String getReviseTime() {
		return reviseTime;
	}
	/**
	 * @param reviseTime the reviseTime to set
	 */
	public void setReviseTime(String reviseTime) {
		this.reviseTime = reviseTime;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
}
