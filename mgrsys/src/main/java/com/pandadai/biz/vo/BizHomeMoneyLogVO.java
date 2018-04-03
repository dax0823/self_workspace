/**
 * 
 */
package com.pandadai.biz.vo;

/**
 * @author 仵作
 * 2014-10-9 上午10:21:34
 */
public class BizHomeMoneyLogVO {
//	select FROM_UNIXTIME(mml.add_time) log_time, mml.affect_money, mml.info from lzh_member_moneylog mml
	private String logTime;
	private float affectMoney;
	private String info;
	
	/**
	 * @return the logTime
	 */
	public String getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	/**
	 * @return the affectMoney
	 */
	public float getAffectMoney() {
		return affectMoney;
	}
	/**
	 * @param affectMoney the affectMoney to set
	 */
	public void setAffectMoney(float affectMoney) {
		this.affectMoney = affectMoney;
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
}
