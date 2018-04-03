/**
 * 
 */
package com.pandadai.adjust.vo;

/**
 * @author 仵作
 * 2014-10-9 下午7:02:17
 */
public class AdjustIntegralLogVO {
//	select type, affect_integral, active_integral, account_integral, info, FROM_UNIXTIME(add_time) time from LZH_MEMBER_INTEGRALLOG
	private int type;
	private int affectIntegral;
	private int activeIntegral;
	private int accountIntegral;
	private String info;
	private String time;
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the affectIntegral
	 */
	public int getAffectIntegral() {
		return affectIntegral;
	}
	/**
	 * @param affectIntegral the affectIntegral to set
	 */
	public void setAffectIntegral(int affectIntegral) {
		this.affectIntegral = affectIntegral;
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
	 * @return the accountIntegral
	 */
	public int getAccountIntegral() {
		return accountIntegral;
	}
	/**
	 * @param accountIntegral the accountIntegral to set
	 */
	public void setAccountIntegral(int accountIntegral) {
		this.accountIntegral = accountIntegral;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
}
