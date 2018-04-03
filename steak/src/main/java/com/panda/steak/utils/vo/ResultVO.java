/** 
 * Project Name: steak 
 * File Name: ResultVO.java 
 * Package Name: com.panda.steak.utils.vo 
 * Date: 2016年9月1日下午1:43:39 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.utils.vo;  
/** 
 * ClassName: ResultVO
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月1日 下午1:43:39
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class ResultVO {
	private int code;
	private String msg;
	private Object obj;
	private Page page;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
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
