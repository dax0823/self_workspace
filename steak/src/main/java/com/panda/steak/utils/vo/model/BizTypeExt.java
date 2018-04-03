/** 
 * Project Name: steak 
 * File Name: BizTypeParent.java 
 * Package Name: com.panda.steak.utils.vo.model 
 * Date: 2016年9月3日下午1:59:07 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.utils.vo.model;  

import com.panda.steak.generator.model.BizType;

/** 
 * ClassName: BizTypeExt
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月3日 下午1:59:07
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class BizTypeExt extends BizType {

    private String coefficientId;
    private String coefficient;
    
	public String getCoefficientId() {
		return coefficientId;
	}
	public void setCoefficientId(String coefficientId) {
		this.coefficientId = coefficientId;
	}
	public String getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}
}
