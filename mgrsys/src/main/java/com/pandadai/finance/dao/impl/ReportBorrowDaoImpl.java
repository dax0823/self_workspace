package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.finance.dao.IReportBorrowDao;
import com.pandadai.finance.vo.ReportBorrowVO;
import com.pandadai.finance.vo.ReportHomeVO;
import com.pandadai.finance.vo.ReportSecondVO;

public class ReportBorrowDaoImpl implements IReportBorrowDao {
	Logger logger = Logger.getLogger(this.getClass());
	
	private JdbcTemplate jdbc;
	private String sqlBorrow;
	private String sqlBorrowDefaultMonth;
	private double sqlBorrowConstant;
	
	private String sqlSecond;
	private String sqlSecondDefaultMonth;
	
	public void setSqlSecond(String sqlSecond) {
		this.sqlSecond = sqlSecond;
	}

	public void setSqlSecondDefaultMonth(String sqlSecondDefaultMonth) {
		this.sqlSecondDefaultMonth = sqlSecondDefaultMonth;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setSqlBorrow(String sqlBorrow) {
		this.sqlBorrow = sqlBorrow;
	}

	public void setSqlBorrowDefaultMonth(String sqlBorrowDefaultMonth) {
		this.sqlBorrowDefaultMonth = sqlBorrowDefaultMonth;
	}

	public void setSqlBorrowConstant(double sqlBorrowConstant) {
		this.sqlBorrowConstant = sqlBorrowConstant;
	}

	@Override
	public List<ReportBorrowVO> queryReportBorrowList(String month)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(month)) {
			sub.append(sqlBorrow).append(" ").append(sqlBorrowDefaultMonth);
		} else {
			sub.append(sqlBorrow).append(" '").append(month).append("'");
		}
		logger.debug("queryReportBorrowList().sql: " + sub.toString() + ".");
		List<ReportBorrowVO> lst = jdbc.query(sub.toString(), new RowMapper<ReportBorrowVO>() {
			public ReportBorrowVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportBorrowVO vo = new ReportBorrowVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("borrow_name"));
				vo.setTime(rs.getString("time"));
				vo.setMoney(rs.getInt("borrow_money"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setInterestRate(rs.getDouble("borrow_interest_rate"));
				vo.setRewardRate(rs.getDouble("reward_num"));
				vo.setInterest(rs.getInt("borrow_interest"));
				vo.setBorrowFee(rs.getInt("borrow_fee"));
				vo.setFee(rs.getInt("fee"));
				
				vo.setIncome(vo.getBorrowFee() + vo.getFee());
				double spreads = (sqlBorrowConstant - (vo.getInterestRate() * 0.01 / 12 + vo.getRewardRate() * 0.01 / vo.getDuration())) * vo.getMoney();
				vo.setSpreads((int) spreads);
				return vo;
			}
		});
		
		return lst;
	}

	@Override
	public List<ReportSecondVO> queryReportSecondList(String month)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(month)) {
			sub.append(sqlSecond).append(" ").append(sqlSecondDefaultMonth);
		} else {
			sub.append(sqlSecond).append(" '").append(month).append("'");
		}
		logger.debug("queryReportSecondList().sql: " + sub.toString() + ".");
		List<ReportSecondVO> lst = jdbc.query(sub.toString(), new RowMapper<ReportSecondVO>() {
			public ReportSecondVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportSecondVO vo = new ReportSecondVO();
				vo.setId(rs.getString("id"));
				vo.setId(rs.getString("id"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setTime(rs.getString("time"));
				vo.setMoney(rs.getInt("borrow_money"));
				vo.setInterestRate(rs.getDouble("borrow_interest_rate"));
				vo.setInterest(rs.getDouble("borrow_interest"));
				vo.setFee(rs.getDouble("fee"));
				vo.setFinalMoney(rs.getDouble("final_money"));
				
				return vo;
			}
		});
		
		return lst;
	}

}
