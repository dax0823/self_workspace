/** 
 * Project Name: steak 
 * File Name: BizTypeImpl.java 
 * Package Name: com.panda.steak.goods.service.impl 
 * Date: 2016年9月1日下午12:01:23 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.service.impl;  

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.panda.steak.generator.dao.BizTypeMapper;
import com.panda.steak.generator.model.BizType;
import com.panda.steak.goods.service.IBizType;
import com.panda.steak.utils.vo.model.BizTypeExt;
import com.panda.steak.utils.vo.model.BizTypeExtTree;

/** 
 * ClassName: BizTypeImpl
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月1日 下午12:01:23
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Service("BizType")
public class BizTypeImpl implements IBizType {

	@Resource
	private BizTypeMapper bizTypeMapper;

	@Override
	public List<BizType> queryListByParentId(int parentId) {
		return bizTypeMapper.queryListByParentId(parentId);
	}

	@Override
	public List<BizTypeExt> queryListByParentIdExt(int parentId) {
		return bizTypeMapper.queryListByParentIdExt(parentId);
	}

	@Override
	public int insertSelective(BizType bizType) {
//		return bizTypeMapper.insert(bizType);
		return bizTypeMapper.insertSelective(bizType);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return bizTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BizType bizType) {
		return bizTypeMapper.updateByPrimaryKeySelective(bizType);
	}

	@Override
	public List<BizTypeExtTree> queryListAllInTree() {
		List<BizTypeExtTree> lst = new ArrayList<BizTypeExtTree>();
		
		//迭代类别树
		
		return null;
	}
}
