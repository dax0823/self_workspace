package com.pandadai.finance.vo;

public class InvestCustomerUncollectedDetailVO {
//	select mm.id, mm.user_name, bif.id borrow_id, bif.borrow_name, bif.borrow_money, bif.borrow_duration, biv.investor_capital, FROM_UNIXTIME(biv.add_time) invest_time
	private String borrowId;
	private String borrowName;
	private int borrowMoney;
	private int duration;
	private int capital;
	private String investTime;
	
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public int getBorrowMoney() {
		return borrowMoney;
	}
	public void setBorrowMoney(int borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getCapital() {
		return capital;
	}
	public void setCapital(int capital) {
		this.capital = capital;
	}
	public String getInvestTime() {
		return investTime;
	}
	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	
}
