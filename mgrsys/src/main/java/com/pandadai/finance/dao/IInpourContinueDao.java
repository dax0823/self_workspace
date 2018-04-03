/**
 * 
 */
package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourContinueVO;

/**
 * @author 仵作
 * 2014-8-31 上午10:29:02
 */
public interface IInpourContinueDao {

	/**
	 * 获取续投奖励记录信息
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourContinueVO> queryContinueList(String startDate, String endDate) throws Exception;
}
