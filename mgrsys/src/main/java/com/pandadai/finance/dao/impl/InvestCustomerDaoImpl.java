/**
 * 
 */
package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IInvestCustomerDao;
import com.pandadai.finance.vo.InvestCustomerBackCapitalVO;
import com.pandadai.finance.vo.InvestCustomerInfoVO;
import com.pandadai.finance.vo.InvestCustomerInpourVO;
import com.pandadai.finance.vo.InvestCustomerInterestVO;
import com.pandadai.finance.vo.InvestCustomerInvestVO;
import com.pandadai.finance.vo.InvestCustomerInvitedVO;
import com.pandadai.finance.vo.InvestCustomerUncollectedDetailVO;
import com.pandadai.finance.vo.InvestCustomerUncollectedVO;
import com.pandadai.finance.vo.InvestCustomerVO;

/**
 * @author 仵作 
 * 2014-9-3 下午8:32:26
 */
public class InvestCustomerDaoImpl implements IInvestCustomerDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlCustomer;
	private String sqlCustomerDefaultDuring;
	private String sqlCustomerInfo;
	private String sqlCustomerInpour;
	private String sqlCustomerInvest;
	private String sqlCustomerInterest;
	private String sqlCustomerBackCapital;
	private String sqlCustomerUncollected;
	private String sqlCustomerInvited;
	private String sqlCustomerUncollecteDetail;
	
	
	public void setSqlCustomerUncollecteDetail(String sqlCustomerUncollecteDetail) {
		this.sqlCustomerUncollecteDetail = sqlCustomerUncollecteDetail;
	}

	public void setSqlCustomerInvited(String sqlCustomerInvited) {
		this.sqlCustomerInvited = sqlCustomerInvited;
	}

	public void setSqlCustomerUncollected(String sqlCustomerUncollected) {
		this.sqlCustomerUncollected = sqlCustomerUncollected;
	}

	public void setSqlCustomerDefaultDuring(String sqlCustomerDefaultDuring) {
		this.sqlCustomerDefaultDuring = sqlCustomerDefaultDuring;
	}

	/**
	 * @param sqlCustomerInfo the sqlCustomerInfo to set
	 */
	public void setSqlCustomerInfo(String sqlCustomerInfo) {
		this.sqlCustomerInfo = sqlCustomerInfo;
	}

	/**
	 * @param sqlCustomerInpour the sqlCustomerInpour to set
	 */
	public void setSqlCustomerInpour(String sqlCustomerInpour) {
		this.sqlCustomerInpour = sqlCustomerInpour;
	}

	/**
	 * @param sqlCustomerInvest the sqlCustomerInvest to set
	 */
	public void setSqlCustomerInvest(String sqlCustomerInvest) {
		this.sqlCustomerInvest = sqlCustomerInvest;
	}

	/**
	 * @param sqlCustomerInterest the sqlCustomerInterest to set
	 */
	public void setSqlCustomerInterest(String sqlCustomerInterest) {
		this.sqlCustomerInterest = sqlCustomerInterest;
	}

	/**
	 * @param sqlCustomerBackCapital the sqlCustomerBackCapital to set
	 */
	public void setSqlCustomerBackCapital(String sqlCustomerBackCapital) {
		this.sqlCustomerBackCapital = sqlCustomerBackCapital;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlCustomer the sqlCustomer to set
	 */
	public void setSqlCustomer(String sqlCustomer) {
		this.sqlCustomer = sqlCustomer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pandadai.finance.dao.InvestCustomerDao#queryInvestCustomerList()
	 */
	@Override
	public List<InvestCustomerVO> queryInvestCustomerList() throws Exception {
		return queryInvestCustomerList(null, null, null, null, null);
	}
	
	@Override
	public List<InvestCustomerVO> queryInvestCustomerList(String userName,
			String realName, String recommendName, String startDate, String endDate)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlCustomerDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(m.reg_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(m.reg_time), '%Y-%m-%d') ");
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			sub.append(" and m.user_name like '%" + userName + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			sub.append(" and mmi.real_name like '%" + realName + "%' ");
		}
		if (StringUtils.isNotBlank(recommendName)) {	//抽奖奖品类型，如“购物卡”、“京东”、“话费”之类
			sub.append(" and m2.user_name like '%" + recommendName + "%' ");
		}
		
		String sql = sqlCustomer.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerList().sql: " + sql + ".");
		List<InvestCustomerVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerVO>() {
			public InvestCustomerVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerVO vo = new InvestCustomerVO();
				vo.setId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setaId(rs.getString("aid"));
				vo.setaUserName(rs.getString("auser_name"));
				vo.setAccountMoney(rs.getFloat("account_money"));
				vo.setSumInvest(rs.getFloat("sum_invest"));
				vo.setSumInterest(rs.getFloat("sum_interest"));
				vo.setSumFee(rs.getFloat("sum_fee"));
				vo.setSumRecommendReward(rs.getFloat("sum_recommend_reward"));
				vo.setSumInpour(rs.getFloat("sum_inpour"));
				//客服奖励为客户投资总额的千分之一
				vo.setSumAuserReward(rs.getFloat("sum_invest") * 0.001f);
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestCustomerDao#queryInvestCustomerInfo()
	 */
	@Override
	public InvestCustomerInfoVO queryInvestCustomerInfo(String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and m.id = ").append(userId).append(" ");
		}
		String sql = sqlCustomerInfo.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerInfo().sql: " + sql + ".");
		
		List<InvestCustomerInfoVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerInfoVO>() {
			public InvestCustomerInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerInfoVO vo = new InvestCustomerInfoVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setAccountMoney(rs.getFloat("account_money"));
				vo.setBankNum(rs.getString("bank_num"));
				vo.setBankName(rs.getString("bank_name"));
				vo.setBankAddress(rs.getString("bank_address"));
				vo.setSumInvest(rs.getFloat("sum_invest"));
				vo.setIdCard(rs.getString("idcard"));
				
				vo.setRecommendId(rs.getString("recommend_id"));
				vo.setRecommendName(rs.getString("recommend_name"));
				return vo;
			}
		});
		
		//当查出的结果仅为一条时
		InvestCustomerInfoVO customerVO = null;
		if (lst != null && lst.size() == 1) {
			customerVO = lst.get(0);
		}
		return customerVO;
	}

	@Override
	public List<InvestCustomerInpourVO> queryInvestCustomerInpour(String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and mp.uid = ").append(userId).append(" ");
		}
		String sql = sqlCustomerInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerInpour().sql: " + sql + ".");
		
		List<InvestCustomerInpourVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerInpourVO>() {
			public InvestCustomerInpourVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerInpourVO vo = new InvestCustomerInpourVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setMoney(rs.getFloat("money"));
				vo.setWay(rs.getString("way"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestCustomerDao#queryInvestCustomerInvest(java.lang.String)
	 */
	@Override
	public List<InvestCustomerInvestVO> queryInvestCustomerInvest(String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and biv.investor_uid = ").append(userId).append(" ");
		}
		String sql = sqlCustomerInvest.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerInvest().sql: " + sql + ".");
		
		List<InvestCustomerInvestVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerInvestVO>() {
			public InvestCustomerInvestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerInvestVO vo = new InvestCustomerInvestVO();
				vo.setBorrowId(rs.getString("id"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setInvestorCapital(rs.getFloat("investor_capital"));
				vo.setInvestTime(rs.getString("invest_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestCustomerDao#queryInvestCustomerInterest(java.lang.String)
	 */
	@Override
	public List<InvestCustomerInterestVO> queryInvestCustomerInterest(String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and id.investor_uid = ").append(userId).append(" ");
		}
		String sql = sqlCustomerInterest.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerInterest().sql: " + sql + ".");
		
		List<InvestCustomerInterestVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerInterestVO>() {
			public InvestCustomerInterestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerInterestVO vo = new InvestCustomerInterestVO();
				vo.setBorrowId(rs.getString("id"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setTotal(rs.getString("total"));
				vo.setSortOrder(rs.getString("sort_order"));
				vo.setInterestTime(rs.getString("interest_time"));
				vo.setInterest(rs.getFloat("interest"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestCustomerDao#queryInvestCustomerBackCapital(java.lang.String)
	 */
	@Override
	public List<InvestCustomerBackCapitalVO> queryInvestCustomerBackCapital(
			String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and id.investor_uid = ").append(userId).append(" ");
		}
		String sql = sqlCustomerBackCapital.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerBackCapital().sql: " + sql + ".");
		
		List<InvestCustomerBackCapitalVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerBackCapitalVO>() {
			public InvestCustomerBackCapitalVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerBackCapitalVO vo = new InvestCustomerBackCapitalVO();
				vo.setBorrowId(rs.getString("id"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setBackTime(rs.getString("back_time"));
				vo.setCapital(rs.getFloat("capital"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public InvestCustomerUncollectedVO queryInvestCustomerUncollected(String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and mm.id = ").append(userId).append(" ");
		}
		String sql = sqlCustomerUncollected.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerUncollected().sql: " + sql + ".");
		
		List<InvestCustomerUncollectedVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerUncollectedVO>() {
			public InvestCustomerUncollectedVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerUncollectedVO vo = new InvestCustomerUncollectedVO();
				vo.setId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setSumUncollected(rs.getFloat("sum"));
				return vo;
			}
		});

		//当查出的结果仅为一条时
		InvestCustomerUncollectedVO vo= null;
		if (lst != null && lst.size() == 1) {
			vo = lst.get(0);
		}
		return vo;
	}

	@Override
	public List<InvestCustomerInvitedVO> queryInvestCustomerInvited(String userId)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and mm.id = ").append(userId).append(" ");
		}
		String sql = sqlCustomerInvited.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerInvited().sql: " + sql + ".");
		
		List<InvestCustomerInvitedVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerInvitedVO>() {
			public InvestCustomerInvitedVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerInvitedVO vo = new InvestCustomerInvitedVO();
				vo.setId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setInvitedId(rs.getString("invited_id"));
				vo.setInvitedName(rs.getString("invited_name"));
				return vo;
			}
		});
		
		return lst;
	}

	@Override
	public List<InvestCustomerUncollectedDetailVO> queryInvestCustomerUncollectedDetail(
			String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and mm.id = ").append(userId).append(" ");
		}
		String sql = sqlCustomerUncollecteDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestCustomerUncollectedDetail().sql: " + sql + ".");
		
		List<InvestCustomerUncollectedDetailVO> lst = jdbc.query(sql, new RowMapper<InvestCustomerUncollectedDetailVO>() {
			public InvestCustomerUncollectedDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestCustomerUncollectedDetailVO vo = new InvestCustomerUncollectedDetailVO();
//				select mm.id, mm.user_name, bif.id borrow_id, bif.borrow_name, bif.borrow_money, bif.borrow_duration, biv.investor_capital, FROM_UNIXTIME(biv.add_time) invest_time
//				private String borrowId;
//				private String borrowName;
//				private int borrowMoney;
//				private int duration;
//				private int capital;
//				private String investTime;
				vo.setBorrowId(rs.getString("borrow_id"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setBorrowMoney(rs.getInt("borrow_money"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setCapital(rs.getInt("investor_capital"));
				vo.setInvestTime(rs.getString("invest_time"));
				return vo;
			}
		});
		
		return lst;
	}
}
