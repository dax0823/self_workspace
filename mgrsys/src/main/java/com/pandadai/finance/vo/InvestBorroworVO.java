/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-7 下午9:32:23
 */
public class InvestBorroworVO {
//	select m.id, m.user_name, sum(bi.borrow_money) sum_borrow, sum(bi.reward_money) sum_reward, 
//	sum(bi.borrow_interest) sum_interest, sum(bi.borrow_fee) sum_fee
	private String id;
	private String userName;
	private float sumBorrow;
	private float sumReward;
	private float sumInterest;
	private float sumFee;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	 * @return the sumBorrow
	 */
	public float getSumBorrow() {
		return sumBorrow;
	}
	/**
	 * @param sumBorrow the sumBorrow to set
	 */
	public void setSumBorrow(float sumBorrow) {
		this.sumBorrow = sumBorrow;
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
	 * @return the sumFee
	 */
	public float getSumFee() {
		return sumFee;
	}
	/**
	 * @param sumFee the sumFee to set
	 */
	public void setSumFee(float sumFee) {
		this.sumFee = sumFee;
	}
}
