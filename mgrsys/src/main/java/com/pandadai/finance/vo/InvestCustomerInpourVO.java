/**
 * 
 */
package com.pandadai.finance.vo;

/**
 * @author 仵作
 * 2014-9-4 下午8:47:02
 */
public class InvestCustomerInpourVO {
//	select FROM_UNIXTIME(mp.add_time) inpour_time, mp.money, mp.way 
	private String inpourTime;
	private float money;
	private String way;
	
	/**
	 * @return the inpourTime
	 */
	public String getInpourTime() {
		return inpourTime;
	}
	/**
	 * @param inpourTime the inpourTime to set
	 */
	public void setInpourTime(String inpourTime) {
		this.inpourTime = inpourTime;
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
	/**
	 * @return the way
	 */
	public String getWay() {
		return way;
	}
	/**
	 * @param way the way to set
	 */
	public void setWay(String way) {
		this.way = way;
	}
}
