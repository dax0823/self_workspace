/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-7 上午9:42:36
 */
public class InvestRemindVO {
//	select DATE_FORMAT(FROM_UNIXTIME(id.deadline),'%Y-%m-%d') remind_time, sum(id.interest) sum_interest, sum(id.capital) sum_capital
	private String remindTime;
	private float sumInterest;
	private float sumCapital;
	
	/**
	 * @return the remindTime
	 */
	public String getRemindTime() {
		return remindTime;
	}
	/**
	 * @param remindTime the remindTime to set
	 */
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	/**
	 * @return the sumInterest
	 */
	public float getSumInterest() {
		return sumInterest;
	}
	/**
	 * @param sumInterest the sumInterest to set
	 */
	public void setSumInterest(float sumInterest) {
		this.sumInterest = sumInterest;
	}
	/**
	 * @return the sumCapital
	 */
	public float getSumCapital() {
		return sumCapital;
	}
	/**
	 * @param sumCapital the sumCapital to set
	 */
	public void setSumCapital(float sumCapital) {
		this.sumCapital = sumCapital;
	}
}
