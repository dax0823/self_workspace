package com.pandadai.bean;

/**
 * 
 * 管理员账户对象实体
 * @author 丁辉
 *
 */
public class AdminUserBean {
	private int id;
	private String userName;   
	private String userPass;
	private int uGroupId;
	private String realName;
	private int lastLogTime;
	private String lastLogIp;
	private int isBan;
	private int areaId ;
	private String areaName;
	private int isKf ;
	private String qq;
	private String phone;
	private String userWord;
	
	public AdminUserBean(String userName, String userPass, String userWord) {
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.userWord = userWord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public int getuGroupId() {
		return uGroupId;
	}
	public void setuGroupId(int uGroupId) {
		this.uGroupId = uGroupId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getLastLogTime() {
		return lastLogTime;
	}
	public void setLastLogTime(int lastLogTime) {
		this.lastLogTime = lastLogTime;
	}
	public String getLastLogIp() {
		return lastLogIp;
	}
	public void setLastLogIp(String lastLogIp) {
		this.lastLogIp = lastLogIp;
	}
	public int getIsBan() {
		return isBan;
	}
	public void setIsBan(int isBan) {
		this.isBan = isBan;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getIsKf() {
		return isKf;
	}
	public void setIsKf(int isKf) {
		this.isKf = isKf;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserWord() {
		return userWord;
	}
	public void setUserWord(String userWord) {
		this.userWord = userWord;
	}
}
