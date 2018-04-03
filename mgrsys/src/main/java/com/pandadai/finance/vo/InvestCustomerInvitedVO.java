package com.pandadai.finance.vo;

/**
 * 
 * @author 仵作
 * 用户推荐人及邀请好友
 *
 */
public class InvestCustomerInvitedVO {
//	select mm.id, mm.user_name, mm2.id recommend_id, mm2.user_name
	private String id;
	private String userName;
	private String invitedId;
	private String invitedName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getInvitedId() {
		return invitedId;
	}
	public void setInvitedId(String invitedId) {
		this.invitedId = invitedId;
	}
	public String getInvitedName() {
		return invitedName;
	}
	public void setInvitedName(String invitedName) {
		this.invitedName = invitedName;
	}
}
