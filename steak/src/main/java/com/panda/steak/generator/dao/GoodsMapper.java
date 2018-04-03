package com.panda.steak.generator.dao;

import java.util.List;

import com.panda.steak.generator.model.Goods;
import com.panda.steak.utils.vo.Page;
import com.panda.steak.utils.vo.model.GoodsExt;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    
    /**
     * 
     * queryList: 查询商品信息
     * 
     * @author dinghui 
     * @param page
     * @return 
     * @since JDK 1.8
     */
    List<GoodsExt> queryList(Page page);
}