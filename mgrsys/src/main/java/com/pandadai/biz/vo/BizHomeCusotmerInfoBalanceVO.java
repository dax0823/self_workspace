/**
 * 
 */
package com.pandadai.biz.vo;

/**
 * @author 仵作
 * 2014-10-7 下午12:28:20
 */
public class BizHomeCusotmerInfoBalanceVO {
//	select count(1) num, sum(money) inpour_sum
//	select count(1) num, sum(withdraw_money) withdraw_sum
	private int num;
	private float money;
	
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the money
	 */
	public float getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(float money) {
		this.money = money;
	}
}
