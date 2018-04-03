package com.panda.steak.generator.dao;

import java.util.List;

import com.panda.steak.generator.model.BizType;
import com.panda.steak.utils.vo.model.BizTypeExt;

public interface BizTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BizType record);

    int insertSelective(BizType record);

    BizType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizType record);

    int updateByPrimaryKey(BizType record);

    /**
     * 
     * queryListByParentId: 根据上级id获取本级商品类别信息
     * 
     * @author dinghui 
     * @param parentId
     * @return 
     * @since JDK 1.8
     */
    List<BizType> queryListByParentId(int parentId);

    List<BizTypeExt> queryListByParentIdExt(int parentId);
}