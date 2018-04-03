/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-3 下午8:23:23
 */
public class InvestCustomerVO {
//	select m.id, m.user_name, aus.id aid, aus.user_name auser_name, (mm.account_money + mm.back_money) account_money,
//	sum(biv.investor_capital) sum_invest, sum(biv.investor_interest) sum_interest, sum(biv.invest_fee) sum_fee
//	sum(mml.affect_money) sum_recommend_reward
//	sum(mp.money) sum_inpour
	private String id;
	private String userName;
	private String aId;
	private String aUserName;
	private float accountMoney;
	private float sumInvest;
	private float sumInterest;
	private float sumFee;
	private float sumRecommendReward;
	private float sumInpour;
	private float sumAuserReward;
	
	/**
	 * @return the sumAuserReward
	 */
	public float getSumAuserReward() {
		return sumAuserReward;
	}
	/**
	 * @param sumAuserReward the sumAuserReward to set
	 */
	public void setSumAuserReward(float sumAuserReward) {
		this.sumAuserReward = sumAuserReward;
	}
	/**
	 * @return the sumInpour
	 */
	public float getSumInpour() {
		return sumInpour;
	}
	/**
	 * @param sumInpour the sumInpour to set
	 */
	public void setSumInpour(float sumInpour) {
		this.sumInpour = sumInpour;
	}
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
	 * @return the aId
	 */
	public String getaId() {
		return aId;
	}
	/**
	 * @param aId the aId to set
	 */
	public void setaId(String aId) {
		this.aId = aId;
	}
	/**
	 * @return the aUserName
	 */
	public String getaUserName() {
		return aUserName;
	}
	/**
	 * @param aUserName the aUserName to set
	 */
	public void setaUserName(String aUserName) {
		this.aUserName = aUserName;
	}
	/**
	 * @return the accountMoney
	 */
	public float getAccountMoney() {
		return accountMoney;
	}
	/**
	 * @param accountMoney the accountMoney to set
	 */
	public void setAccountMoney(float accountMoney) {
		this.accountMoney = accountMoney;
	}
	/**
	 * @return the sumInvest
	 */
	public float getSumInvest() {
		return sumInvest;
	}
	/**
	 * @param sumInvest the sumInvest to set
	 */
	public void setSumInvest(float sumInvest) {
		this.sumInvest = sumInvest;
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
	/**
	 * @return the sumRecommendReward
	 */
	public float getSumRecommendReward() {
		return sumRecommendReward;
	}
	/**
	 * @param sumRecommendReward the sumRecommendReward to set
	 */
	public void setSumRecommendReward(float sumRecommendReward) {
		this.sumRecommendReward = sumRecommendReward;
	}
}
