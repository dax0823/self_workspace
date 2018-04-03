/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2015-1-3 下午2:33:14
 */
public class InvestRemindDetailCustomerInfoVO {
//	select mm.id, mm.user_name, mmi.real_name, mm.user_phone, mm.user_email, mmi.idcard, FROM_UNIXTIME(mm.reg_time) reg_time, mmy.account_money + mmy.back_money usable_money, mmy.money_collect
	private String userId;
	private String userName;
	private String realName;
	private String mobile;
	private String email;
	private String IDCard;
	private String regTime;
	private float usableMoney;
	private float collectMoney;
	
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the iDCard
	 */
	public String getIDCard() {
		return IDCard;
	}
	/**
	 * @param iDCard the iDCard to set
	 */
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	/**
	 * @return the regTime
	 */
	public String getRegTime() {
		return regTime;
	}
	/**
	 * @param regTime the regTime to set
	 */
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	/**
	 * @return the usableMoney
	 */
	public float getUsableMoney() {
		return usableMoney;
	}
	/**
	 * @param usableMoney the usableMoney to set
	 */
	public void setUsableMoney(float usableMoney) {
		this.usableMoney = usableMoney;
	}
	/**
	 * @return the collectMoney
	 */
	public float getCollectMoney() {
		return collectMoney;
	}
	/**
	 * @param collectMoney the collectMoney to set
	 */
	public void setCollectMoney(float collectMoney) {
		this.collectMoney = collectMoney;
	}
}
