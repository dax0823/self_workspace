/**
 * 
 */
package com.pandadai.finance.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IInvestHomeDao;
import com.pandadai.finance.vo.InvestHomeDetail4ExportVO;
import com.pandadai.finance.vo.InvestHomeDetailVO;
import com.pandadai.finance.vo.InvestHomeSumInterestVO;
import com.pandadai.finance.vo.InvestHomeVO;

/**
 * @author 仵作
 * 2014-8-31 下午9:04:55
 */
public class InvestHomeDaoImpl implements IInvestHomeDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlHome;
	private String sqlHomeDefaultDuring;
	private String sqlDetail;
	private String sqlDetailExport;
	private String sqlSumInterset;

	/**
	 * @param sqlSumInterset the sqlSumInterset to set
	 */
	public void setSqlSumInterset(String sqlSumInterset) {
		this.sqlSumInterset = sqlSumInterset;
	}

	/**
	 * @param sqlDetailExport the sqlDetailExport to set
	 */
	public void setSqlDetailExport(String sqlDetailExport) {
		this.sqlDetailExport = sqlDetailExport;
	}

	/**
	 * @param sqlDetail the sqlDetail to set
	 */
	public void setSqlDetail(String sqlDetail) {
		this.sqlDetail = sqlDetail;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlHome the sqlHome to set
	 */
	public void setSqlHome(String sqlHome) {
		this.sqlHome = sqlHome;
	}

	/**
	 * @param sqlHomeDefaultDuring the sqlHomeDefaultDuring to set
	 */
	public void setSqlHomeDefaultDuring(String sqlHomeDefaultDuring) {
		this.sqlHomeDefaultDuring = sqlHomeDefaultDuring;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestHomeDao#queryInvestList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<InvestHomeVO> queryInvestHomeList(String startDate, String endDate)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlHomeDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(bi.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(bi.add_time), '%Y-%m-%d') ");
			}
		}
		String sql = sqlHome.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestHomeList().sql: " + sql + ".");
		
		List<InvestHomeVO> lst = jdbc.query(sql, new RowMapper<InvestHomeVO>() {
			public InvestHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestHomeVO vo = new InvestHomeVO();
				vo.setId(rs.getString("id"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setBorrowMoney(rs.getFloat("borrow_money"));
				vo.setBorrowInterest(rs.getFloat("borrow_interest"));
				vo.setBorrowInterestRate(rs.getFloat("borrow_interest_rate"));
				vo.setRewardNum(rs.getFloat("reward_num"));
//				vo.setCollectDay(rs.getString("collect_day"));
				vo.setBorrowDuration(rs.getString("borrow_duration"));
//				vo.setCollectTime(rs.getString("collect_time"));
				vo.setDeadline(rs.getString("deadline"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestHomeDao#queryInvestHomeDetailByBorrowId(java.lang.String)
	 */
	@Override
	public List<InvestHomeDetailVO> queryInvestHomeDetailByBorrowId(
			String borrowId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(borrowId)) {
			sub.append(" and bi.id = ").append(borrowId).append(" ");
		}
		String sql = sqlDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestHomeDetailByBorrowId().sql: " + sql + ".");
		
		List<InvestHomeDetailVO> lst = jdbc.query(sql, new RowMapper<InvestHomeDetailVO>() {
			public InvestHomeDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestHomeDetailVO vo = new InvestHomeDetailVO();
				vo.setUserName(rs.getString("user_name"));
				vo.setInvestTime(rs.getString("invest_time"));
				vo.setInvestorCapital(rs.getFloat("investor_capital"));
				vo.setBorrowName(rs.getString("borrow_name"));
//				vo.setCollectDay(rs.getString("collect_day"));
				vo.setBorrowDuration(rs.getString("borrow_duration"));
				vo.setBorrowInterestRate(rs.getFloat("borrow_interest_rate"));
				vo.setInvestorInterest(rs.getFloat("investor_interest"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestHomeDao#queryInvestHomeDetailByBorrowId4Export(java.lang.String)
	 */
	@Override
	public List<InvestHomeDetail4ExportVO> queryInvestHomeDetailByBorrowId4Export(
			String borrowId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(borrowId)) {
			sub.append(" and bi.id = ").append(borrowId).append(" ");
		}
		String sql = sqlDetailExport.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestHomeDetailByBorrowId4Export().sql: " + sql + ".");
		
		List<InvestHomeDetail4ExportVO> lst = jdbc.query(sql, new RowMapper<InvestHomeDetail4ExportVO>() {
			public InvestHomeDetail4ExportVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestHomeDetail4ExportVO vo = new InvestHomeDetail4ExportVO();
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setInvestorCapital(rs.getFloat("investor_capital"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setSummy(rs.getFloat("summy"));
				vo.setBorrowInterestRate(rs.getFloat("borrow_interest_rate"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestHomeDao#queryInvestHomeSumInterestByBorrorwId(java.lang.String)
	 */
	@Override
	public List<InvestHomeSumInterestVO> queryInvestHomeSumInterestByBorrorwId(
			String borrowId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(borrowId)) {
			sub.append(" and biv.borrow_id = ").append(borrowId).append(" ");
		}
		String sql = sqlSumInterset.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestHomeSumInterestByBorrorwId().sql: " + sql + ".");
		
		List<InvestHomeSumInterestVO> lst = jdbc.query(sql, new RowMapper<InvestHomeSumInterestVO>() {
			public InvestHomeSumInterestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestHomeSumInterestVO vo = new InvestHomeSumInterestVO();
				vo.setUserName(rs.getString("user_name"));
				vo.setSumInvest(rs.getFloat("sum_invest"));
				vo.setSumReward(rs.getFloat("sum_reward"));
				vo.setBorrowInterestRate(rs.getFloat("borrow_interest_rate"));
				vo.setSumInterest(rs.getFloat("sum_interest"));
				vo.setBorrowDuration(rs.getInt("borrow_duration"));
				//由于每个标的返还期限不同，所以需要根据不同的 borrow_duration 来确定显示多少个月
				try {
					for (int i = 0; i < vo.getBorrowDuration(); i++) {
						Method mt;
						mt = vo.getClass().getMethod("setMonth" + (i + 1), Float.class);
						mt.invoke(vo, rs.getFloat("month" + (i + 1)));
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return vo;
			}
		});
		return lst;
	}
}
