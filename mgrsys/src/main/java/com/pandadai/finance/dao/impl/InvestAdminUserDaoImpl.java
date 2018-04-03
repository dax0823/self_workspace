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
import com.pandadai.finance.dao.IInvestAdminUserDao;
import com.pandadai.finance.vo.InvestAdminUserVO;
import com.pandadai.finance.vo.InvestCustomerVO;

/**
 * @author 仵作
 * 2014-9-5 上午9:51:17
 */
public class InvestAdminUserDaoImpl implements IInvestAdminUserDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlAdminUser;
	private String sqlAdminUserDefaultDuring;

	/**
	 * @param sqlAdminUserDefaultDuring the sqlAdminUserDefaultDuring to set
	 */
	public void setSqlAdminUserDefaultDuring(String sqlAdminUserDefaultDuring) {
		this.sqlAdminUserDefaultDuring = sqlAdminUserDefaultDuring;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlAdminUser the sqlAdminUser to set
	 */
	public void setSqlAdminUser(String sqlAdminUser) {
		this.sqlAdminUser = sqlAdminUser;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestAdminUserDao#queryAdminUserList()
	 */
	@Override
	public List<InvestAdminUserVO> queryAdminUserList(String startDate, String endDate) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlAdminUserDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(biv.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(biv.add_time), '%Y-%m-%d') ");
			}
		}
		String sql = sqlAdminUser.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryAdminUserList().sql: " + sql + ".");
		List<InvestAdminUserVO> lst = jdbc.query(sql, new RowMapper<InvestAdminUserVO>() {
			public InvestAdminUserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestAdminUserVO vo = new InvestAdminUserVO();
				vo.setAdminUserId(rs.getString("id"));
				vo.setAdminUserName(rs.getString("user_name"));
				vo.setAdminRealName(rs.getString("real_name"));
				vo.setIsKf(rs.getString("is_kf"));
//				vo.setIsBan(rs.getString("is_ban"));
				vo.setSumReward(rs.getFloat("sum_reward"));
				return vo;
			}
		});
		return lst;
	}
}
