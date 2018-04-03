package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IReportFilingDao;
import com.pandadai.finance.vo.InvestCustomerVO;
import com.pandadai.finance.vo.ReportBorrowVO;
import com.pandadai.finance.vo.ReportFilingBorrowVO;
import com.pandadai.finance.vo.ReportFilingVO;

public class ReportFilingDaoImpl implements IReportFilingDao {
	Logger logger = Logger.getLogger(this.getClass());
	
	private JdbcTemplate jdbc;
	private String sqlBorrowListById;
	private String sqlBorrowListByCondition;
	private String sqlBorrowListByConditionDefaultDuring;
	private String sqlReportFiling;
	
	public void setSqlReportFiling(String sqlReportFiling) {
		this.sqlReportFiling = sqlReportFiling;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setSqlBorrowListById(String sqlBorrowListById) {
		this.sqlBorrowListById = sqlBorrowListById;
	}

	public void setSqlBorrowListByCondition(String sqlBorrowListByCondition) {
		this.sqlBorrowListByCondition = sqlBorrowListByCondition;
	}

	public void setSqlBorrowListByConditionDefaultDuring(
			String sqlBorrowListByConditionDefaultDuring) {
		this.sqlBorrowListByConditionDefaultDuring = sqlBorrowListByConditionDefaultDuring;
	}

	@Override
	public List<ReportFilingBorrowVO> queryReportBorrowList(String id)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(id)) {
			//必须传入 id
			return null;
		} else {
			sub.append(sqlBorrowListById).append(" ").append(id);
		}
		logger.debug("queryReportBorrowList().sql: " + sub.toString() + ".");
		List<ReportFilingBorrowVO> lst = jdbc.query(sub.toString(), new RowMapper<ReportFilingBorrowVO>() {
			public ReportFilingBorrowVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportFilingBorrowVO vo = new ReportFilingBorrowVO();
				vo.setId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setTime(rs.getString("time"));
				vo.setMoney(rs.getInt("borrow_money"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setInterestRate(rs.getDouble("borrow_interest_rate"));
				vo.setRewardRate(rs.getDouble("reward_num"));
				return vo;
			}
		});
		
		return lst;
	}

	@Override
	public List<ReportFilingBorrowVO> queryReportBorrowList(String userName,String borrowName, String startDate, String endDate) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlBorrowListByConditionDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m-%d') ");
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			sub.append(" and user_name like '%" + userName + "%' ");
		}
		if (StringUtils.isNotBlank(borrowName)) {
			sub.append(" and borrow_name like '%" + borrowName + "%' ");
		}
		String sql = sqlBorrowListByCondition.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryReportBorrowList().sql: " + sql + ".");
		List<ReportFilingBorrowVO> lst = jdbc.query(sql, new RowMapper<ReportFilingBorrowVO>() {
			public ReportFilingBorrowVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportFilingBorrowVO vo = new ReportFilingBorrowVO();
				vo.setId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setTime(rs.getString("time"));
				vo.setMoney(rs.getInt("borrow_money"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setInterestRate(rs.getDouble("borrow_interest_rate"));
				vo.setRewardRate(rs.getDouble("reward_num"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<ReportFilingVO> queryReportFiling(String id) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(id)) {
			sub.append(" and bif.id = " + id + " ");
		}
		String sql = sqlReportFiling.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryReportFiling().sql: " + sql + ".");
		List<ReportFilingVO> lst = jdbc.query(sql, new RowMapper<ReportFilingVO>() {
			public ReportFilingVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportFilingVO vo = new ReportFilingVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setIDcard(rs.getString("idcard"));
				vo.setCapital(rs.getDouble("capital"));
				vo.setInterest(rs.getDouble("interest"));
				vo.setUserPhone(rs.getString("user_phone"));
				vo.setBankName(rs.getString("bank_name"));
				vo.setBankNum(rs.getString("bank_num"));
				return vo;
			}
		});
		return lst;
	}

}
