/**
 * 
 */
package com.pandadai.biz.vo;

/**
 * @author 仵作
 * 2014-10-7 下午12:32:21
 */
public class BizHomeCustomerInfoMoneyVO {
//	select (mm.money_freeze + money_collect + account_money + back_money) account_sum
//	, money_collect, (account_money + back_money) usable_money, money_freeze
	private float accountSum;
	private float collectMoney;
	private float usableMoney;
	private float freezeMoney;
	
	/**
	 * @return the accountSum
	 */
	public float getAccountSum() {
		return accountSum;
	}
	/**
	 * @param accountSum the accountSum to set
	 */
	public void setAccountSum(float accountSum) {
		this.accountSum = accountSum;
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
}
