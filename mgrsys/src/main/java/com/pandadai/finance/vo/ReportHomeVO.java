package com.pandadai.finance.vo;

public class ReportHomeVO {
	private String month;
	private int numbers;
	private int volume;
	private int capitalBack;
	private double interestBack;
	private double fee;
	private double profit;
	private int capitalUncollected;
	private double interestUncollected;
	private double feeUncollected;
	private double sumUncollected;
	private double averageProfit;
	private int registerNew;
	private int registerSum;
	
	public int getRegisterNew() {
		return registerNew;
	}
	public void setRegisterNew(int registerNew) {
		this.registerNew = registerNew;
	}
	public int getRegisterSum() {
		return registerSum;
	}
	public void setRegisterSum(int registerSum) {
		this.registerSum = registerSum;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getCapitalBack() {
		return capitalBack;
	}
	public void setCapitalBack(int capitalBack) {
		this.capitalBack = capitalBack;
	}
	public double getInterestBack() {
		return interestBack;
	}
	public void setInterestBack(double interestBack) {
		this.interestBack = interestBack;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public int getCapitalUncollected() {
		return capitalUncollected;
	}
	public void setCapitalUncollected(int capitalUncollected) {
		this.capitalUncollected = capitalUncollected;
	}
	public double getInterestUncollected() {
		return interestUncollected;
	}
	public void setInterestUncollected(double interestUncollected) {
		this.interestUncollected = interestUncollected;
	}
	public double getFeeUncollected() {
		return feeUncollected;
	}
	public void setFeeUncollected(double feeUncollected) {
		this.feeUncollected = feeUncollected;
	}
	public double getSumUncollected() {
		return sumUncollected;
	}
	public void setSumUncollected(double sumUncollected) {
		this.sumUncollected = sumUncollected;
	}
	public double getAverageProfit() {
		return averageProfit;
	}
	public void setAverageProfit(double averageProfit) {
		this.averageProfit = averageProfit;
	}
}
