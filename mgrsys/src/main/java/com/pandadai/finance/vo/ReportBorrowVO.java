package com.pandadai.finance.vo;

public class ReportBorrowVO {
//	select bif.id, bif.borrow_name, FROM_UNIXTIME(add_time) time, bif.borrow_money, bif.borrow_duration, 
//	bif.borrow_interest_rate, bif.reward_num, bif.borrow_interest, bif.borrow_fee, bif.borrow_interest * 0.1
	private String id;
	private String name;
	private String time;
	private int money;
	private int duration;
	private double interestRate;
	private double rewardRate;
	private int interest;
	private int borrowFee;
	private int fee;
	private int income;
	private int spreads;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getRewardRate() {
		return rewardRate;
	}
	public void setRewardRate(double rewardRate) {
		this.rewardRate = rewardRate;
	}
	public int getInterest() {
		return interest;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	public int getBorrowFee() {
		return borrowFee;
	}
	public void setBorrowFee(int borrowFee) {
		this.borrowFee = borrowFee;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public int getSpreads() {
		return spreads;
	}
	public void setSpreads(int spreads) {
		this.spreads = spreads;
	}
}
