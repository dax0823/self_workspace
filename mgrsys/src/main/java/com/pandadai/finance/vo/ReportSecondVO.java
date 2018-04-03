package com.pandadai.finance.vo;

public class ReportSecondVO {
//	select bif.id, bif.borrow_name, FROM_UNIXTIME(add_time) time, bif.borrow_money, bif.borrow_interest_rate
//	, bif.borrow_interest, bif.borrow_interest * 0.1 fee, bif.borrow_interest - bif.borrow_interest * 0.1 final_money
	private String id;
	private String borrowName;
	private String time;
	private int money;
	private double interestRate;
	private double interest;
	private double fee;
	private double finalMoney;
	
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public double getFinalMoney() {
		return finalMoney;
	}
	public void setFinalMoney(double finalMoney) {
		this.finalMoney = finalMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}