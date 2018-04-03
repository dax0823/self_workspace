/**
 * 
 */
package com.pandadai.adjust.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.adjust.dao.IAdjustIntegralDao;
import com.pandadai.adjust.vo.AdjustIntegralCustomerVO;
import com.pandadai.adjust.vo.AdjustIntegralLogVO;
import com.pandadai.common.utils.Constants;

/**
 * @author 仵作
 * 2014-10-9 下午7:08:45
 */
public class AdjustIntegralDaoImpl implements IAdjustIntegralDao {
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlIntegralCustomer;
	private String sqlIntegralLog;
	private String sqlUpdateIntegralByUserId;
	private String sqlInsertIntegralLogByUserId;
	
	private String sqlIntegralCustomerDefaultCondition;
	
	public void setSqlIntegralCustomerDefaultCondition(
			String sqlIntegralCustomerDefaultCondition) {
		this.sqlIntegralCustomerDefaultCondition = sqlIntegralCustomerDefaultCondition;
	}

	/**
	 * @param sqlUpdateIntegralByUserId the sqlUpdateIntegralByUserId to set
	 */
	public void setSqlUpdateIntegralByUserId(String sqlUpdateIntegralByUserId) {
		this.sqlUpdateIntegralByUserId = sqlUpdateIntegralByUserId;
	}

	/**
	 * @param sqlInsertIntegralLogByUserId the sqlInsertIntegralLogByUserId to set
	 */
	public void setSqlInsertIntegralLogByUserId(String sqlInsertIntegralLogByUserId) {
		this.sqlInsertIntegralLogByUserId = sqlInsertIntegralLogByUserId;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlIntegralCustomer the sqlIntegralCustomer to set
	 */
	public void setSqlIntegralCustomer(String sqlIntegralCustomer) {
		this.sqlIntegralCustomer = sqlIntegralCustomer;
	}

	/**
	 * @param sqlIntegralLog the sqlIntegralLog to set
	 */
	public void setSqlIntegralLog(String sqlIntegralLog) {
		this.sqlIntegralLog = sqlIntegralLog;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.adjust.dao.IAdjustIntegralDao#queryIntegralCustomer()
	 */
	@Override
	public List<AdjustIntegralCustomerVO> queryIntegralCustomer(String userName)
			throws Exception {
		return queryIntegralCustomer(null, null, userName);
	}
	
	@Override
	public List<AdjustIntegralCustomerVO> queryIntegralCustomer(String startDate, String endDate, String userName)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlIntegralCustomerDefaultCondition).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(m.reg_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(m.reg_time), '%Y-%m-%d') ");
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			sub.append(" and user_name like '%" + userName + "%' ");
		}
		
		String sql = sqlIntegralCustomer.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		
//		System.out.println("queryIntegralCustomer().sql: " + sql + ".");
		logger.debug("queryIntegralCustomer().sql: " + sql + ".");
		List<AdjustIntegralCustomerVO> lst = jdbc.query(sql, new RowMapper<AdjustIntegralCustomerVO>() {
			public AdjustIntegralCustomerVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdjustIntegralCustomerVO vo = new AdjustIntegralCustomerVO(); 
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setIntegral(rs.getInt("integral"));
				vo.setActiveIntegral(rs.getInt("active_integral"));
				vo.setRegTime(rs.getString("reg_time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.adjust.dao.IAdjustIntegralDao#queryIntegralLog(java.lang.String)
	 */
	@Override
	public List<AdjustIntegralLogVO> queryIntegralLog(String userId)
			throws Exception {
		String sql = sqlIntegralLog.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and uid = " + userId + " ");
		logger.debug("queryIntegralLog().sql: " + sql + ".");
		List<AdjustIntegralLogVO> lst = jdbc.query(sql, new RowMapper<AdjustIntegralLogVO>() {
			public AdjustIntegralLogVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdjustIntegralLogVO vo = new AdjustIntegralLogVO(); 
				vo.setType(rs.getInt("type"));
				vo.setAffectIntegral(rs.getInt("affect_integral"));
				vo.setActiveIntegral(rs.getInt("active_integral"));
				vo.setAccountIntegral(rs.getInt("account_integral"));
				vo.setInfo(rs.getString("info"));
				vo.setTime(rs.getString("time"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.adjust.dao.IAdjustIntegralDao#updateIntegralbyUserId(java.lang.String)
	 */
	@Override
	public void updateIntegralbyUserId(String userId, int adjNum) throws Exception {
		String sql = sqlUpdateIntegralByUserId.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and id = " + userId + " ");
//		sql = sql.replace(Constants.SQL_DEFAULT_CONDITION_MULTIPLY_1, String.valueOf(adjNum));
		sql = sql.replace(Constants.SQL_DEFAULT_CONDITION_MULTIPLY_1, "+ (" + String.valueOf(adjNum) + ")");
		logger.debug("updateIntegralbyUserId().sql: " + sql + ".");
		jdbc.execute(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.pandadai.adjust.dao.IAdjustIntegralDao#getIntegralInfoByUserId(java.lang.String)
	 */
	@Override
	public AdjustIntegralCustomerVO getIntegralInfoByUserId(String userId)
			throws Exception {
		String sql = sqlIntegralCustomer.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and id = " + userId + " ");
		logger.debug("getIntegralInfoByUserId().sql: " + sql + ".");
		List<AdjustIntegralCustomerVO> lst = jdbc.query(sql, new RowMapper<AdjustIntegralCustomerVO>() {
			public AdjustIntegralCustomerVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdjustIntegralCustomerVO vo = new AdjustIntegralCustomerVO(); 
//				vo.setId(rs.getInt("id"));
//				vo.setUserName(rs.getString("user_name"));
				vo.setIntegral(rs.getInt("integral"));
				vo.setActiveIntegral(rs.getInt("active_integral"));
//				vo.setRegTime(rs.getString("reg_time"));
				return vo;
			}
		});
		AdjustIntegralCustomerVO vo = null;
		if (lst != null && lst.size() == 1)
			vo = lst.get(0);
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.adjust.dao.IAdjustIntegralDao#insertIntegralLogByUserId(java.lang.String)
	 */
	@Override
	public void insertIntegralLogByUserId(String userId, int adjNum, int activeIntegral, int integral, String describe, String ip) throws Exception {
		String sql = sqlInsertIntegralLogByUserId.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, " and uid = " + userId + " ");
//		insert into LZH_MEMBER_INTEGRALLOG values(id, 44, 4, 1, 2594, 3824, 'mgrsys系统，人工修改投标积分', NOW(), '220.231.48.246') -->
//		insert into LZH_MEMBER_INTEGRALLOG values(id, ?, 4, ?, ?, ?, ?, unix_timestamp(NOW()), ?);
		sql = sql.replaceFirst("\\?", userId);
		sql = sql.replaceFirst("\\?", String.valueOf(adjNum));
		sql = sql.replaceFirst("\\?", String.valueOf(activeIntegral));
		sql = sql.replaceFirst("\\?", String.valueOf(integral));
		sql = sql.replaceFirst("\\?", "'" + describe + "'");
		sql = sql.replaceFirst("\\?", "'" + ip + "'");
		logger.debug("updateIntegralbyUserId().sql: " + sql + ".");
		jdbc.execute(sql);
	}


}
