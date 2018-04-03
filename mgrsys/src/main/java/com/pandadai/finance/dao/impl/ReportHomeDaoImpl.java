package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.finance.dao.IReportHomeDao;
import com.pandadai.finance.vo.ReportHomeVO;

public class ReportHomeDaoImpl implements IReportHomeDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlNumbers;
	private String sqlNumbersDefaultMonth;
	private String sqlVolume;
	private String sqlVolumeDefaultMonth;
	private String sqlBack;
	private String sqlBackDefaultMonth;
	private String sqlUncollectedSite;
	private String sqlUncollectedTmp;
	private String sqlUncollectedTmp2;
	private String sqlUncollectedDefaultMonth;
	private String sqlUncollectedDefaultMonth2;
	private String sqlRegisterNew;
	private String sqlRegisterNewDefaultMonth;
	private String sqlRegisterSum;
	
	public void setSqlRegisterSum(String sqlRegisterSum) {
		this.sqlRegisterSum = sqlRegisterSum;
	}

	public void setSqlRegisterNew(String sqlRegisterNew) {
		this.sqlRegisterNew = sqlRegisterNew;
	}

	public void setSqlRegisterNewDefaultMonth(String sqlRegisterNewDefaultMonth) {
		this.sqlRegisterNewDefaultMonth = sqlRegisterNewDefaultMonth;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setSqlUncollectedSite(String sqlUncollectedSite) {
		this.sqlUncollectedSite = sqlUncollectedSite;
	}

	public void setSqlUncollectedTmp(String sqlUncollectedTmp) {
		this.sqlUncollectedTmp = sqlUncollectedTmp;
	}

	public void setSqlUncollectedTmp2(String sqlUncollectedTmp2) {
		this.sqlUncollectedTmp2 = sqlUncollectedTmp2;
	}

	public void setSqlUncollectedDefaultMonth(String sqlUncollectedDefaultMonth) {
		this.sqlUncollectedDefaultMonth = sqlUncollectedDefaultMonth;
	}

	public void setSqlUncollectedDefaultMonth2(String sqlUncollectedDefaultMonth2) {
		this.sqlUncollectedDefaultMonth2 = sqlUncollectedDefaultMonth2;
	}

	public void setSqlBack(String sqlBack) {
		this.sqlBack = sqlBack;
	}

	public void setSqlBackDefaultMonth(String sqlBackDefaultMonth) {
		this.sqlBackDefaultMonth = sqlBackDefaultMonth;
	}

	public void setSqlVolume(String sqlVolume) {
		this.sqlVolume = sqlVolume;
	}

	public void setSqlVolumeDefaultMonth(String sqlVolumeDefaultMonth) {
		this.sqlVolumeDefaultMonth = sqlVolumeDefaultMonth;
	}

	public void setSqlNumbers(String sqlNumbers) {
		this.sqlNumbers = sqlNumbers;
	}

	public void setSqlNumbersDefaultMonth(String sqlNumbersDefaultMonth) {
		this.sqlNumbersDefaultMonth = sqlNumbersDefaultMonth;
	}

	@Override
	public ReportHomeVO queryReportNumbers(String month) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(month)) {
			sub.append(sqlNumbers).append(" ").append(sqlNumbersDefaultMonth);
		} else {
			sub.append(sqlNumbers).append(" '").append(month).append("'");
		}
		logger.debug("queryReportNumbers().sql: " + sub.toString() + ".");
		List<ReportHomeVO> lst = jdbc.query(sub.toString(), new RowMapper<ReportHomeVO>() {
			public ReportHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportHomeVO vo = new ReportHomeVO();
				vo.setMonth(rs.getString("month"));
				vo.setNumbers(rs.getInt("numbers"));
				return vo;
			}
		});
		
		//当查出的结果仅为一条时
		ReportHomeVO vo = null;
		if (lst != null && lst.size() == 1) {
			vo = lst.get(0);
		}
		
		return vo;
	}

	@Override
	public ReportHomeVO queryReportNumbers() throws Exception {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//		String dateStr = formatter.format(new Date());
//		return queryReportInfo(dateStr);
		return queryReportNumbers(null);
	}

	@Override
	public ReportHomeVO queryReportVolume(String month) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(month)) {
			sub.append(sqlVolume).append(" ").append(sqlVolumeDefaultMonth);
		} else {
			sub.append(sqlVolume).append(" '").append(month).append("'");
		}
		logger.debug("queryReportVolume().sql: " + sub.toString() + ".");
		List<ReportHomeVO> lst = jdbc.query(sub.toString(), new RowMapper<ReportHomeVO>() {
			public ReportHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportHomeVO vo = new ReportHomeVO();
				vo.setMonth(rs.getString("month"));
				vo.setVolume(rs.getInt("volume"));
				return vo;
			}
		});
		
		//当查出的结果仅为一条时
		ReportHomeVO vo = null;
		if (lst != null && lst.size() == 1) {
			vo = lst.get(0);
		}
		
		return vo;
	}

	@Override
	public ReportHomeVO queryReportBack(String month) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(month)) {
			sub.append(sqlBack).append(" ").append(sqlBackDefaultMonth);
		} else {
			sub.append(sqlBack).append(" '").append(month).append("'");
		}
		logger.debug("queryReportBack().sql: " + sub.toString() + ".");
		List<ReportHomeVO> lst = jdbc.query(sub.toString(), new RowMapper<ReportHomeVO>() {
			public ReportHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportHomeVO vo = new ReportHomeVO();
				vo.setMonth(rs.getString("month"));
				vo.setCapitalBack(rs.getInt("capital"));
				vo.setInterestBack(rs.getDouble("interest"));
				vo.setFee(rs.getDouble("fee"));
				return vo;
			}
		});
		
		//当查出的结果仅为一条时
		ReportHomeVO vo = null;
		if (lst != null && lst.size() == 1) {
			vo = lst.get(0);
		}
		
		return vo;
	}

	@Override
	public ReportHomeVO queryReportUncollected(String month) throws Exception {
		String sql = null;
		if (StringUtils.isBlank(month)) {
			sql = sqlUncollectedSite.replace(sqlUncollectedTmp, sqlUncollectedDefaultMonth)
					.replace(sqlUncollectedTmp2, sqlUncollectedDefaultMonth2);
		} else {
			sql = sqlUncollectedSite.replace(sqlUncollectedTmp, "'" + month + "'")
					.replace(sqlUncollectedTmp2, "'" + month + "'");
		}
		logger.debug("queryReportUncollected().sql: " + sql + ".");
		List<ReportHomeVO> lst = jdbc.query(sql, new RowMapper<ReportHomeVO>() {
			public ReportHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportHomeVO vo = new ReportHomeVO();
				vo.setCapitalUncollected(rs.getInt("capital"));
				vo.setInterestUncollected(rs.getDouble("interest"));
				vo.setFeeUncollected(rs.getDouble("fee"));
				return vo;
			}
		});
		
		//当查出的结果仅为一条时
		ReportHomeVO vo = null;
		if (lst != null && lst.size() == 1) {
			vo = lst.get(0);
		}
		
		return vo;
	}

	@Override
	public int queryReportRegisterNew(String month) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(month)) {
			sub.append(sqlRegisterNew).append(" ").append(sqlRegisterNewDefaultMonth);
		} else {
			sub.append(sqlRegisterNew).append(" '").append(month).append("'");
		}
		logger.debug("queryReportRegisterNew().sql: " + sub.toString() + ".");
		List<Integer> lst = jdbc.query(sub.toString(), new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("num");
			}
		});
		
		//当查出的结果仅为一条时
		int n = 0;
		if (lst != null && lst.size() == 1) {
			n = lst.get(0);
		}
		
		return n;
	}

	@Override
	public int queryReportRegisterSum() throws Exception {
		logger.debug("queryReportRegisterSum().sql: " + sqlRegisterSum + ".");
		List<Integer> lst = jdbc.query(sqlRegisterSum, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("num");
			}
		});
		
		//当查出的结果仅为一条时
		int n = 0;
		if (lst != null && lst.size() == 1) {
			n = lst.get(0);
		}
		
		return n;
	}

}
