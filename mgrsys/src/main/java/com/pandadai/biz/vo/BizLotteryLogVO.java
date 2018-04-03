/**
 * 
 */
package com.pandadai.biz.vo;

/**
 * @author 仵作
 * 2014-12-3 上午11:02:24
 */
public class BizLotteryLogVO {
//	select mm.id, mm.user_name, mmi.real_name, ml.name, ml.info, FROM_UNIXTIME(ml.add_time) lottery_time
	private int id;
	private String userName;
	private String realName;
	private String mobile;
	private String name;
	private String info;
	private String lotteryTime;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the lotteryTime
	 */
	public String getLotteryTime() {
		return lotteryTime;
	}
	/**
	 * @param lotteryTime the lotteryTime to set
	 */
	public void setLotteryTime(String lotteryTime) {
		this.lotteryTime = lotteryTime;
	}
}
