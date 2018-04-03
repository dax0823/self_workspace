package com.pandadai.finance.handler;

import java.util.ArrayList;
import java.util.List;

import com.pandadai.finance.dao.impl.InpourHomeDaoImpl;
import com.pandadai.finance.vo.InpourHomeInpourVO;
import com.pandadai.finance.vo.InpourHomeVO;

/**
 * 财务总览处理
 * @author 仵作
 *
 */
public class InpourHomeHandler {
	/**
	 * 配置注入
	 */
	private InpourHomeDaoImpl inpourHomeImpl;

	/**
	 * @param inpourHomeImpl the inpourHomeImpl to set
	 */
	public void setInpourHomeImpl(InpourHomeDaoImpl inpourHomeImpl) {
		this.inpourHomeImpl = inpourHomeImpl;
	}

	/**
	 * 获取总览页面所需内容
	 * @return
	 */
	public List<InpourHomeVO> getHomeList() throws Exception {
		List<InpourHomeVO> lst = new ArrayList<InpourHomeVO>();
		//当天
		List<Float> currDateLst = inpourHomeImpl.sumCurrDate();
		InpourHomeVO currDateVO = new InpourHomeVO();
		currDateVO.setDuring("当天");
		if (currDateLst != null && currDateLst.size() == 3) {
			currDateVO.setBaofoo(currDateLst.get(0));
			currDateVO.setEasypay(currDateLst.get(1));
			currDateVO.setOff(currDateLst.get(2));
			currDateVO.setTotal(currDateLst.get(0) + currDateLst.get(1) + currDateLst.get(2));
//			currDateVO.setParams("{type: 0}");
		} else {
			currDateVO.setBaofoo(0);
			currDateVO.setEasypay(0);
			currDateVO.setOff(0);
			currDateVO.setTotal(0);
//			currDateVO.setParams("{type: 0}");
		}
		lst.add(currDateVO);
		
		//最近 7 天
		List<Float> last7DayLst = inpourHomeImpl.sumLast7Day();
		InpourHomeVO last7DayVO = new InpourHomeVO();
		last7DayVO.setDuring("一周内（截止至今天的自然日）");
		if (last7DayLst != null && last7DayLst.size() == 3) {
			last7DayVO.setBaofoo(last7DayLst.get(0));
			last7DayVO.setEasypay(last7DayLst.get(1));
			last7DayVO.setOff(last7DayLst.get(2));
			last7DayVO.setTotal(last7DayLst.get(0) + last7DayLst.get(1) + last7DayLst.get(2));
//			last7DayVO.setParams("{type: 1}");
		} else {
			last7DayVO.setBaofoo(0);
			last7DayVO.setEasypay(0);
			last7DayVO.setOff(0);
			last7DayVO.setTotal(0);
//			last7DayVO.setParams("{type: 1}");
		}
		lst.add(last7DayVO);
		
		//最近一个月
		List<Float> lastMonthLst = inpourHomeImpl.sumLastMonth();
		InpourHomeVO lastMonthVO = new InpourHomeVO();
		lastMonthVO.setDuring("一月内（截止至今天的自然日）");
		if (lastMonthLst != null && lastMonthLst.size() == 3) {
			lastMonthVO.setBaofoo(lastMonthLst.get(0));
			lastMonthVO.setEasypay(lastMonthLst.get(1));
			lastMonthVO.setOff(lastMonthLst.get(2));
			lastMonthVO.setTotal(lastMonthLst.get(0) + lastMonthLst.get(1) + lastMonthLst.get(2));
//			lastMonthVO.setParams("{type: 2}");
		} else {
			lastMonthVO.setBaofoo(0);
			lastMonthVO.setEasypay(0);
			lastMonthVO.setOff(0);
			lastMonthVO.setTotal(0);
//			lastMonthVO.setParams("{type: 2}");
		}
		lst.add(lastMonthVO);
		return lst;
	}
	
	/**
	 * 总览页-按天统计
	 * @return
	 * @throws Exception
	 */
	public List<InpourHomeVO> getHomeEveryDayList(String startDate, String endDate, String way) throws Exception {
		return inpourHomeImpl.sumEveryDay(startDate, endDate, way);
	}
	
	/**
	 * 总览页-按月统计
	 * @return
	 * @throws Exception
	 */
	public List<InpourHomeVO> getHomeEveryMonthList(String startDate, String endDate, String way) throws Exception {
		return inpourHomeImpl.sumEveryMonth(startDate, endDate, way);
	}
	
	/**
	 * 查询当天的充值情况
	 * @return
	 * @throws Exception
	 */
	public List<InpourHomeInpourVO> queryInpourCurrDate() throws Exception {
		return inpourHomeImpl.queryInpourCurrDate();
	}
	
	/**
	 * 查询最近 7 天的充值情况
	 * @return
	 * @throws Exception
	 */
	public List<InpourHomeInpourVO> queryInpourLast7Day() throws Exception {
		return inpourHomeImpl.queryInpourLast7Day();
	}
	
	/**
	 * 查询最近一个月的充值情况
	 * @return
	 * @throws Exception
	 */
	public List<InpourHomeInpourVO> queryInpourLastMonth() throws Exception {
		return inpourHomeImpl.queryInpourLastMonth();
	}
}
