package com.panda.test.pay.lianlian.vo;

/**
 * 实际支付请求参数对象
 * @author 仵作
 *
 */
public class ParamPayVO {
	private String user_id;
	private String acct_name; // 真实姓名
	private String card_no; // 银行卡号
	private String id_type; // 证件类型
	private String id_no; // 证件号码
	private String money_order; // 支付金额
	private String version; // 接口版本号
	private String oid_partner; // 商户编号
	private String app_request; // 请求应用标识 1：Android 2：ios 3：WAP
	private String no_order; // 订单编号
	private String dt_order; // 订单时间
	private String name_goods; // 商品名称
	private String notify_url; // 异步调用链接
//	private RiskItem risk_item; // 风险参数
	private ParamRiskItemVO risk_item; // 风险参数
	private String sign;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getMoney_order() {
		return money_order;
	}
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getApp_request() {
		return app_request;
	}
	public void setApp_request(String app_request) {
		this.app_request = app_request;
	}
	public String getNo_order() {
		return no_order;
	}
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	public String getDt_order() {
		return dt_order;
	}
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	public String getName_goods() {
		return name_goods;
	}
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public ParamRiskItemVO getRisk_item() {
		return risk_item;
	}
	public void setRisk_item(ParamRiskItemVO risk_item) {
		this.risk_item = risk_item;
	}
}
