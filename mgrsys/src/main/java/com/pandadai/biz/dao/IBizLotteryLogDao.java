/**
 * 
 */
package com.pandadai.biz.dao;

import java.util.List;

import com.pandadai.biz.vo.BizLotteryLogVO;

/**
 * @author 仵作
 * 2014-12-3 下午1:43:26
 */
public interface IBizLotteryLogDao {

	/**
	 * 获取积分抽奖记录
	 * @return
	 */
	List<BizLotteryLogVO> queryIntegralLotteryLog(String userName, String realName, String name, String startDate, String endDate) throws Exception;
}
