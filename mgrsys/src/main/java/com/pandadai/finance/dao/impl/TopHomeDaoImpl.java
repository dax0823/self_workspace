package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.ITopHomeDao;
import com.pandadai.finance.vo.InvestRemindVO;
import com.pandadai.finance.vo.TopBorrowVO;
import com.pandadai.finance.vo.TopInpourVO;
import com.pandadai.finance.vo.TopIntegralVO;
import com.pandadai.finance.vo.TopInvestedVO;
import com.pandadai.finance.vo.TopUncollectedVO;
import com.pandadai.finance.vo.TopWithdrawVO;

public class TopHomeDaoImpl implements ITopHomeDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlInvested;
	private String sqlUncollected;
	private String sqlInpour;
	private String sqlWithdraw;
	private String sqlBorrow;
	private String sqlIntegral;
	
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public void setSqlIntegral(String sqlIntegral) {
		this.sqlIntegral = sqlIntegral;
	}

	public void setSqlBorrow(String sqlBorrow) {
		this.sqlBorrow = sqlBorrow;
	}

	public void setSqlInvested(String sqlInvested) {
		this.sqlInvested = sqlInvested;
	}

	public void setSqlUncollected(String sqlUncollected) {
		this.sqlUncollected = sqlUncollected;
	}
	
	public void setSqlInpour(String sqlInpour) {
		this.sqlInpour = sqlInpour;
	}

	public void setSqlWithdraw(String sqlWithdraw) {
		this.sqlWithdraw = sqlWithdraw;
	}

	@Override
	public List<TopUncollectedVO> queryTopUncollected(int rankNum)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (rankNum == 0) {
			sub.append(sqlUncollected).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_10);
		} else {
			sub.append(sqlUncollected).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM).append(rankNum);
		}
		logger.debug("queryTopUncollected().sql: " + sub.toString() + ".");
		List<TopUncollectedVO> lst = jdbc.query(sub.toString(), new RowMapper<TopUncollectedVO>() {
			int i = 1;	//排名顺序
			
			public TopUncollectedVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TopUncollectedVO vo = new TopUncollectedVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMoney(rs.getFloat("money_collect"));
				vo.setRank(i++);
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<TopInvestedVO> queryTopInvested(int rankNum) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (rankNum == 0) {
			sub.append(sqlInvested).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_10);
		} else {
			sub.append(sqlInvested).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM).append(rankNum);
		}
		logger.debug("queryTopInvested().sql: " + sub.toString() + ".");
		List<TopInvestedVO> lst = jdbc.query(sub.toString(), new RowMapper<TopInvestedVO>() {
			int i = 1;	//排名顺序
			
			public TopInvestedVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TopInvestedVO vo = new TopInvestedVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMoney(rs.getFloat("sum_invest"));
				vo.setRank(i++);
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<TopInpourVO> queryTopInpour(int rankNum) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (rankNum == 0) {
			sub.append(sqlInpour).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_10);
		} else {
			sub.append(sqlInpour).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM).append(rankNum);
		}
		logger.debug("queryTopInpour().sql: " + sub.toString() + ".");
		List<TopInpourVO> lst = jdbc.query(sub.toString(), new RowMapper<TopInpourVO>() {
			int i = 1;	//排名顺序
			
			public TopInpourVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TopInpourVO vo = new TopInpourVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMoney(rs.getFloat("sum_money"));
				vo.setRank(i++);
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<TopWithdrawVO> queryTopWithdraw(int rankNum) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (rankNum == 0) {
			sub.append(sqlWithdraw).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_10);
		} else {
			sub.append(sqlWithdraw).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM).append(rankNum);
		}
		logger.debug("queryTopWithdraw().sql: " + sub.toString() + ".");
		List<TopWithdrawVO> lst = jdbc.query(sub.toString(), new RowMapper<TopWithdrawVO>() {
			int i = 1;	//排名顺序
			
			public TopWithdrawVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TopWithdrawVO vo = new TopWithdrawVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMoney(rs.getFloat("sum_money"));
				vo.setRank(i++);
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<TopBorrowVO> queryTopBorrow(int rankNum) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (rankNum == 0) {
			sub.append(sqlBorrow).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_10);
		} else {
			sub.append(sqlBorrow).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM).append(rankNum);
		}
		logger.debug("queryTopBorrow().sql: " + sub.toString() + ".");
		List<TopBorrowVO> lst = jdbc.query(sub.toString(), new RowMapper<TopBorrowVO>() {
			int i = 1;	//排名顺序
			
			public TopBorrowVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TopBorrowVO vo = new TopBorrowVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMoney(rs.getFloat("sum_money"));
				vo.setRank(i++);
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<TopIntegralVO> queryTopIntegral(int rankNum) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (rankNum == 0) {
			sub.append(sqlIntegral).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_10);
		} else {
			sub.append(sqlIntegral).append(" ").append(Constants.SQL_DEFAULT_CONDITION_LIMIT_NUM_PARAM).append(rankNum);
		}
		logger.debug("queryTopIntegral().sql: " + sub.toString() + ".");
		List<TopIntegralVO> lst = jdbc.query(sub.toString(), new RowMapper<TopIntegralVO>() {
			int i = 1;	//排名顺序
			
			public TopIntegralVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TopIntegralVO vo = new TopIntegralVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setNum(rs.getInt("active_integral"));
				vo.setRank(i++);
				return vo;
			}
		});
		return lst;
	}

}
