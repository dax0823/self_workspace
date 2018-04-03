package com.pandadai.common.vo;

public class AdminUserVO {
//	select user_name userName, u_group_id uGroupId, real_name realName, last_log_time lastLogTime, last_log_ip lastLogIp, is_kf isKf, qq, phone
//	from lzh_ausers
//	where user_name = userName and user_pass = pwd and user_word = userWord
	private int id;
	private String username;
//	private String pwd;
//	private String userword;
	private int uGroupId;
	private String realName;
	private long lastLogTime;
	private String lastLogIp;
	private int isKf;
	private String qq;
	
	public int getuGroupId() {
		return uGroupId;
	}
	public void setuGroupId(int uGroupId) {
		this.uGroupId = uGroupId;
	}
	public long getLastLogTime() {
		return lastLogTime;
	}
	public void setLastLogTime(long lastLogTime) {
		this.lastLogTime = lastLogTime;
	}
	public String getLastLogIp() {
		return lastLogIp;
	}
	public void setLastLogIp(String lastLogIp) {
		this.lastLogIp = lastLogIp;
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
	private String phone;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
//	public String getPwd() {
//		return pwd;
//	}
//	public void setPwd(String pwd) {
//		this.pwd = pwd;
//	}
//	public String getUserword() {
//		return userword;
//	}
//	public void setUserword(String userword) {
//		this.userword = userword;
//	}
	
}
