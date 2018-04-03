package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IInpourRewardDao;
import com.pandadai.finance.vo.InpourRewardCusVO;
import com.pandadai.finance.vo.InpourRewardVO;

/**
 * 
 * @author 仵作
 *
 */
public class InpourRewardDaoImpl implements IInpourRewardDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlReward;
	private String sqlRewardDefaultDuring;
	private String sqlRewardCus;
	
	public void setSqlRewardCus(String sqlRewardCus) {
		this.sqlRewardCus = sqlRewardCus;
	}

	public void setSqlRewardDefaultDuring(String sqlRewardDefaultDuring) {
		this.sqlRewardDefaultDuring = sqlRewardDefaultDuring;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setSqlReward(String sqlReward) {
		this.sqlReward = sqlReward;
	}

	/**
	 * 拼接明细的 sql 条件
	 * @param startDate
	 * @param endDate
	 * @param way
	 * @return
	 */
//	private String createSqlCondition(String id, String startDate, String endDate, String defaultCondition) {
//		StringBuffer sub = new StringBuffer();
//		//未设置时间范围，默认查询最后 7 天
//		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
//			sub.append(" ").append(defaultCondition).append(" ");
//		} else {
//			if (StringUtils.isNotBlank(startDate)) {
////				'2014-07-18' <= FROM_UNIXTIME(add_time)
//				sub.append(" and '").append(startDate).append("' <= FROM_UNIXTIME(add_time) ");
//			}
//			if (StringUtils.isNotBlank(endDate)) {
//				sub.append(" and '").append(endDate).append("' >= FROM_UNIXTIME(add_time) ");
//			}
//		}
//		
//		//充值类型
//		if (StringUtils.isNotBlank(id)) {
//			sub.append(" and ").append(" p.uid = ").append(id).append(" ");
//		}
//		return sub.toString();
//	}

	@Override
	public List<InpourRewardVO> queryRewardList(String startDate,
			String endDate) {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlRewardDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m') <= DATE_FORMAT(FROM_UNIXTIME(p.add_time), '%Y-%m') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m') >= DATE_FORMAT(FROM_UNIXTIME(p.add_time), '%Y-%m') ");
			}
		}
		
		String sql = sqlReward.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryRewardList().sql: " + sql + ".");
		
		List<InpourRewardVO> lst = jdbc.query(sql, new RowMapper<InpourRewardVO>() {
			public InpourRewardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourRewardVO vo = new InpourRewardVO();
				vo.setUserId(rs.getString("uid"));
				vo.setUserName(rs.getString("user_name"));
				vo.setMoneySum(rs.getFloat("money_sum"));
				vo.setRewardMonth(rs.getString("reward_month"));
				vo.setRewardSum(rs.getFloat("reward_sum"));
				return vo;
			}
		});
		return lst;
	}
	
	@Override
	public List<InpourRewardCusVO> queryCusRewardList(String id) {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isNotBlank(id)) {
			sub.append(" and ").append(" p.uid = ").append(id).append(" ");
		}
		String sql = sqlRewardCus.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryCusRewardList().sql: " + sql + ".");
		
		List<InpourRewardCusVO> lst = jdbc.query(sql, new RowMapper<InpourRewardCusVO>() {
			public InpourRewardCusVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourRewardCusVO vo = new InpourRewardCusVO();
				vo.setUserId(rs.getString("uid"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRewardTime(rs.getString("reward_time"));
				vo.setMoney(rs.getFloat("money"));
				if (vo.getMoney() >= Constants.OFF_REWARD_5000 && vo.getMoney() < Constants.OFF_REWARD_20000) {
					vo.setRate(Constants.OFF_REWARD_RATE_015_STR);
					vo.setRewardMoney(vo.getMoney() * Constants.OFF_REWARD_RATE_015);
				} else if  (vo.getMoney() >= Constants.OFF_REWARD_20000 && vo.getMoney() < Constants.OFF_REWARD_50000) {
					vo.setRate(Constants.OFF_REWARD_RATE_02_STR);
					vo.setRewardMoney(vo.getMoney() * Constants.OFF_REWARD_RATE_02);
				} else if  (vo.getMoney() >= Constants.OFF_REWARD_50000) {
					vo.setRate(Constants.OFF_REWARD_RATE_025_STR);
					vo.setRewardMoney(vo.getMoney() * Constants.OFF_REWARD_RATE_025);
				} else {
					//低于 5k 不奖励
				}
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourRewardCusVO> queryCusRewardList(String id,
			String startDate, String endDate) {
		StringBuffer sub = new StringBuffer();
		//未设置时间范围，默认查询最近三个月
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlRewardDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT(").append(startDate).append(",'%Y-%m') <= DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT(").append(endDate).append(",'%Y-%m') >= DATE_FORMAT(FROM_UNIXTIME(add_time), '%Y-%m') ");
			}
		}
		if (StringUtils.isNotBlank(id)) {
			sub.append(" and ").append(" p.uid = ").append(id).append(" ");
		}
		
		String sql = sqlRewardCus.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryCusRewardList().sql: " + sql + ".");
		
		List<InpourRewardCusVO> lst = jdbc.query(sql, new RowMapper<InpourRewardCusVO>() {
			public InpourRewardCusVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourRewardCusVO vo = new InpourRewardCusVO();
				vo.setUserId(rs.getString("uid"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRewardTime(rs.getString("reward_time"));
				vo.setMoney(rs.getFloat("money"));
				if (vo.getMoney() >= Constants.OFF_REWARD_5000 && vo.getMoney() < Constants.OFF_REWARD_20000) {
					vo.setRate(Constants.OFF_REWARD_RATE_015_STR);
					vo.setRewardMoney(vo.getMoney() * Constants.OFF_REWARD_RATE_015);
				} else if  (vo.getMoney() >= Constants.OFF_REWARD_20000 && vo.getMoney() < Constants.OFF_REWARD_50000) {
					vo.setRate(Constants.OFF_REWARD_RATE_02_STR);
					vo.setRewardMoney(vo.getMoney() * Constants.OFF_REWARD_RATE_02);
				} else if  (vo.getMoney() >= Constants.OFF_REWARD_50000) {
					vo.setRate(Constants.OFF_REWARD_RATE_025_STR);
					vo.setRewardMoney(vo.getMoney() * Constants.OFF_REWARD_RATE_025);
				} else {
					//低于 5k 不奖励
				}
				return vo;
			}
		});
		return lst;
	}
}
