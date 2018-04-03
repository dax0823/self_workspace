/**
 * 
 */
package com.pandadai.biz.handler;

import java.util.List;

import com.pandadai.biz.dao.impl.BizHomeDaoImpl;
import com.pandadai.biz.dao.impl.BizLotteryLogDaoImpl;
import com.pandadai.biz.vo.BizHomeAutoDetailVO;
import com.pandadai.biz.vo.BizHomeBalanceDetailVO;
import com.pandadai.biz.vo.BizHomeCusotmerInfoBalanceVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoBaseVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoMoneyVO;
import com.pandadai.biz.vo.BizHomeInvestVO;
import com.pandadai.biz.vo.BizHomeMoneyLogVO;
import com.pandadai.biz.vo.BizLotteryLogVO;

/**
 * @author 仵作
 * 2014-9-14 下午4:33:48
 */
public class BizHomeHandler {
	/**
	 * 配置注入
	 */
	private BizHomeDaoImpl bizHomeImpl;
	
	private String idCardFolderPath;

	/**
	 * @param idCardFolderPath the idCardFolderPath to set
	 */
	public void setIdCardFolderPath(String idCardFolderPath) {
		this.idCardFolderPath = idCardFolderPath;
	}

	/**
	 * @param bizHomeImpl the bizHomeImpl to set
	 */
	public void setBizHomeImpl(BizHomeDaoImpl bizHomeImpl) {
		this.bizHomeImpl = bizHomeImpl;
	}

	/**
	 * 获取平台已借出款项总额
	 * @return
	 * @throws Exception
	 */
	public float querySumBorrowMoney() throws Exception {
		return bizHomeImpl.querySumBorrowMoney();
	}

	/**
	 * 获取平台当前可用金额
	 * @return
	 * @throws Exception
	 */
	public float queryUsableMoney() throws Exception {
//		可用金额 = 总充值金额 - 总提现金额 - 总冻结金额 - 总借出金额
		float usableMoney = bizHomeImpl.querySumInpourMoney() - bizHomeImpl.querySumWithdrawMoney()
				- bizHomeImpl.querySumFreezeMoney() - bizHomeImpl.querySumBorrowMoney();
		return usableMoney;
	}
	
	/**
	 * 获取平台自动投标总人数
	 * @return
	 * @throws Exception
	 */
	public int queryAutoNum() throws Exception {
		return bizHomeImpl.queryAutoNum();
	}
	
	/**
	 * 获取平台自动投标
	 * @return
	 * @throws Exception
	 */
	public float querySumAutoMoney() throws Exception {
		return bizHomeImpl.querySumAutoMoney();
	}
	
	/**
	 * 获取平台自动投标的概要信息
	 * @return
	 * @throws Exception
	 */
	public Object[] queryAutoInfo() throws Exception {
		return bizHomeImpl.queryAutoInfo();
	}
	
	/**
	 * 获取平台当天充值总额
	 * @return
	 * @throws Exception
	 */
	public float querySumInpourMoneyCurrDay() throws Exception {
		return bizHomeImpl.querySumInpourMoneyCurrDay();
	}
	
	/**
	 * 获取平台当天提现总额
	 * @return
	 * @throws Exception
	 */
	public float querySumWithdrawMoneyCurrDay() throws Exception {
		return bizHomeImpl.querySumWithdrawMoneyCurrDay();
	}
	
	/**
	 * 获取平台当天进出值
	 * @return
	 * @throws Exception
	 */
	public float queryBalanceCurrDay() throws Exception {
//		进出 = 充值 - 提现
		return bizHomeImpl.querySumInpourMoneyCurrDay() - bizHomeImpl.querySumWithdrawMoneyCurrDay();
	}
	
	/**
	 * 获取平台昨天进出值
	 * @return
	 * @throws Exception
	 */
	public float queryBalanceLastDay() throws Exception {
		return bizHomeImpl.querySumInpourMoneyLastDay() - bizHomeImpl.querySumWithdrawMoneyLastDay();
	}
	
	/**
	 * 获取平台本周进出值
	 * @return
	 * @throws Exception
	 */
	public float queryBalanceCurrWeek() throws Exception {
		return bizHomeImpl.querySumInpourMoneyCurrWeek() - bizHomeImpl.querySumWithdrawMoneyCurrWeek();
	}
	
	/**
	 * 获取平台上周进出值
	 * @return
	 * @throws Exception
	 */
	public float queryBalanceLastWeek() throws Exception {
		return bizHomeImpl.querySumInpourMoneyLastWeek() - bizHomeImpl.querySumWithdrawMoneyLastWeek();
	}
	
	/**
	 * 获取平台自动投标排队详情
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeAutoDetailVO> queryAutoDetailList() throws Exception {
		return bizHomeImpl.queryAutoDetailList();
	}
	
	/**
	 * 获取当天充值的明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailCurrDay() throws Exception {
		return bizHomeImpl.queryInpourMoneyDetailCurrDay();
	}
	
	/**
	 * 获取昨天充值的明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailLastDay() throws Exception {
		return bizHomeImpl.queryInpourMoneyDetailLastDay();
	}
	
	/**
	 * 获取本周充值的明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailCurrWeek() throws Exception {
		return bizHomeImpl.queryInpourMoneyDetailCurrWeek();
	}
	
	/**
	 * 获取上周的充值明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailLastWeek() throws Exception {
		return bizHomeImpl.queryInpourMoneyDetailLastWeek();
	}
	
	/**
	 * 获取当天提现的明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailCurrDay() throws Exception {
		return bizHomeImpl.queryWithdrawMoneyDetailCurrDay();
	}
	
	/**
	 * 获取昨天提现的明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailLastDay() throws Exception {
		return bizHomeImpl.queryWithdrawMoneyDetailLastDay();
	}
	
	/**
	 * 获取本周提现的明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailCurrWeek() throws Exception {
		return bizHomeImpl.queryWithdrawMoneyDetailCurrWeek();
	}
	
	/**
	 * 获取上周的提现明细记录
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailLastWeek() throws Exception {
		return bizHomeImpl.queryWithdrawMoneyDetailLastWeek();
	}
	
	/**
	 * 获取用户基础信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public BizHomeCustomerInfoBaseVO queryCustomerInfoBase(String userId) throws Exception {
		return bizHomeImpl.queryCustomerInfoBase(userId);
	}
	
	/**
	 * 获取用户当前资金状况信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public BizHomeCustomerInfoMoneyVO queryCustomerInfoMoney(String userId) throws Exception {
		return bizHomeImpl.queryCustomerInfoMoney(userId);
	}
	
	/**
	 * 获取用户充值信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public BizHomeCusotmerInfoBalanceVO queryCustomerInfoInpour(String userId) throws Exception {
		return bizHomeImpl.queryCustomerInfoInpour(userId);
	}
	
	/**
	 * 获取用户提现信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public BizHomeCusotmerInfoBalanceVO queryCustomerInfoWithdraw(String userId) throws Exception {
		return bizHomeImpl.queryCustomerInfoWithdraw(userId);
	}
	
	/**
	 * 获取用户投标信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeInvestVO> queryCustomerInfoInvest(String userId) throws Exception {
		return bizHomeImpl.queryCustomerInfoInvest(userId);
	}
	
	/**
	 * 获取用户资金变动记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<BizHomeMoneyLogVO> queryCustomerInfoMoneyLog(String userId) throws Exception {
		return bizHomeImpl.queryCustomerInfoMoneyLog(userId);
	}
	
	/**
	 * 获取用户身份证号目录的绝对路径
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getIDCardImgPath(String userId) throws Exception {
		String path = null;
		if (!idCardFolderPath.endsWith("/")) {
			idCardFolderPath += "/";
		}
		path = idCardFolderPath + bizHomeImpl.queryCustomerInfoIDCardPath(userId);
		
		//路径斜杠转换
		path = path.replaceAll("\\\\", "/");
		
		return path;
	}
	
}
