/**
 * 
 */
package com.pandadai.adjust.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pandadai.adjust.dao.impl.AdjustIntegralDaoImpl;
import com.pandadai.adjust.vo.AdjustIntegralCustomerVO;
import com.pandadai.adjust.vo.AdjustIntegralLogVO;

/**
 * @author 仵作
 * 2014-10-9 下午7:19:55
 */
public class AdjustIntegralHandler {
	/**
	 * 配置注入
	 */
	private AdjustIntegralDaoImpl adjIntegralImpl;
	private String integralLogDescribe;
	
//	private String integralLogIPFilterExcept220_231_48_;
//	220.231.48.,127.0.0.1,localhost
	private String integralLogIPFilter;

	/**
	 * @param integralLogIPFilter the integralLogIPFilter to set
	 */
	public void setIntegralLogIPFilter(String integralLogIPFilter) {
		this.integralLogIPFilter = integralLogIPFilter;
	}

	/**
	 * @param integralLogDescribe the integralLogDescribe to set
	 */
	public void setIntegralLogDescribe(String integralLogDescribe) {
		this.integralLogDescribe = integralLogDescribe;
	}

	/**
	 * @param adjIntegralImpl the adjIntegralImpl to set
	 */
	public void setAdjIntegralImpl(AdjustIntegralDaoImpl adjIntegralImpl) {
		this.adjIntegralImpl = adjIntegralImpl;
	}

	/**
	 * 获取客户积分列表
	 * @return
	 * @throws Exception
	 */
	public List<AdjustIntegralCustomerVO> queryIntegralCustomer(String startDate, String endDate, String userName) throws Exception {
//		return adjIntegralImpl.queryIntegralCustomer(userName);
		return adjIntegralImpl.queryIntegralCustomer(startDate, endDate, userName);
	}
	
	/**
	 * 获取单个客户积分日志
	 * @return
	 * @throws Exception
	 */
	public List<AdjustIntegralLogVO> queryIntegralLog(String userId) throws Exception {
		return adjIntegralImpl.queryIntegralLog(userId); 
	}
	
	/**
	 * 更新投标积分数据
	 * @param userId
	 * @param adjNum
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public boolean updateIntegral(String userId, int adjNum, String description, String ip) throws Exception {
		
//		服务器端总是出现奇怪的 ip 访问
//		此处屏蔽掉除固定 ip 外的调用
		boolean flag = false;
		String[] ips = integralLogIPFilter.split(",");
		for (int i=0; i<ips.length; i++) {
			if (ip.startsWith(ips[i])) {
				flag = true;
				break;
			}
		}
		if(!flag) return false;
		
		//1. 先修改当前积分
		try {
			adjIntegralImpl.updateIntegralbyUserId(userId, adjNum);
		} catch(Exception e) {
			return false;
		}
		
		//2. 然后查询修改后积分值
		AdjustIntegralCustomerVO vo = null;
		try {
			vo = adjIntegralImpl.getIntegralInfoByUserId(userId);
		} catch(Exception e) {
			return false;
		}
		
		//3. 最后增加一条新的积分日志信息
		if (vo != null) {
			try {
				String info = description;
				if (StringUtils.isBlank(info)) {
					info = integralLogDescribe;
				}
				
				adjIntegralImpl.insertIntegralLogByUserId(userId, adjNum, vo.getActiveIntegral(), vo.getIntegral(), info, ip);
			} catch(Exception e) {
				return false;
			}
		} else return false;
		
		return true;
	}
}
