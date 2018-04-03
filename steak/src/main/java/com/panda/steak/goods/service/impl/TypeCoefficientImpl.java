/** 
 * Project Name: steak 
 * File Name: TypeCoefficientImpl.java 
 * Package Name: com.panda.steak.goods.service.impl 
 * Date: 2016年9月4日下午5:03:25 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.service.impl;  

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.panda.steak.generator.dao.TypeCoefficientMapper;
import com.panda.steak.generator.model.TypeCoefficient;
import com.panda.steak.goods.service.ITypeCoefficient;

/** 
 * ClassName: TypeCoefficientImpl
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月4日 下午5:03:25
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Service("TypeCoefficient")
public class TypeCoefficientImpl implements ITypeCoefficient {

	@Resource
	private TypeCoefficientMapper typeCoefficientMapper;
	
	@Override
	public int updateByPrimaryKey(TypeCoefficient typeCoefficient) {
		return typeCoefficientMapper.updateByPrimaryKeySelective(typeCoefficient);
	}

	@Override
	public int insertSelective(TypeCoefficient typeCoefficient) {
		return typeCoefficientMapper.insertSelective(typeCoefficient);
	}

}
