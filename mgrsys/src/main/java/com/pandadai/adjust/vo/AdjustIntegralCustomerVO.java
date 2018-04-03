/**
 * 
 */
package com.pandadai.adjust.vo;

/**
 * @author 仵作
 * 2014-10-9 下午6:57:38
 */
public class AdjustIntegralCustomerVO {
//	select m.id, m.user_name, m.integral, m.active_integral, FROM_UNIXTIME(m.reg_time) reg_time from lzh_members m
	private int id;
	private String userName;
	private int integral;
	private int activeIntegral;
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
	 * @return the integral
	 */
	public int getIntegral() {
		return integral;
	}
	/**
	 * @param integral the integral to set
	 */
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	/**
	 * @return the activeIntegral
	 */
	public int getActiveIntegral() {
		return activeIntegral;
	}
	/**
	 * @param activeIntegral the activeIntegral to set
	 */
	public void setActiveIntegral(int activeIntegral) {
		this.activeIntegral = activeIntegral;
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
