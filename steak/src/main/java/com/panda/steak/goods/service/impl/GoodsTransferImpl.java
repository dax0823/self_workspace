package com.panda.steak.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.panda.steak.generator.dao.GoodsTransferMapper;
import com.panda.steak.generator.model.GoodsTransfer;
import com.panda.steak.goods.service.IGoodsTransfer;

@Service("goodsTransfer")
public class GoodsTransferImpl implements IGoodsTransfer {

	@Resource
	private GoodsTransferMapper goodsTransferMapper;

	@Override
	public List<GoodsTransfer> queryListByGoodsId(int goodsId) {
		return goodsTransferMapper.queryListByGoodsId(goodsId);
	}

}
