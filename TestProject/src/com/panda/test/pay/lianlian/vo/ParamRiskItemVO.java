package com.panda.test.pay.lianlian.vo;

/**
 * 风险参数（调试阶段可不传）
 * @author 仵作
 *
 */
public class ParamRiskItemVO {
	private String frms_ware_category;	//商品类目
	private String user_info_mercht_userno;	//userId
	private String user_info_bind_phone;	//手机号
	private String user_info_dt_register;	//注册时间
	private String user_info_full_name;	//真实姓名
	private String user_info_id_no;	//证件号码
	private String user_info_identify_type;	//是否实名认证
	private String user_info_identify_state;	//实名认证方式
	
	public String getFrms_ware_category() {
		return frms_ware_category;
	}
	public void setFrms_ware_category(String frms_ware_category) {
		this.frms_ware_category = frms_ware_category;
	}
	public String getUser_info_mercht_userno() {
		return user_info_mercht_userno;
	}
	public void setUser_info_mercht_userno(String user_info_mercht_userno) {
		this.user_info_mercht_userno = user_info_mercht_userno;
	}
	public String getUser_info_bind_phone() {
		return user_info_bind_phone;
	}
	public void setUser_info_bind_phone(String user_info_bind_phone) {
		this.user_info_bind_phone = user_info_bind_phone;
	}
	public String getUser_info_dt_register() {
		return user_info_dt_register;
	}
	public void setUser_info_dt_register(String user_info_dt_register) {
		this.user_info_dt_register = user_info_dt_register;
	}
	public String getUser_info_full_name() {
		return user_info_full_name;
	}
	public void setUser_info_full_name(String user_info_full_name) {
		this.user_info_full_name = user_info_full_name;
	}
	public String getUser_info_id_no() {
		return user_info_id_no;
	}
	public void setUser_info_id_no(String user_info_id_no) {
		this.user_info_id_no = user_info_id_no;
	}
	public String getUser_info_identify_type() {
		return user_info_identify_type;
	}
	public void setUser_info_identify_type(String user_info_identify_type) {
		this.user_info_identify_type = user_info_identify_type;
	}
	public String getUser_info_identify_state() {
		return user_info_identify_state;
	}
	public void setUser_info_identify_state(String user_info_identify_state) {
		this.user_info_identify_state = user_info_identify_state;
	}
}
