package com.panda.steak.generator.dao;

import com.panda.steak.generator.model.TypeCoefficient;

public interface TypeCoefficientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TypeCoefficient record);

    int insertSelective(TypeCoefficient record);

    TypeCoefficient selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TypeCoefficient record);

    int updateByPrimaryKey(TypeCoefficient record);
}