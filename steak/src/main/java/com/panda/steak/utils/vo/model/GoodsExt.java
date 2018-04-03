/** 
 * Project Name: steak 
 * File Name: GoodsExt.java 
 * Package Name: com.panda.steak.utils.vo.model 
 * Date: 2016年9月5日上午10:35:34 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.utils.vo.model;  

import com.panda.steak.generator.model.Goods;

/** 
 * ClassName: GoodsExt
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月5日 上午10:35:34
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class GoodsExt extends Goods {
	
    private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
    
}
