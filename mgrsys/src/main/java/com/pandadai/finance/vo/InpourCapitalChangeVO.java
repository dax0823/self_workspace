package com.pandadai.finance.vo;

/***
 * 
 * @author 仵作
 *
 */
public class InpourCapitalChangeVO {
//	select FROM_UNIXTIME(mml.add_time), mm.id, mm.user_name, mmi.real_name
//	, mml.type, mml.affect_money, mml.account_money, mml.back_money, mml.collect_money, mml.freeze_money, mml.target_uid, mml.target_uname, mml.info
	private String userId;
	private String userName;
	private String realName;
	private String time;
	private String type;
	private String typeName;
	private float affectMoney;
	private float accountMoney;	//account_money + back_money
	private float collectMoney;
	private float freezeMoney;
	private String targetUid;
	private String targetUname;
	private String info;
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return the targetUname
	 */
	public String getTargetUname() {
		return targetUname;
	}
	/**
	 * @param targetUname the targetUname to set
	 */
	public void setTargetUname(String targetUname) {
		this.targetUname = targetUname;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return the affectMoney
	 */
	public float getAffectMoney() {
		return affectMoney;
	}
	/**
	 * @param affectMoney the affectMoney to set
	 */
	public void setAffectMoney(float affectMoney) {
		this.affectMoney = affectMoney;
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
	/**
	 * @return the targetUid
	 */
	public String getTargetUid() {
		return targetUid;
	}
	/**
	 * @param targetUid the targetUid to set
	 */
	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}
}
