/**
 * 
 */
package com.pandadai.biz.dao;

import java.util.List;

import com.pandadai.biz.vo.BizHomeAutoDetailVO;
import com.pandadai.biz.vo.BizHomeBalanceDetailVO;
import com.pandadai.biz.vo.BizHomeCusotmerInfoBalanceVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoBaseVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoMoneyVO;
import com.pandadai.biz.vo.BizHomeInvestVO;
import com.pandadai.biz.vo.BizHomeMoneyLogVO;

/**
 * @author 仵作
 * 2014-9-14 下午4:18:42
 */
public interface IBizHomeDao {
	
	/**
	 * 查询平台所有已借出款总额
	 * @return
	 * @throws Exception
	 */
	float querySumBorrowMoney() throws Exception;
	
	/**
	 * 查询充值总金额
	 * @return
	 * @throws Exception
	 */
	float querySumInpourMoney() throws Exception;
	
	/**
	 * 查询冻结总金额
	 * @return
	 * @throws Exception
	 */
	float querySumFreezeMoney() throws Exception;
	
	/**
	 * 获取提现总金额
	 * @return
	 * @throws Exception
	 */
	float querySumWithdrawMoney() throws Exception;
	
	/**
	 * 获取自动投标总人数
	 * @return
	 * @throws Exception
	 */
	int queryAutoNum() throws Exception;
	
	/**
	 * 获取自动投标概要信息
	 * @return
	 * @throws Exception
	 */
	Object[] queryAutoInfo() throws Exception;
	
	/**
	 * 获取自动投标总金额
	 * @return
	 * @throws Exception
	 */
	float querySumAutoMoney() throws Exception;
	
	/**
	 * 获取当天充值总额
	 * @return
	 * @throws Exception
	 */
	float querySumInpourMoneyCurrDay() throws Exception;
	
	/**
	 * 获取当天提现总额
	 * @return
	 * @throws Exception
	 */
	float querySumWithdrawMoneyCurrDay() throws Exception;

	/**
	 * 获取昨天充值总额
	 * @return
	 * @throws Exception
	 */
	float querySumInpourMoneyLastDay() throws Exception;
	
	/**
	 * 获取昨天提现总额
	 * @return
	 * @throws Exception
	 */
	float querySumWithdrawMoneyLastDay() throws Exception;
	
	/**
	 * 获取本周充值总额
	 * @return
	 * @throws Exception
	 */
	float querySumInpourMoneyCurrWeek() throws Exception;
	
	/**
	 * 获取本周提现总额
	 * @return
	 * @throws Exception
	 */
	float querySumWithdrawMoneyCurrWeek() throws Exception;
	
	/**
	 * 获取上周充值总额
	 * @return
	 * @throws Exception
	 */
	float querySumInpourMoneyLastWeek() throws Exception;
	
	/**
	 * 获取上周提现总额
	 * @return
	 * @throws Exception
	 */
	float querySumWithdrawMoneyLastWeek() throws Exception;
	
	/**
	 * 获取自动投标排队详情
	 * @return
	 * @throws Exception
	 */
	List<BizHomeAutoDetailVO> queryAutoDetailList() throws Exception;
	
	/**
	 * 获取当天充值的明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryInpourMoneyDetailCurrDay() throws Exception;
	
	/**
	 * 获取昨天充值的明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryInpourMoneyDetailLastDay() throws Exception;
	
	/**
	 * 获取本周充值的明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryInpourMoneyDetailCurrWeek() throws Exception;
	
	/**
	 * 获取上周的充值明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryInpourMoneyDetailLastWeek() throws Exception;
	
	/**
	 * 获取当天提现的明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailCurrDay() throws Exception;
	
	/**
	 * 获取昨天提现的明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailLastDay() throws Exception;
	
	/**
	 * 获取本周提现的明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailCurrWeek() throws Exception;
	
	/**
	 * 获取上周的提现明细记录
	 * @return
	 * @throws Exception
	 */
	List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailLastWeek() throws Exception;
	
	/**
	 * 获取用户基础信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BizHomeCustomerInfoBaseVO queryCustomerInfoBase(String userId) throws Exception;
	
	/**
	 * 获取用户当前资金状况信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BizHomeCustomerInfoMoneyVO queryCustomerInfoMoney(String userId) throws Exception;
	
	/**
	 * 获取用户充值信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BizHomeCusotmerInfoBalanceVO queryCustomerInfoInpour(String userId) throws Exception;
	
	/**
	 * 获取用户提现信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BizHomeCusotmerInfoBalanceVO queryCustomerInfoWithdraw(String userId) throws Exception;
	
	/**
	 * 获取用户投标信息记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<BizHomeInvestVO> queryCustomerInfoInvest(String userId) throws Exception;
	
	/**
	 * 获取用户资金变动记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<BizHomeMoneyLogVO> queryCustomerInfoMoneyLog(String userId) throws Exception;
	
	/**
	 * 获取用户身份证目录的相对路径
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	String queryCustomerInfoIDCardPath(String userId) throws Exception;
	
}
