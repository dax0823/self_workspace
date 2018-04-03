/** 
 * Project Name: steak 
 * File Name: IGoods.java 
 * Package Name: com.panda.steak.goods.service 
 * Date: 2016年9月5日上午11:20:57 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.service;  

import java.util.List;

import com.panda.steak.generator.model.Goods;
import com.panda.steak.utils.vo.Page;
import com.panda.steak.utils.vo.model.GoodsExt;

/** 
 * ClassName: IGoods
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月5日 上午11:20:57
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public interface IGoods {
	
	/**
	 * 
	 * queryListByConditions: 查询商品信息
	 * 
	 * @author dinghui 
	 * @param page
	 * @return 
	 * @since JDK 1.8
	 */
	public List<GoodsExt> queryList(Page page);
	
	/**
	 * 
	 * insertSelective: 保存一条商品信息
	 * 
	 * @author dinghui 
	 * @param record
	 * @return 
	 * @since JDK 1.8
	 */
	public int insertSelective(Goods goods);
	
	/**
	 * 
	 * deleteByPrimaryKey: 删除一条商品信息
	 * 
	 * @author dinghui 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	public int deleteByPrimaryKey(Integer id);
	
	/**
	 * 
	 * updateByPrimaryKeySelective: 更新一条商品信息
	 * 
	 * @author dinghui 
	 * @param goods
	 * @return 
	 * @since JDK 1.8
	 */
	public int updateByPrimaryKeySelective(Goods goods);
}
