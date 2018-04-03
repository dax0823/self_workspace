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
import com.pandadai.finance.dao.IInpourReviseDao;
import com.pandadai.finance.vo.InpourReviseVO;

/**
 * @author 仵作
 * 2014-8-30 下午11:54:04
 */
public class InpourReviseDaoImpl implements IInpourReviseDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlRevise;
	private String sqlReviseDefaultDuring;
	
	/**
	 * @param sqlRevise the sqlRevise to set
	 */
	public void setSqlRevise(String sqlRevise) {
		this.sqlRevise = sqlRevise;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlReviseDefaultDuring the sqlReviseDefaultDuring to set
	 */
	public void setSqlReviseDefaultDuring(String sqlReviseDefaultDuring) {
		this.sqlReviseDefaultDuring = sqlReviseDefaultDuring;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IFinanceReviseDao#queryReviseList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<InpourReviseVO> queryReviseList(String startDate,
			String endDate) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlReviseDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(mm.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(mm.add_time), '%Y-%m-%d') ");
			}
		}
		
		String sql = sqlRevise.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryReviseList().sql: " + sql + ".");
		
		List<InpourReviseVO> lst = jdbc.query(sql, new RowMapper<InpourReviseVO>() {
			public InpourReviseVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourReviseVO vo = new InpourReviseVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setReviseTime(rs.getString("revise_time"));
				vo.setReviseMoney(rs.getFloat("affect_money"));
				vo.setReason(rs.getString("info"));
				return vo;
			}
		});
		return lst;
	}

}
