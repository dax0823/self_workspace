/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourReviseVO;


/**
 * @author 仵作
 * 2014-8-30 下午11:51:34
 */
public interface IInpourReviseDao {
	/**
	 * 将管理员手动修改记录取出（默认查询最近一个月内数据）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourReviseVO> queryReviseList(String startDate, String endDate) throws Exception;
}
