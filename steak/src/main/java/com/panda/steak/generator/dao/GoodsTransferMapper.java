package com.panda.steak.generator.dao;

import java.util.List;

import com.panda.steak.generator.model.GoodsTransfer;

public interface GoodsTransferMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsTransfer record);

    int insertSelective(GoodsTransfer record);

    GoodsTransfer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsTransfer record);

    int updateByPrimaryKey(GoodsTransfer record);
    
    /**
     * 
     * queryListByGoodsId: 根据商品id查询流转记录
     * 
     * @author dinghui 
     * @param goodsId
     * @return 
     * @since JDK 1.8
     */
    List<GoodsTransfer> queryListByGoodsId(Integer goodsId);
}