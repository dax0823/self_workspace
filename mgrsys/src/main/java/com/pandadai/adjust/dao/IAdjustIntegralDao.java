/**
 * 
 */
package com.pandadai.adjust.dao;

import java.util.List;

import com.pandadai.adjust.vo.AdjustIntegralCustomerVO;
import com.pandadai.adjust.vo.AdjustIntegralLogVO;

/**
 * @author 仵作
 * 2014-10-9 下午6:57:07
 */
public interface IAdjustIntegralDao {
	
	/**
	 * 获取客户积分列表
	 * @return
	 * @throws Exception
	 */
	List<AdjustIntegralCustomerVO> queryIntegralCustomer(String userName) throws Exception;

	List<AdjustIntegralCustomerVO> queryIntegralCustomer(String startDate, String endDate, String userName) throws Exception;
	
	/**
	 * 获取单个客户积分日志
	 * @return
	 * @throws Exception
	 */
	List<AdjustIntegralLogVO> queryIntegralLog(String userId) throws Exception;
	
	/**
	 * 更新用户的投标积分
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	void updateIntegralbyUserId(String userId, int adjNum) throws Exception;
	
	/**
	 * 获取刚刚修改后的积分信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	AdjustIntegralCustomerVO getIntegralInfoByUserId(String userId) throws Exception;
	
	/**
	 * 增加一条新的积分修改记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	void insertIntegralLogByUserId(String userId, int adjNum, int activeIntegral, int integral, String describe, String ip) throws Exception;
}
