package com.pandadai.finance.vo;

/**
 * 提现明细输出对象
 * @author 仵作
 * 2014-12-11 下午4:16:12
 */
public class InpourWithdrawVO {
//	select mw.id withdraw_id, FROM_UNIXTIME(mw.add_time) withdraw_time, mw.withdraw_money, mw.withdraw_fee, mw.success_money
//	, case mw.withdraw_status when '0' then '待审核' when '1' then '处理中' when '2' then '已提现' when '3' then '未通过' else mw.withdraw_status end withdraw_status
//	, mm.id user_id, mm.user_name, mmi.real_name, mb.bank_num, mb.bank_address, mb.bank_name, mb.bank_province, mb.bank_city
//	提现 id	申请提现时间	提现金额（单位：元）	手续费（单位：元）	到账（单位：元）	提现状态	客户id	客户名称	收款人姓名	银行账号	开户行	银行名称	开户行省名	开户行市名

	private String withdrawId;
	private String withdrawTime;
	private float withdrawMoney;
	private float withdrawFee;
	private float successMoney;
	private String withdrawStatus;
	private String userId;
	private String userName;
	private String realName;
	private String bankNum;
	private String bankAddr;
	private String bankName;
	private String bankProv;
	private String bankCity;
	

	/**
	 * @return the successMoney
	 */
	public float getSuccessMoney() {
		return successMoney;
	}
	/**
	 * @param successMoney the successMoney to set
	 */
	public void setSuccessMoney(float successMoney) {
		this.successMoney = successMoney;
	}
	/**
	 * @return the withdrawId
	 */
	public String getWithdrawId() {
		return withdrawId;
	}
	/**
	 * @param withdrawId the withdrawId to set
	 */
	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
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
	 * @return the withdrawStatus
	 */
	public String getWithdrawStatus() {
		return withdrawStatus;
	}
	/**
	 * @param withdrawStatus the withdrawStatus to set
	 */
	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getBankProv() {
		return bankProv;
	}
	public void setBankProv(String bankProv) {
		this.bankProv = bankProv;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getBankAddr() {
		return bankAddr;
	}
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public float getWithdrawMoney() {
		return withdrawMoney;
	}
	public void setWithdrawMoney(float withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}
	public float getWithdrawFee() {
		return withdrawFee;
	}
	public void setWithdrawFee(float withdrawFee) {
		this.withdrawFee = withdrawFee;
	}
}
