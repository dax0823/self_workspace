/** 
 * Project Name: steak 
 * File Name: IBizType.java 
 * Package Name: com.panda.steak.goods.service 
 * Date: 2016年9月1日上午11:59:39 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.service;  

import java.util.List;

import com.panda.steak.generator.model.BizType;
import com.panda.steak.utils.vo.model.BizTypeExt;
import com.panda.steak.utils.vo.model.BizTypeExtTree;

/** 
 * ClassName: IBizType
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月1日 上午11:59:39
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public interface IBizType {
	
	/**
	 * 
	 * getInfoByParentId: 根据上级id获取本级商品类别信息
	 * 
	 * @author dinghui 
	 * @param parentId
	 * @return 
	 * @since JDK 1.8
	 */
	public List<BizType> queryListByParentId(int parentId);
	
	public List<BizTypeExt> queryListByParentIdExt(int parentId);
	
	/**
	 * 
	 * addBizTypeInfo: 插入一条类别数据
	 * 
	 * @author dinghui 
	 * @param bizType
	 * @return 
	 * @since JDK 1.8
	 */
	public int insertSelective(BizType bizType);
	
	/**
	 * 
	 * deleteById: 删除一条类别数据
	 * 
	 * @author dinghui 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	public int deleteByPrimaryKey(int id);
	
	/**
	 * 
	 * updateByPrimaryKeySelective: 更新一条类别数据
	 * 
	 * @author dinghui 
	 * @param bizType
	 * @return 
	 * @since JDK 1.8
	 */
	public int updateByPrimaryKeySelective(BizType bizType);
	
	/**
	 * 
	 * queryListAllInTree: 将全部信息以树形结构查出
	 * 
	 * @author dinghui 
	 * @return 
	 * @since JDK 1.8
	 */
	public List<BizTypeExtTree> queryListAllInTree();
}
