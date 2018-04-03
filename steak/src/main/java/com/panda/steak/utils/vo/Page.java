/** 
 * Project Name: steak 
 * File Name: Page.java 
 * Package Name: com.panda.steak.utils.vo 
 * Date: 2016年9月5日上午11:08:38 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.utils.vo;  
/** 
 * ClassName: Page
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月5日 上午11:08:38
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class Page {
	private int index = 1;	//当前页数
	private int size = 10;	//每页显示条数
	private int total	= 0;	//总记录数

	private int start	= 0;	//本次查询起始条数
	
	public int getStart() {
		return size * index;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
