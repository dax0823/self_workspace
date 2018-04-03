/**
 * 
 */
package com.pandadai.biz.vo;

/**
 * @author 仵作
 * 2014-9-16 下午9:18:12
 */
public class BizHomeAutoDetailVO {
//	select ab.id, ab.is_use, m.user_name, mi.real_name, mm.account_money + mm.back_money usable_money, mm.money_collect, mm.money_freeze
//	, m2.user_name recommend_user, m.customer_name, FROM_UNIXTIME(m.reg_time) reg_time
	private int id;
//	private int isUse;
	private String userName;
	private String realName;
	private float usableMoney;
	private float collectMoney;
	private float freezeMoney;
	private String recommendUser;
	private String customerName;
	private String regTime;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	/**
	 * @return the freezeMoney
	 */
	public float getFreezeMoney() {
		return freezeMoney;
	}
	/**
	 * @param freezeMoney the freezeMoney to set
	 */
	public void setFreezeMoney(float freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	/**
	 * @return the recommendUser
	 */
	public String getRecommendUser() {
		return recommendUser;
	}
	/**
	 * @param recommendUser the recommendUser to set
	 */
	public void setRecommendUser(String recommendUser) {
		this.recommendUser = recommendUser;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
}
