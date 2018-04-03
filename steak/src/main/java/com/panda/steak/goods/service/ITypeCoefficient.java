/** 
 * Project Name: steak 
 * File Name: ITypeCoefficient.java 
 * Package Name: com.panda.steak.goods.service 
 * Date: 2016年9月4日下午5:01:07 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.service;  

import com.panda.steak.generator.model.TypeCoefficient;

/** 
 * ClassName: ITypeCoefficient
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月4日 下午5:01:07
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public interface ITypeCoefficient {
	
	/**
	 * 
	 * insertSelective: 新增一条系数数据
	 * 
	 * @author dinghui 
	 * @param record
	 * @return 
	 * @since JDK 1.8
	 */
    public int insertSelective(TypeCoefficient typeCoefficient);
	
	/**
	 * updateByPrimaryKey: 更新一条数据的系数
	 * 
	 * @author dinghui 
	 * @param record
	 * @return 
	 * @since JDK 1.8
	 */
	public int updateByPrimaryKey(TypeCoefficient typeCoefficient);
}
