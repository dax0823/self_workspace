package com.pandadai.finance.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pandadai.finance.dao.impl.InpourDetailDaoImpl;
import com.pandadai.finance.vo.InpourDetailVO;

/***
 * 
 * @author 仵作
 * 2014-8-29 上午10:32:26
 */
public class InpourDetailHandler {
	/**
	 * 配置注入
	 */
	private InpourDetailDaoImpl inpourDetailImpl;

	public void setInpourDetailImpl(InpourDetailDaoImpl inpourDetailImpl) {
		this.inpourDetailImpl = inpourDetailImpl;
	}
	
	/**
	 * 获取某个时间段内的所有客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourDetailVO> queryInpourAll(String startDate, String endDate) throws Exception {
		return inpourDetailImpl.queryInpourAll(startDate, endDate);
	} 
	
	/**
	 * 获取某个时间段内的 baofoo 客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourDetailVO> queryInpourByBaofoo(String startDate, String endDate) throws Exception {
		return inpourDetailImpl.queryInpourByBaofoo(startDate, endDate);
	} 
	
	/**
	 * 获取某个时间段内的 easypay 客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourDetailVO> queryInpourByEasypay(String startDate, String endDate) throws Exception {
		return inpourDetailImpl.queryInpourByEasypay(startDate, endDate);
	} 
	
	/**
	 * 获取某个时间段内的线下客户充值记录，以天为单位（默认获取最近 7 天）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourDetailVO> queryInpourByOff(String startDate, String endDate) throws Exception {
		return inpourDetailImpl.queryInpourByOff(startDate, endDate);
	}
	
	/**
	 * 获取某个时间段内的某个客户的单笔充值记录
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InpourDetailVO> queryInpourCustomer(String id, String startDate, String endDate, String way) throws Exception {
		//当客户端传入的值不合法时，默认设置为 off
		if (StringUtils.isNotBlank(way) && !way.equalsIgnoreCase("baofoo") && !way.equalsIgnoreCase("easypay")) {
			way = "off";
		}
		return inpourDetailImpl.queryInpourCustomer(id, startDate, endDate, way);
	}
	
	/**
	 * 获取某天某个客户的单笔充值记录
	 * @param id
	 * @param inpourTime
	 * @param way
	 * @return
	 * @throws Exception
	 */
	public List<InpourDetailVO> queryInpourCustomer(String id, String inpourTime, String way) throws Exception {
		if (StringUtils.isNotBlank(way) && !way.equalsIgnoreCase("baofoo") && !way.equalsIgnoreCase("easypay")) {
			way = "off";
		}
		return inpourDetailImpl.queryInpourCustomer(id, inpourTime, way);
	}
}
