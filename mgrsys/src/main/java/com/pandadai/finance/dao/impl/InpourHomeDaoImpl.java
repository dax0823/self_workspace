package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.apache.commons.lang3.StringUtils;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IInpourHomeDao;
import com.pandadai.finance.vo.InpourHomeInpourVO;
import com.pandadai.finance.vo.InpourHomeVO;

/***
 * 财务处理实现
 * @author 仵作
 *
 */
public class InpourHomeDaoImpl implements IInpourHomeDao {
	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
//	总览列表（三行）
	private String sqlCurrDate;
	private String sqlLast7Day;
	private String sqlLastMonth;
//	总览明细导出文件 sql
	private String sqlEveryDay;
	private String sqlEveryDayDefaultCondition;
	private String sqlEveryMonth;
	private String sqlEveryMonthDefaultCondition;
//	查询总览（当天、最近 7 天、最近一个月）充值情况
	private String sqlInpour;
	private String sqlInpourCurrDate;
	private String sqlInpourLast7Day;
	private String sqlInpourLastMonth;
	
	public void setSqlInpour(String sqlInpour) {
		this.sqlInpour = sqlInpour;
	}

	public void setSqlInpourCurrDate(String sqlInpourCurrDate) {
		this.sqlInpourCurrDate = sqlInpourCurrDate;
	}

	public void setSqlInpourLast7Day(String sqlInpourLast7Day) {
		this.sqlInpourLast7Day = sqlInpourLast7Day;
	}

	public void setSqlInpourLastMonth(String sqlInpourLastMonth) {
		this.sqlInpourLastMonth = sqlInpourLastMonth;
	}

	public void setSqlEveryDayDefaultCondition(String sqlEveryDayDefaultCondition) {
		this.sqlEveryDayDefaultCondition = sqlEveryDayDefaultCondition;
	}

	public void setSqlEveryMonthDefaultCondition(
			String sqlEveryMonthDefaultCondition) {
		this.sqlEveryMonthDefaultCondition = sqlEveryMonthDefaultCondition;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public void setSqlCurrDate(String sqlCurrDate) {
		this.sqlCurrDate = sqlCurrDate;
	}

	public void setSqlLast7Day(String sqlLast7Day) {
		this.sqlLast7Day = sqlLast7Day;
	}

	public void setSqlLastMonth(String sqlLastMonth) {
		this.sqlLastMonth = sqlLastMonth;
	}
	
	public void setSqlEveryDay(String sqlEveryDay) {
		this.sqlEveryDay = sqlEveryDay;
	}

	public void setSqlEveryMonth(String sqlEveryMonth) {
		this.sqlEveryMonth = sqlEveryMonth;
	}

	/**
	 * 拼接明细的 sql 条件
	 * @param startDate
	 * @param endDate
	 * @param way
	 * @return
	 */
	private String createSqlCondition(String startDate, String endDate, String way, String defaultCondition) {
		StringBuffer sub = new StringBuffer();
		//未设置时间范围，默认查询最后 7 天
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(defaultCondition).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
//				'2014-07-18' <= FROM_UNIXTIME(add_time)
				sub.append(" and '").append(startDate).append("' <= FROM_UNIXTIME(add_time) ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and '").append(endDate).append("' >= FROM_UNIXTIME(add_time) ");
			}
		}
		
		//充值类型
		if (StringUtils.isNotBlank(way) && !StringUtils.trim(way).equalsIgnoreCase("all")) {
			sub.append(" and ").append(" way = '").append(way).append("' ");
		}
		return sub.toString();
	}
	
	@Override
	public List<Float> sumCurrDate() throws Exception {
		logger.debug("sumCurrDate().sqlCurrDate: " + sqlCurrDate + ".");
		List<Float> lst = jdbc.query(sqlCurrDate, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum(money)");
			}
		});
		return lst;
	}

	@Override
	public List<Float> sumLast7Day() throws Exception {
		logger.debug("sumLast7Day().sqlLast7Day: " + sqlLast7Day + ".");
		List<Float> lst = jdbc.query(sqlLast7Day, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum(money)");
			}
		});
		return lst;
	}

	@Override
	public List<Float> sumLastMonth() throws Exception {
		logger.debug("sumLastMonth().sqlLastMonth: " + sqlLastMonth + ".");
		List<Float> lst = jdbc.query(sqlLastMonth, new RowMapper<Float>() {
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum(money)");
			}
		});
		return lst;
	}

	@Override
	public List<InpourHomeVO> sumEveryDay(String startDate, String endDate, String way) throws Exception {
		StringBuffer sub = new StringBuffer();
		//未设置时间范围，默认查询最后 7 天
//		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
//			sub.append(b)
////			sqlEveryDay.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlEveryDayDefaultCondition);
//		} else {
////			sqlEveryDay.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlEveryDayDefaultCondition);
//		}
//		if (StringUtils.isNotBlank(way)) {
//		}
		String sql = sqlEveryDay.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createSqlCondition(startDate,  endDate, way, sqlEveryDayDefaultCondition));
		logger.debug("sumEveryDay().sqlEveryDay: " + sql + ".");
		
		List<InpourHomeVO> lst = jdbc.query(sql, new RowMapper<InpourHomeVO>() {
			public InpourHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourHomeVO vo = new InpourHomeVO();
				vo.setDuring(rs.getString("add_time"));
				vo.setBaofoo(rs.getFloat("baofoo"));
				vo.setEasypay(rs.getFloat("easypay"));
				vo.setOff(rs.getFloat("off"));
				vo.setTotal(rs.getFloat("off") + rs.getFloat("easypay") + rs.getFloat("baofoo"));
				return vo;
			}
		});
		return lst;
	}
	
	@Override
	public List<InpourHomeVO> sumEveryMonth(String startDate, String endDate, String way) throws Exception {
		String sql = sqlEveryMonth.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createSqlCondition(startDate,  endDate, way, sqlEveryMonthDefaultCondition));
		logger.debug("sumEveryMonth().sqlEveryMonth: " + sql + ".");
		
		List<InpourHomeVO> lst = jdbc.query(sql, new RowMapper<InpourHomeVO>() {
			public InpourHomeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourHomeVO vo = new InpourHomeVO();
				vo.setDuring(rs.getString("add_time"));
				vo.setBaofoo(rs.getFloat("baofoo"));
				vo.setEasypay(rs.getFloat("easypay"));
				vo.setOff(rs.getFloat("off"));
				vo.setTotal(rs.getFloat("off") + rs.getFloat("easypay") + rs.getFloat("baofoo"));
				return vo;
			}
		});
		return lst;
	}
	
	@Override
	public List<InpourHomeInpourVO> queryInpourCurrDate() throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlInpourCurrDate);
		logger.debug("queryInpourCurrDate().sqlInpour: " + sql + ".");
		
		List<InpourHomeInpourVO> lst = jdbc.query(sql, new RowMapper<InpourHomeInpourVO>() {
			public InpourHomeInpourVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourHomeInpourVO vo = new InpourHomeInpourVO();
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setWay(rs.getString("way"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourHomeInpourVO> queryInpourLast7Day() throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlInpourLast7Day);
		logger.debug("queryInpourLast7Day().sqlInpour: " + sql + ".");
		
		List<InpourHomeInpourVO> lst = jdbc.query(sql, new RowMapper<InpourHomeInpourVO>() {
			public InpourHomeInpourVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourHomeInpourVO vo = new InpourHomeInpourVO();
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setWay(rs.getString("way"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourHomeInpourVO> queryInpourLastMonth() throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sqlInpourLastMonth);
		logger.debug("queryInpourLast7Month().sqlInpour: " + sql + ".");
		
		List<InpourHomeInpourVO> lst = jdbc.query(sql, new RowMapper<InpourHomeInpourVO>() {
			public InpourHomeInpourVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourHomeInpourVO vo = new InpourHomeInpourVO();
				vo.setUserName(rs.getString("user_name"));
				vo.setMoney(rs.getFloat("money"));
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setWay(rs.getString("way"));
				return vo;
			}
		});
		return lst;
	}
}
