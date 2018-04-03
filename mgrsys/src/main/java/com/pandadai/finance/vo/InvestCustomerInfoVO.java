/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-4 下午7:26:11
 */
public class InvestCustomerInfoVO {
//	select a.*, sum(investor_capital) sum_invest from (
//			select m.id, m.user_name, mi.real_name, (mm.account_money + mm.back_money) account_money, mi.idcard, 
//				mb.bank_num, mb.bank_name, mb.bank_address
	private String userId;
	private String userName;
	private String realName;
	private float accountMoney;
	private String idCard;
	private String bankNum;
	private String bankName;
	private String bankAddress;
	private float sumInvest;

	private String recommendId;
	private String recommendName;
	private float sumUncollected;
	private String[]  invitedName;
	
	public String getRecommendName() {
		return recommendName;
	}
	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public float getSumUncollected() {
		return sumUncollected;
	}
	public void setSumUncollected(float sumUncollected) {
		this.sumUncollected = sumUncollected;
	}
	public String[] getInvitedName() {
		return invitedName;
	}
	public void setInvitedName(String[] invitedName) {
		this.invitedName = invitedName;
	}
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
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * @return the bankNum
	 */
	public String getBankNum() {
		return bankNum;
	}
	/**
	 * @param bankNum the bankNum to set
	 */
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the bankAddress
	 */
	public String getBankAddress() {
		return bankAddress;
	}
	/**
	 * @param bankAddress the bankAddress to set
	 */
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
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
}
