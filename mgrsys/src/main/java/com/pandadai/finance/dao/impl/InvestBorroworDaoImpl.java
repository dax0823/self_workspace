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
import com.pandadai.finance.dao.IInvestBorroworDao;
import com.pandadai.finance.vo.InvestBorroworDetailVO;
import com.pandadai.finance.vo.InvestBorroworVO;
import com.pandadai.finance.vo.InvestCustomerInfoVO;

/**
 * @author 仵作
 * 2014-9-7 下午9:38:59
 */
public class InvestBorroworDaoImpl implements IInvestBorroworDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlBorrowor;
	private String sqlBorroworDetail;
	
	/**
	 * @param sqlBorroworDetail the sqlBorroworDetail to set
	 */
	public void setSqlBorroworDetail(String sqlBorroworDetail) {
		this.sqlBorroworDetail = sqlBorroworDetail;
	}
	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	/**
	 * @param sqlBorrowor the sqlBorrowor to set
	 */
	public void setSqlBorrowor(String sqlBorrowor) {
		this.sqlBorrowor = sqlBorrowor;
	}
	
	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestBorroworDao#queryInvestBorroworList()
	 */
	@Override
	public List<InvestBorroworVO> queryInvestBorroworList() throws Exception {
		logger.debug("queryInvestBorroworList().sql: " + sqlBorrowor + ".");
		List<InvestBorroworVO> lst = jdbc.query(sqlBorrowor, new RowMapper<InvestBorroworVO>() {
			public InvestBorroworVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestBorroworVO vo = new InvestBorroworVO();
				vo.setId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setSumBorrow(rs.getFloat("sum_borrow"));
				vo.setSumReward(rs.getFloat("sum_reward"));
				vo.setSumInterest(rs.getFloat("sum_interest"));
				vo.setSumFee(rs.getFloat("sum_fee"));
				return vo;
			}
		});
		return lst;
	}
	
	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestBorroworDao#queryInvestBorroworDetailList(java.lang.String)
	 */
	@Override
	public List<InvestBorroworDetailVO> queryInvestBorroworDetailList(
			String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(userId)) {
			sub.append(" and m.id = ").append(userId).append(" ");
		}
		String sql = sqlBorroworDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryInvestBorroworDetailList().sql: " + sql + ".");
		
		List<InvestBorroworDetailVO> lst = jdbc.query(sql, new RowMapper<InvestBorroworDetailVO>() {
			public InvestBorroworDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestBorroworDetailVO vo = new InvestBorroworDetailVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setBorrowId(rs.getString("bid"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setDuration(rs.getInt("borrow_duration"));
				vo.setBorrowTime(rs.getString("borrow_time"));
				vo.setBorrowInterest(rs.getFloat("borrow_interest"));
				vo.setBorrowFee(rs.getFloat("borrow_fee"));
				vo.setRecentlyTime(rs.getString("recently_time"));
				return vo;
			}
		});
		return lst;
	}
}
