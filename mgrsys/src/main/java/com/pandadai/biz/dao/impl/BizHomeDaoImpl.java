/**
 * 
 */
package com.pandadai.biz.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.biz.dao.IBizHomeDao;
import com.pandadai.biz.vo.BizHomeAutoDetailVO;
import com.pandadai.biz.vo.BizHomeBalanceDetailVO;
import com.pandadai.biz.vo.BizHomeCusotmerInfoBalanceVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoBaseVO;
import com.pandadai.biz.vo.BizHomeCustomerInfoMoneyVO;
import com.pandadai.biz.vo.BizHomeInvestVO;
import com.pandadai.biz.vo.BizHomeMoneyLogVO;
import com.pandadai.biz.vo.BizLotteryLogVO;
import com.pandadai.common.utils.Constants;
import com.pandadai.finance.vo.InpourReviseVO;

/**
 * @author 仵作
 * 2014-9-14 下午4:20:41
 */
public class BizHomeDaoImpl implements IBizHomeDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlSumBorrowMoney;
	private String sqlSumInpourMoney;
	private String sqlSumFreezeMoney;
	private String sqlSumWithdrawMoney;
	private String sqlAutoNum;
	private String sqlAutoMoney;
	private String sqlAutoInfo;
	
	private String sqlSumInpourMoneyCurr;
	private String sqlSumInpourMoneyCurrDefaultCondition;
	private String sqlSumWithdrawMoneyCurr;
	private String sqlSumWithdrawMoneyCurrDefaultCondition;
	private String sqlAutoDetail;
	
	private String sqlInpourMoneyDetail;
	private String sqlWithdrawMoneyDetail;
	
	private String sqlCustomerInfoBase;
	private String sqlCustomerInfoMoney;
	private String sqlCustomerInfoInpour;
	private String sqlCustomerInfoWithdraw;
	private String sqlCustomerInfoInvest;
	private String sqlCustomerInfoMoneyLog;
	private String sqlCustomerInfoIDCard;
	
	/**
	 * @param sqlAutoInfo the sqlAutoInfo to set
	 */
	public void setSqlAutoInfo(String sqlAutoInfo) {
		this.sqlAutoInfo = sqlAutoInfo;
	}

	/**
	 * @param sqlCustomerInfoIDCard the sqlCustomerInfoIDCard to set
	 */
	public void setSqlCustomerInfoIDCard(String sqlCustomerInfoIDCard) {
		this.sqlCustomerInfoIDCard = sqlCustomerInfoIDCard;
	}

	/**
	 * @param sqlCustomerInfoMoneyLog the sqlCustomerInfoMoneyLog to set
	 */
	public void setSqlCustomerInfoMoneyLog(String sqlCustomerInfoMoneyLog) {
		this.sqlCustomerInfoMoneyLog = sqlCustomerInfoMoneyLog;
	}

	/**
	 * @param sqlCustomerInfoInvest the sqlCustomerInfoInvest to set
	 */
	public void setSqlCustomerInfoInvest(String sqlCustomerInfoInvest) {
		this.sqlCustomerInfoInvest = sqlCustomerInfoInvest;
	}

	/**
	 * @param sqlCustomerInfoBase the sqlCustomerInfoBase to set
	 */
	public void setSqlCustomerInfoBase(String sqlCustomerInfoBase) {
		this.sqlCustomerInfoBase = sqlCustomerInfoBase;
	}

	/**
	 * @param sqlCustomerInfoMoney the sqlCustomerInfoMoney to set
	 */
	public void setSqlCustomerInfoMoney(String sqlCustomerInfoMoney) {
		this.sqlCustomerInfoMoney = sqlCustomerInfoMoney;
	}

	/**
	 * @param sqlCustomerInfoInpour the sqlCustomerInfoInpour to set
	 */
	public void setSqlCustomerInfoInpour(String sqlCustomerInfoInpour) {
		this.sqlCustomerInfoInpour = sqlCustomerInfoInpour;
	}

	/**
	 * @param sqlCustomerInfoWithdraw the sqlCustomerInfoWithdraw to set
	 */
	public void setSqlCustomerInfoWithdraw(String sqlCustomerInfoWithdraw) {
		this.sqlCustomerInfoWithdraw = sqlCustomerInfoWithdraw;
	}

	/**
	 * @param sqlInpourMoneyDetail the sqlInpourMoneyDetail to set
	 */
	public void setSqlInpourMoneyDetail(String sqlInpourMoneyDetail) {
		this.sqlInpourMoneyDetail = sqlInpourMoneyDetail;
	}

	/**
	 * @param sqlWithdrawMoneyDetail the sqlWithdrawMoneyDetail to set
	 */
	public void setSqlWithdrawMoneyDetail(String sqlWithdrawMoneyDetail) {
		this.sqlWithdrawMoneyDetail = sqlWithdrawMoneyDetail;
	}

	/**
	 * @param sqlAutoDetail the sqlAutoDetail to set
	 */
	public void setSqlAutoDetail(String sqlAutoDetail) {
		this.sqlAutoDetail = sqlAutoDetail;
	}

	/**
	 * @param sqlSumInpourMoneyCurrDefaultCondition the sqlSumInpourMoneyCurrDefaultCondition to set
	 */
	public void setSqlSumInpourMoneyCurrDefaultCondition(
			String sqlSumInpourMoneyCurrDefaultCondition) {
		this.sqlSumInpourMoneyCurrDefaultCondition = sqlSumInpourMoneyCurrDefaultCondition;
	}

	/**
	 * @param sqlSumWithdrawMoneyCurrDefaultCondition the sqlSumWithdrawMoneyCurrDefaultCondition to set
	 */
	public void setSqlSumWithdrawMoneyCurrDefaultCondition(
			String sqlSumWithdrawMoneyCurrDefaultCondition) {
		this.sqlSumWithdrawMoneyCurrDefaultCondition = sqlSumWithdrawMoneyCurrDefaultCondition;
	}

	/**
	 * @param sqlSumWithdrawMoneyCurr the sqlSumWithdrawMoneyCurr to set
	 */
	public void setSqlSumWithdrawMoneyCurr(String sqlSumWithdrawMoneyCurr) {
		this.sqlSumWithdrawMoneyCurr = sqlSumWithdrawMoneyCurr;
	}

	/**
	 * @param sqlSumInpourMoneyCurr the sqlSumInpourMoneyCurr to set
	 */
	public void setSqlSumInpourMoneyCurr(String sqlSumInpourMoneyCurr) {
		this.sqlSumInpourMoneyCurr = sqlSumInpourMoneyCurr;
	}

	/**
	 * @param sqlAutoMoney the sqlAutoMoney to set
	 */
	public void setSqlAutoMoney(String sqlAutoMoney) {
		this.sqlAutoMoney = sqlAutoMoney;
	}

	/**
	 * @param sqlAutoNum the sqlAutoNum to set
	 */
	public void setSqlAutoNum(String sqlAutoNum) {
		this.sqlAutoNum = sqlAutoNum;
	}

	/**
	 * @param sqlSumWithdrawMoney the sqlSumWithdrawMoney to set
	 */
	public void setSqlSumWithdrawMoney(String sqlSumWithdrawMoney) {
		this.sqlSumWithdrawMoney = sqlSumWithdrawMoney;
	}

	/**
	 * @param sqlSumFreezeMoney the sqlSumFreezeMoney to set
	 */
	public void setSqlSumFreezeMoney(String sqlSumFreezeMoney) {
		this.sqlSumFreezeMoney = sqlSumFreezeMoney;
	}

	/**
	 * @param sqlSumInpourMoney the sqlSumInpourMoney to set
	 */
	public void setSqlSumInpourMoney(String sqlSumInpourMoney) {
		this.sqlSumInpourMoney = sqlSumInpourMoney;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlSumBorrowMoney the sqlSumBorrowMoney to set
	 */
	public void setSqlSumBorrowMoney(String sqlSumBorrowMoney) {
		this.sqlSumBorrowMoney = sqlSumBorrowMoney;
	}


	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IBizHomeDao#querySumBorrowMoney()
	 */
	@Override
	public float querySumBorrowMoney() throws Exception {
		float money = 0;
		logger.debug("querySumBorrowMoney().sql: " + sqlSumBorrowMoney + ".");
		List<Float> lst = jdbc.query(sqlSumBorrowMoney, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_borrow_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumInpourMoney()
	 */
	@Override
	public float querySumInpourMoney() throws Exception {
		float money = 0;
		logger.debug("querySumInpourMoney().sql: " + sqlSumInpourMoney + ".");
		List<Float> lst = jdbc.query(sqlSumInpourMoney, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_inpour_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumFreezeMoney()
	 */
	@Override
	public float querySumFreezeMoney() throws Exception {
		float money = 0;
		logger.debug("querySumFreezeMoney().sql: " + sqlSumFreezeMoney + ".");
		List<Float> lst = jdbc.query(sqlSumFreezeMoney, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_freeze_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumWithdrawMoney()
	 */
	@Override
	public float querySumWithdrawMoney() throws Exception {
		float money = 0;
		logger.debug("querySumWithdrawMoney().sql: " + sqlSumWithdrawMoney + ".");
		List<Float> lst = jdbc.query(sqlSumWithdrawMoney, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_withdraw_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryAutoNum()
	 */
	@Override
	public int queryAutoNum() throws Exception {
		int num = 0;
		logger.debug("queryAutoNum().sql: " + sqlAutoNum + ".");
		List<Integer> lst = jdbc.query(sqlAutoNum, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("auto_num");
			}
		});
		
		if (lst != null && lst.size() == 1)
			num = lst.get(0);
		
		return num;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumAutoMoney()
	 */
	@Override
	public float querySumAutoMoney() throws Exception {
		float money = 0;
		logger.debug("querySumAutoMoney().sql: " + sqlAutoMoney + ".");
		List<Float> lst = jdbc.query(sqlAutoMoney, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_auto_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumInpourMoneyCurrDay()
	 */
	@Override
	public float querySumInpourMoneyCurrDay() throws Exception {
		float money = 0;
		String sql = sqlSumInpourMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlSumInpourMoneyCurrDefaultCondition);
		
		logger.debug("querySumInpourMoneyCurrDay().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_inpour_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumWithdrawMoneyCurrDay()
	 */
	@Override
	public float querySumWithdrawMoneyCurrDay() throws Exception {
		float money = 0;
		String sql = sqlSumWithdrawMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlSumWithdrawMoneyCurrDefaultCondition);
		logger.debug("querySumWithdrawMoneyCurrDay().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_withdraw_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumInpourMoneyLastDay()
	 */
	@Override
	public float querySumInpourMoneyLastDay() throws Exception {
		float money = 0;
		String sql = sqlSumInpourMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1
				, " and DATE_SUB(CURDATE(), INTERVAL 1 DAY) = DATE_FORMAT(FROM_UNIXTIME(add_time),'%Y-%m-%d') ");
		
		logger.debug("querySumInpourMoneyLastDay().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_inpour_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumWithdrawMoneyLastDay()
	 */
	@Override
	public float querySumWithdrawMoneyLastDay() throws Exception {
		float money = 0;
		String sql = sqlSumWithdrawMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1
				, " and DATE_SUB(CURDATE(), INTERVAL 1 DAY) = DATE_FORMAT(FROM_UNIXTIME(add_time),'%Y-%m-%d') ");
		logger.debug("querySumWithdrawMoneyLastDay().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_withdraw_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumInpourMoneyCurrWeek()
	 */
	@Override
	public float querySumInpourMoneyCurrWeek() throws Exception {
		float money = 0;
		String sql = sqlSumInpourMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1
				, " and YEARWEEK(now()) = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m-%d')) ");
		
		logger.debug("querySumInpourMoneyCurrWeek().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_inpour_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumWithdrawMoneyCurrWeek()
	 */
	@Override
	public float querySumWithdrawMoneyCurrWeek() throws Exception {
		float money = 0;
		String sql = sqlSumWithdrawMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1
				, " and YEARWEEK(now()) = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m-%d')) ");
		
		logger.debug("querySumWithdrawMoneyCurrWeek().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_withdraw_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumInpourMoneyLastWeek()
	 */
	@Override
	public float querySumInpourMoneyLastWeek() throws Exception {
		float money = 0;
		String sql = sqlSumInpourMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1
				, " and YEARWEEK(now()) - 1 = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m-%d')) ");
		
		logger.debug("querySumInpourMoneyLastWeek().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_inpour_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#querySumWithdrawMoneyLastWeek()
	 */
	@Override
	public float querySumWithdrawMoneyLastWeek() throws Exception {
		float money = 0;
		String sql = sqlSumWithdrawMoneyCurr.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1
				, " and YEARWEEK(now()) - 1 = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m-%d')) ");
		
		logger.debug("querySumWithdrawMoneyLastWeek().sql: " + sql + ".");
		List<Float> lst = jdbc.query(sql, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum_withdraw_money");
			}
		});
		
		if (lst != null && lst.size() == 1)
			money = lst.get(0);
		
		return money;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryAutoDetailList()
	 */
	@Override
	public List<BizHomeAutoDetailVO> queryAutoDetailList() throws Exception {
		logger.debug("queryAutoDetailList().sql: " + sqlAutoDetail + ".");
		List<BizHomeAutoDetailVO> lst = jdbc.query(sqlAutoDetail, new RowMapper<BizHomeAutoDetailVO>() {
			public BizHomeAutoDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeAutoDetailVO vo = new BizHomeAutoDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setUsableMoney(rs.getFloat("usable_money"));
				vo.setCollectMoney(rs.getFloat("money_collect"));
				vo.setFreezeMoney(rs.getFloat("money_freeze"));
				vo.setRecommendUser(rs.getString("recommend_user"));
				vo.setCustomerName(rs.getString("customer_name"));
				vo.setRegTime(rs.getString("reg_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryInpourMoneyDetailCurrDay()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailCurrDay()
			throws Exception {
		String sql = sqlInpourMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and FROM_UNIXTIME(mp.add_time) = CURDATE() ");
		logger.debug("queryInpourMoneyDetailCurrDay().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setTime(rs.getString("inpour_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryInpourMoneyDetailLastDay()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailLastDay()
			throws Exception {
		String sql = sqlInpourMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and DATE_SUB(CURDATE(), INTERVAL 1 DAY) = DATE_FORMAT(FROM_UNIXTIME(mp.add_time),'%Y-%m-%d') ");
		logger.debug("queryInpourMoneyDetailLastDay().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setTime(rs.getString("inpour_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryInpourMoneyDetailCurrWeek()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailCurrWeek()
			throws Exception {
		String sql = sqlInpourMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and YEARWEEK(now()) = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(mp.add_time), '%Y-%m-%d')) ");
		logger.debug("queryInpourMoneyDetailCurrWeek().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setTime(rs.getString("inpour_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryInpourMoneyDetailLastWeek()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryInpourMoneyDetailLastWeek()
			throws Exception {
		String sql = sqlInpourMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and YEARWEEK(now()) - 1 = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(mp.add_time), '%Y-%m-%d')) ");
		logger.debug("queryInpourMoneyDetailLastWeek().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setTime(rs.getString("inpour_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryWithdrawMoneyDetailCurrDay()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailCurrDay()
			throws Exception {
		String sql = sqlWithdrawMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and FROM_UNIXTIME(mw.add_time) = CURDATE() ");
		logger.debug("queryWithdrawMoneyDetailCurrDay().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("withdraw_money"));
				vo.setTime(rs.getString("withdraw_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryWithdrawMoneyDetailLastDay()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailLastDay()
			throws Exception {
		String sql = sqlWithdrawMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and DATE_SUB(CURDATE(), INTERVAL 1 DAY) = DATE_FORMAT(FROM_UNIXTIME(mw.add_time),'%Y-%m-%d') ");
		logger.debug("queryWithdrawMoneyDetailLastDay().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("withdraw_money"));
				vo.setTime(rs.getString("withdraw_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryWithdrawMoneyDetailCurrWeek()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailCurrWeek()
			throws Exception {
		String sql = sqlWithdrawMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and YEARWEEK(now()) = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(mw.add_time), '%Y-%m-%d')) ");
		logger.debug("queryWithdrawMoneyDetailCurrWeek().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("withdraw_money"));
				vo.setTime(rs.getString("withdraw_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryWithdrawMoneyDetailLastWeek()
	 */
	@Override
	public List<BizHomeBalanceDetailVO> queryWithdrawMoneyDetailLastWeek()
			throws Exception {
		String sql = sqlWithdrawMoneyDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and YEARWEEK(now()) - 1 = YEARWEEK(DATE_FORMAT(FROM_UNIXTIME(mw.add_time), '%Y-%m-%d')) ");
		logger.debug("queryWithdrawMoneyDetailLastWeek().sql: " + sql + ".");
		List<BizHomeBalanceDetailVO> lst = jdbc.query(sql, new RowMapper<BizHomeBalanceDetailVO>() {
			public BizHomeBalanceDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeBalanceDetailVO vo = new BizHomeBalanceDetailVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("withdraw_money"));
				vo.setTime(rs.getString("withdraw_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoBase(java.lang.String)
	 */
	@Override
	public BizHomeCustomerInfoBaseVO queryCustomerInfoBase(String userId)
			throws Exception {
		String sql = sqlCustomerInfoBase.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and m.id = " + userId + " ");
		logger.debug("queryCustomerInfoBase().sql: " + sql + ".");
		List<BizHomeCustomerInfoBaseVO> lst = jdbc.query(sql, new RowMapper<BizHomeCustomerInfoBaseVO>() {
			public BizHomeCustomerInfoBaseVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeCustomerInfoBaseVO vo = new BizHomeCustomerInfoBaseVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setSex(rs.getString("sex"));
				vo.setRecommendName(rs.getString("recommend"));
				vo.setCustomerName(rs.getString("customer_name"));
				vo.setIdcard(rs.getString("idcard"));
				vo.setUserPhone(rs.getString("user_phone"));
				vo.setUserEmail(rs.getString("user_email"));
				vo.setAddress(rs.getString("address"));
				return vo;
			}
		});

		BizHomeCustomerInfoBaseVO vo = null;
		if (lst != null && lst.size() == 1)
			vo = lst.get(0);
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoMoney(java.lang.String)
	 */
	@Override
	public BizHomeCustomerInfoMoneyVO queryCustomerInfoMoney(String userId)
			throws Exception {
		String sql = sqlCustomerInfoMoney.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and mm.uid = " + userId + " ");
		logger.debug("queryCustomerInfoMoney().sql: " + sql + ".");
		List<BizHomeCustomerInfoMoneyVO> lst = jdbc.query(sql, new RowMapper<BizHomeCustomerInfoMoneyVO>() {
			public BizHomeCustomerInfoMoneyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeCustomerInfoMoneyVO vo = new BizHomeCustomerInfoMoneyVO(); 
				vo.setAccountSum(rs.getFloat("account_sum"));
				vo.setCollectMoney(rs.getFloat("money_collect"));
				vo.setUsableMoney(rs.getFloat("usable_money"));
				vo.setFreezeMoney(rs.getFloat("money_freeze"));
				return vo;
			}
		});

		BizHomeCustomerInfoMoneyVO vo = null;
		if (lst != null && lst.size() == 1)
			vo = lst.get(0);
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoInpour(java.lang.String)
	 */
	@Override
	public BizHomeCusotmerInfoBalanceVO queryCustomerInfoInpour(String userId)
			throws Exception {
		String sql = sqlCustomerInfoInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and mp.uid = " + userId + " ");
		logger.debug("queryCustomerInfoInpour().sql: " + sql + ".");
		List<BizHomeCusotmerInfoBalanceVO> lst = jdbc.query(sql, new RowMapper<BizHomeCusotmerInfoBalanceVO>() {
			public BizHomeCusotmerInfoBalanceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeCusotmerInfoBalanceVO vo = new BizHomeCusotmerInfoBalanceVO(); 
				vo.setNum(rs.getInt("num"));
				vo.setMoney(rs.getFloat("inpour_sum"));
				return vo;
			}
		});

		BizHomeCusotmerInfoBalanceVO vo = null;
		if (lst != null && lst.size() == 1)
			vo = lst.get(0);
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoWithdraw(java.lang.String)
	 */
	@Override
	public BizHomeCusotmerInfoBalanceVO queryCustomerInfoWithdraw(String userId)
			throws Exception {
		String sql = sqlCustomerInfoWithdraw.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and mw.uid = " + userId + " ");
		logger.debug("queryCustomerInfoWithdraw().sql: " + sql + ".");
		List<BizHomeCusotmerInfoBalanceVO> lst = jdbc.query(sql, new RowMapper<BizHomeCusotmerInfoBalanceVO>() {
			public BizHomeCusotmerInfoBalanceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeCusotmerInfoBalanceVO vo = new BizHomeCusotmerInfoBalanceVO(); 
				vo.setNum(rs.getInt("num"));
				vo.setMoney(rs.getFloat("withdraw_sum"));
				return vo;
			}
		});

		BizHomeCusotmerInfoBalanceVO vo = null;
		if (lst != null && lst.size() == 1)
			vo = lst.get(0);
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoInvest(java.lang.String)
	 */
	@Override
	public List<BizHomeInvestVO> queryCustomerInfoInvest(String userId)
			throws Exception {
		String sql = sqlCustomerInfoInvest.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and biv.investor_uid = " + userId + " ");
		logger.debug("queryCustomerInfoInvest().sql: " + sql + ".");
		List<BizHomeInvestVO> lst = jdbc.query(sql, new RowMapper<BizHomeInvestVO>() {
			public BizHomeInvestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeInvestVO vo = new BizHomeInvestVO();
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setInvestTime(rs.getString("invest_time"));
				vo.setInvestorCapital(rs.getFloat("investor_capital"));
				vo.setEndTime(rs.getString("end_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoMoneyLog(java.lang.String)
	 */
	@Override
	public List<BizHomeMoneyLogVO> queryCustomerInfoMoneyLog(String userId)
			throws Exception {
		String sql = sqlCustomerInfoMoneyLog.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and mml.uid = " + userId + " ");
		logger.debug("queryCustomerInfoMoneyLog().sql: " + sql + ".");
		List<BizHomeMoneyLogVO> lst = jdbc.query(sql, new RowMapper<BizHomeMoneyLogVO>() {
			public BizHomeMoneyLogVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizHomeMoneyLogVO vo = new BizHomeMoneyLogVO();
				vo.setLogTime(rs.getString("log_time"));
				vo.setAffectMoney(rs.getFloat("affect_money"));
				vo.setInfo(rs.getString("info"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryCustomerInfoIDCardPath(java.lang.String)
	 */
	@Override
	public String queryCustomerInfoIDCardPath(String userId) throws Exception {
		String sql = sqlCustomerInfoIDCard.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and mi.uid = " + userId + " ");
		logger.debug("queryCustomerInfoIDCardPath().sql: " + sql + ".");
		List<String> lst = jdbc.query(sql, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("card_img");
			}
		});

		String path = null;
		if (lst != null && lst.size() == 1)
			path = lst.get(0);
		
		return path;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryAutoInfo()
	 */
	@Override
	public Object[] queryAutoInfo() throws Exception {
		logger.debug("queryAutoInfo().sql: " + sqlAutoInfo + ".");
		List<Object[]> lst = jdbc.query(sqlAutoInfo, new RowMapper<Object[]>() {
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object[] obj = new Object[2];
				obj[0] = rs.getInt("auto_num");
				obj[1] = rs.getFloat("sum_auto_money");
				return obj;
			}
		});

		Object[] ob = null;
		if (lst != null && lst.size() == 1)
			ob = lst.get(0);
		
		return ob;
	}

}
