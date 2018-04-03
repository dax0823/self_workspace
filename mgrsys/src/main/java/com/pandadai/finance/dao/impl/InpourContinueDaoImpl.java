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
import com.pandadai.finance.dao.IInpourContinueDao;
import com.pandadai.finance.vo.InpourContinueVO;

/**
 * @author 仵作
 * 2014-8-31 上午11:54:37
 */
public class InpourContinueDaoImpl implements IInpourContinueDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlContinue;
	private String sqlContinueDefaultDuring;
	
	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlContinue the sqlContinue to set
	 */
	public void setSqlContinue(String sqlContinue) {
		this.sqlContinue = sqlContinue;
	}

	/**
	 * @param sqlContinueDefaultDuring the sqlContinueDefaultDuring to set
	 */
	public void setSqlContinueDefaultDuring(String sqlContinueDefaultDuring) {
		this.sqlContinueDefaultDuring = sqlContinueDefaultDuring;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IFinanceContinueDao#queryContinueList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<InpourContinueVO> queryContinueList(String startDate,
			String endDate) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlContinueDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(tr.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(tr.add_time), '%Y-%m-%d') ");
			}
		}
		
		String sql = sqlContinue.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryContinueList().sql: " + sql + ".");
		
		List<InpourContinueVO> lst = jdbc.query(sql, new RowMapper<InpourContinueVO>() {
			public InpourContinueVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourContinueVO vo = new InpourContinueVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setContinueTime(rs.getString("continue_time"));
				vo.setRewardMoney(rs.getFloat("reward_money"));
				vo.setInvestMoney(rs.getFloat("invest_money"));
				return vo;
			}
		});
		return lst;
	}

}
