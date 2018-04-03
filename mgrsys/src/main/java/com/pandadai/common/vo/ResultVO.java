package com.pandadai.common.vo;

/**
 * 
 * Controller 返回前台页面的结果集对象
 * @author 仵作
 *
 */
public class ResultVO {
	private int code;
	private String msg;
	private Object obj;
	//此属性专为 jquery.datatable 而设………………
	private Object data;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
