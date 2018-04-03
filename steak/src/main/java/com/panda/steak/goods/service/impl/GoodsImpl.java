/** 
 * Project Name: steak 
 * File Name: IGoodsImpl.java 
 * Package Name: com.panda.steak.goods.service.impl 
 * Date: 2016年9月5日上午11:23:50 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.goods.service.impl;  

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.panda.steak.generator.dao.GoodsMapper;
import com.panda.steak.generator.dao.GoodsTransferMapper;
import com.panda.steak.generator.model.Goods;
import com.panda.steak.generator.model.GoodsTransfer;
import com.panda.steak.goods.service.IGoods;
import com.panda.steak.utils.vo.Page;
import com.panda.steak.utils.vo.model.GoodsExt;

/** 
 * ClassName: IGoodsImpl
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月5日 上午11:23:50
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Service("Goods")
public class GoodsImpl implements IGoods {

	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private GoodsTransferMapper goodsTransferMapper;

	@Override
	public List<GoodsExt> queryList(Page page) {
		return goodsMapper.queryList(page);
	}

	@Override
	public int insertSelective(Goods goods) {
		int n = goodsMapper.insertSelective(goods);

		//创建数据同时，新增一条流转记录
		if (n > 0) {
			GoodsTransfer goodsTransfer = new GoodsTransfer();
			goodsTransfer.setGoodsId(goods.getId());
//			操作类型：
//			0：其他；
//			1：入库；
//			2：转移；
//			3：出售；
//			4：退货；
			goodsTransfer.setType(1);
			goodsTransfer.setSource("无");
			goodsTransfer.setTarget(goods.getAddress());
			n = goodsTransferMapper.insertSelective(goodsTransfer);
		}
		
		return n;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return goodsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Goods goods) {
		int n = 0;
		// 更新数据同时，新增一条流转记录
		// if (n > 0) {
			Goods lastGoods = new Goods();
			lastGoods = goodsMapper.selectByPrimaryKey(goods.getId());
	
			// 当“Address”有修改时
			if (!lastGoods.getAddress().equalsIgnoreCase(goods.getAddress())) {
				GoodsTransfer goodsTransfer = new GoodsTransfer();
				goodsTransfer.setGoodsId(goods.getId());
				// 操作类型：
				// 0：其他；
				// 1：入库；
				// 2：转移；
				// 3：出库；
				// 4：退还；
				if (lastGoods.getStock() < goods.getStock()) {
					if (lastGoods.getSum() < goods.getSum())
						goodsTransfer.setType(1);
					else if (lastGoods.getSum() == goods.getSum())
						goodsTransfer.setType(4);
					else goodsTransfer.setType(0);
				}
				else if (lastGoods.getStock() > goods.getStock())
					goodsTransfer.setType(3);
				else
					goodsTransfer.setType(2);
	
				goodsTransfer.setSource(lastGoods.getAddress());
				goodsTransfer.setTarget(goods.getAddress());
				n = goodsTransferMapper.insertSelective(goodsTransfer);
			}
		// }

		n = goodsMapper.updateByPrimaryKeySelective(goods);

		return n;
	}

}
