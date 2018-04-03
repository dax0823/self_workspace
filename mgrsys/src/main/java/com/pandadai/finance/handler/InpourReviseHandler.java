/**
 * 
 */
package com.pandadai.finance.handler;

import java.util.List;

import com.pandadai.finance.dao.impl.InpourReviseDaoImpl;
import com.pandadai.finance.vo.InpourReviseVO;

/**
 * @author 仵作
 * 2014-8-31 上午12:00:25
 */
public class InpourReviseHandler {
	/**
	 * 配置注入
	 */
	private InpourReviseDaoImpl inpourReviseImpl;

	/**
	 * @param inpourReviseImpl the inpourReviseImpl to set
	 */
	public void setInpourReviseImpl(InpourReviseDaoImpl inpourReviseImpl) {
		this.inpourReviseImpl = inpourReviseImpl;
	}


	/**
	 * 将管理员手动修改记录取出（默认查询最近一个月内数据）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourReviseVO> queryReviseList(String startDate, String endDate) throws Exception {
		return inpourReviseImpl.queryReviseList(startDate, endDate);
	} 
}
