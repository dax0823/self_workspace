package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourHomeInpourVO;
import com.pandadai.finance.vo.InpourHomeVO;

/**
 * 财务模块-总览页面
 * @author 仵作
 *
 */
public interface IInpourHomeDao {
	/**
	 * 查询当天的（成功）充值总额
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Float> sumCurrDate() throws Exception;

	/**
	 * 查询最近7天的（成功）充值总额
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Float> sumLast7Day() throws Exception;

	/**
	 * 查询最近一个月的（成功）充值总额
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Float> sumLastMonth() throws Exception;
	
	/**
	 * 查询最近一个月内每天的充值统计情况
	 * @return
	 * @throws Exception
	 */
	List<InpourHomeVO> sumEveryDay(String startDate, String endDate, String way)
			throws Exception;

	/**
	 * 查询最近一个年内每月的充值统计情况
	 * @return
	 * @throws Exception
	 */
	List<InpourHomeVO> sumEveryMonth(String startDate, String endDate,
			String way) throws Exception;
	
	/**
	 * 查询当天充值情况
	 * @return
	 * @throws Exception
	 */
	List<InpourHomeInpourVO> queryInpourCurrDate() throws Exception;
	
	/**
	 * 查询最近 7 天充值情况
	 * @return
	 * @throws Exception
	 */
	List<InpourHomeInpourVO> queryInpourLast7Day() throws Exception;
	
	/**
	 * 查询最近一个月内充值情况
	 * @return
	 * @throws Exception
	 */
	List<InpourHomeInpourVO> queryInpourLastMonth() throws Exception;
}
