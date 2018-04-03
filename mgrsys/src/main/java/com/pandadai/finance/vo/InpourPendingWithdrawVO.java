/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2015-1-15 下午5:39:52
 */
public class InpourPendingWithdrawVO {
//	select a.id, a.user_name, a.withdraw_time, a.withdraw_money, a.account_money, sum(mp.money) as inpour_money
	private String userId;
	private String userName;
	private String realName;
	private String withdrawTime;
	private float withdrawMoney;
	private float accountMoney;	//含冻结金额（mmy.account_money + mmy.back_money + mmy.money_freeze）
	private float inpourMoney;	//15天内充值金额
	private float freeMoney;	//免手续费金额
	private float feeMoney;	//手续费金额
	private float finalMoney;	//实际提现金额
	
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
	 * @return the withdrawTime
	 */
	public String getWithdrawTime() {
		return withdrawTime;
	}
	/**
	 * @param withdrawTime the withdrawTime to set
	 */
	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	/**
	 * @return the withdrawMoney
	 */
	public float getWithdrawMoney() {
		return withdrawMoney;
	}
	/**
	 * @param withdrawMoney the withdrawMoney to set
	 */
	public void setWithdrawMoney(float withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
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
	 * @return the inpourMoney
	 */
	public float getInpourMoney() {
		return inpourMoney;
	}
	/**
	 * @param inpourMoney the inpourMoney to set
	 */
	public void setInpourMoney(float inpourMoney) {
		this.inpourMoney = inpourMoney;
	}
	/**
	 * @return the freeMoney
	 */
	public float getFreeMoney() {
		return freeMoney;
	}
	/**
	 * @param freeMoney the freeMoney to set
	 */
	public void setFreeMoney(float freeMoney) {
		this.freeMoney = freeMoney;
	}
	/**
	 * @return the feeMoney
	 */
	public float getFeeMoney() {
		return feeMoney;
	}
	/**
	 * @param feeMoney the feeMoney to set
	 */
	public void setFeeMoney(float feeMoney) {
		this.feeMoney = feeMoney;
	}
	/**
	 * @return the finalMoney
	 */
	public float getFinalMoney() {
		return finalMoney;
	}
	/**
	 * @param finalMoney the finalMoney to set
	 */
	public void setFinalMoney(float finalMoney) {
		this.finalMoney = finalMoney;
	}
}
