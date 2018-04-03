package com.pandadai.finance.vo;

/***
 * 财务总览对象
 * @author 仵作
 *
 */
public class InpourHomeVO {
	private String during;	//时间范围
	private float baofoo;	//宝付
	private float easypay;	//易付
	private float off;	//线下
	private float total;	//总额
//	private String params;	//页面 datatable 点击事件所需参数
	
	public InpourHomeVO() {
		this.during = "当天";
		this.baofoo = 0;
		this.easypay = 0;
		this.off = 0;
		this.total = 0;
//		this.params = "{type: 0}";
	}
	
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

//	public String getParams() {
//		return params;
//	}
//
//	public void setParams(String params) {
//		this.params = params;
//	}

	public String getDuring() {
		return during;
	}
	public void setDuring(String during) {
		this.during = during;
	}

	public float getBaofoo() {
		return baofoo;
	}

	public void setBaofoo(float baofoo) {
		this.baofoo = baofoo;
	}

	public float getEasypay() {
		return easypay;
	}

	public void setEasypay(float easypay) {
		this.easypay = easypay;
	}

	public float getOff() {
		return off;
	}

	public void setOff(float off) {
		this.off = off;
	}
	
}
