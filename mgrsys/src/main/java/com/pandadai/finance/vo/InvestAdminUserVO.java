/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-5 上午9:47:01
 */
public class InvestAdminUserVO {
//	select aus.id, aus.user_name, aus.real_name, aus.is_kf, aus.is_ban, (sum(m.sum_invest) * 0.001) sum_reward
	private String adminUserId;
	private String adminUserName;
	private String adminRealName;
	private String isKf;
	private String isBan;
	private float sumReward;
	
	/**
	 * @return the adminUserId
	 */
	public String getAdminUserId() {
		return adminUserId;
	}
	/**
	 * @param adminUserId the adminUserId to set
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	/**
	 * @return the adminUserName
	 */
	public String getAdminUserName() {
		return adminUserName;
	}
	/**
	 * @param adminUserName the adminUserName to set
	 */
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	/**
	 * @return the adminRealName
	 */
	public String getAdminRealName() {
		return adminRealName;
	}
	/**
	 * @param adminRealName the adminRealName to set
	 */
	public void setAdminRealName(String adminRealName) {
		this.adminRealName = adminRealName;
	}
	/**
	 * @return the isKf
	 */
	public String getIsKf() {
		return isKf;
	}
	/**
	 * @param isKf the isKf to set
	 */
	public void setIsKf(String isKf) {
		this.isKf = isKf;
	}
	/**
	 * @return the isBan
	 */
	public String getIsBan() {
		return isBan;
	}
	/**
	 * @param isBan the isBan to set
	 */
	public void setIsBan(String isBan) {
		this.isBan = isBan;
	}
	/**
	 * @return the sumReward
	 */
	public float getSumReward() {
		return sumReward;
	}
	/**
	 * @param sumReward the sumReward to set
	 */
	public void setSumReward(float sumReward) {
		this.sumReward = sumReward;
	}
}
