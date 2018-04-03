package com.pandadai.finance.dao;

import java.util.List;

import com.pandadai.finance.vo.InpourDetailVO;

/**
 * 
 * @author 仵作
 *
 */
public interface IInpourDetailDao {

	/**
	 * 查询某段时间内，所有客户的充值情况，以天为单位
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourDetailVO> queryInpourAll(String startDate, String endDate) throws Exception;
	
	/**
	 * 查询某段时间内，baofoo 客户的充值情况，以天为单位
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourDetailVO> queryInpourByBaofoo(String startDate, String endDate) throws Exception;
	
	/**
	 * 查询某段时间内，easypay 客户的充值情况，以天为单位
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourDetailVO> queryInpourByEasypay(String startDate, String endDate) throws Exception;
	
	/**
	 * 查询某段时间内，off 客户的充值情况，以天为单位
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourDetailVO> queryInpourByOff(String startDate, String endDate) throws Exception;
	
	/**
	 * 查询某个客户某个时间段内的充值明细
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<InpourDetailVO> queryInpourCustomer(String id, String startDate, String endDate, String way) throws Exception;
	

	/**
	 *  查询某个客户某天的充值明细
	 * @param userId
	 * @param inpourTime
	 * @param way
	 * @return
	 * @throws Exception
	 */
	List<InpourDetailVO> queryInpourCustomer(String id, String inpourTime, String way) throws Exception;
}
