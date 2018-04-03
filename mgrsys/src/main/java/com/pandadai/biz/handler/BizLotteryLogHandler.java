/**
 * 
 */
package com.pandadai.biz.handler;

import java.util.List;

import com.pandadai.biz.dao.impl.BizLotteryLogDaoImpl;
import com.pandadai.biz.vo.BizLotteryLogVO;

/**
 * @author 仵作
 * 2014-12-3 下午1:53:45
 */
public class BizLotteryLogHandler {
	/**
	 * 配置注入
	 */
	private BizLotteryLogDaoImpl bizLotteryLogImpl;

	/**
	 * @param bizLotteryLogImpl the bizLotteryLogImpl to set
	 */
	public void setBizLotteryLogImpl(BizLotteryLogDaoImpl bizLotteryLogImpl) {
		this.bizLotteryLogImpl = bizLotteryLogImpl;
	}

	/**
	 * 获取积分抽奖记录
	 * @param userName
	 * @param realName
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<BizLotteryLogVO> queryIntegralLotteryLog(String userName, String realName, String name, String startDate, String endDate) throws Exception {
		return bizLotteryLogImpl.queryIntegralLotteryLog(userName, realName, name, startDate, endDate);
	}
}
