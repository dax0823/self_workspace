package com.panda.steak.goods.service;

import java.util.List;

import com.panda.steak.generator.model.GoodsTransfer;

/**
 * 
 * ClassName: IGoodsTransfer
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * date: 2016年9月9日 下午2:51:30
 * 
 * @author dinghui
 * @version 
 * @since JDK 1.8
 */
public interface IGoodsTransfer {
	
	/**
	 * 
	 * getInfoById: 查询一条商品的流转信息
	 * 
	 * @author dinghui 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	public List<GoodsTransfer> queryListByGoodsId(int goodsId);
}
