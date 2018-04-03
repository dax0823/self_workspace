/**
 * 
 */
package com.pandadai.biz.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.biz.dao.IBizLotteryLogDao;
import com.pandadai.biz.vo.BizLotteryLogVO;
import com.pandadai.common.utils.Constants;

/**
 * @author 仵作
 * 2014-12-3 下午1:44:15
 */
public class BizLotteryLogDaoImpl implements IBizLotteryLogDao {
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	
	private String sqlLotteryLog;
	private String sqlLotteryLogDefaultCondition;

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlLotteryLogDefaultCondition the sqlLotteryLogDefaultCondition to set
	 */
	public void setSqlLotteryLogDefaultCondition(
			String sqlLotteryLogDefaultCondition) {
		this.sqlLotteryLogDefaultCondition = sqlLotteryLogDefaultCondition;
	}

	/**
	 * @param sqlLotteryLog the sqlLotteryLog to set
	 */
	public void setSqlLotteryLog(String sqlLotteryLog) {
		this.sqlLotteryLog = sqlLotteryLog;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.biz.dao.IBizHomeDao#queryIntegralLotteryLog(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<BizLotteryLogVO> queryIntegralLotteryLog(String userName,
			String realName, String name, String startDate, String endDate) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlLotteryLogDefaultCondition).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(ml.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(ml.add_time), '%Y-%m-%d') ");
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			sub.append(" and mm.user_name like '%" + userName + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			sub.append(" and mmi.real_name like '%" + realName + "%' ");
		}
		if (StringUtils.isNotBlank(name)) {	//抽奖奖品类型，如“购物卡”、“京东”、“话费”之类
			sub.append(" and ml.name like '%" + name + "%' ");
		}
		
		String sql = sqlLotteryLog.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryIntegralLotteryLog().sql: " + sql + ".");
		
		List<BizLotteryLogVO> lst = jdbc.query(sql, new RowMapper<BizLotteryLogVO>() {
			public BizLotteryLogVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizLotteryLogVO vo = new BizLotteryLogVO();
				vo.setId(rs.getInt("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMobile(rs.getString("user_phone"));
				vo.setName(rs.getString("name"));
				vo.setInfo(rs.getString("info"));
				vo.setLotteryTime(rs.getString("lottery_time"));
				return vo;
			}
		});
		return lst;
	}
}
