package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IInpourDetailDao;
import com.pandadai.finance.vo.InpourDetailVO;

/**
 * 
 * @author 仵作
 *
 */
public class InpourDetailDaoImpl implements IInpourDetailDao {
	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlInpour;
	private String sqlInpourDefaultDuring;
	private String sqlInpourParamBaofoo;
	private String sqlInpourParamEasypay;
	private String sqlInpourParamOff;
	private String sqlInpourCustomer;

	public void setSqlInpourCustomer(String sqlInpourCustomer) {
		this.sqlInpourCustomer = sqlInpourCustomer;
	}

	public void setSqlInpourParamOff(String sqlInpourParamOff) {
		this.sqlInpourParamOff = sqlInpourParamOff;
	}

	public void setSqlInpourDefaultDuring(String sqlInpourDefaultDuring) {
		this.sqlInpourDefaultDuring = sqlInpourDefaultDuring;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setSqlInpour(String sqlInpour) {
		this.sqlInpour = sqlInpour;
	}

	public void setSqlInpourParamBaofoo(String sqlInpourParamBaofoo) {
		this.sqlInpourParamBaofoo = sqlInpourParamBaofoo;
	}

	public void setSqlInpourParamEasypay(String sqlInpourParamEasypay) {
		this.sqlInpourParamEasypay = sqlInpourParamEasypay;
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
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(p.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(p.add_time), '%Y-%m-%d') ");
			}
		}
		
		//充值类型
		if (StringUtils.isNotBlank(way) && !StringUtils.trim(way).equalsIgnoreCase("all")) {
			sub.append(" and ").append(" way = '").append(way).append("' ");
		}
		return sub.toString();
	}
	
	/**
	 * 拼接客户单笔查询明细的 sql 条件
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @param way
	 * @param defaultCondition
	 * @return
	 */
	private String createCusSqlCondition(String id, String startDate, String endDate, String way, String defaultCondition) {
		return createSqlCondition(startDate,  endDate, way, sqlInpourDefaultDuring) + " and uid = " + id + " ";
	}
	
	/**
	 * 拼接客户某天的单笔查询明细的 sql 条件
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @param way
	 * @param defaultCondition
	 * @return
	 */
	private String createCusDaySqlCondition(String id, String inpourTime, String way, String defaultCondition) {
		StringBuffer sub = new StringBuffer();
		//未设置时间范围，默认查询最后 7 天
		if (StringUtils.isBlank(inpourTime)) {
			sub.append(" and DATE_FORMAT(FROM_UNIXTIME(add_time),'%Y-%m-%d') = curdate() ");
		} else {
			sub.append(" and DATE_FORMAT(FROM_UNIXTIME(add_time),'%Y-%m-%d') = '").append(inpourTime).append("' ");
		}
		
		//充值类型
		if (StringUtils.isNotBlank(way) && !StringUtils.trim(way).equalsIgnoreCase("all")) {
			sub.append(" and ").append(" way = '").append(way).append("' ");
		}
		
		//用户 id
		if (StringUtils.isNotBlank(id)) {
			sub.append(" and ").append(" uid = '").append(id).append("' ");
		}
		return sub.toString();
	}
	
	@Override
	public List<InpourDetailVO> queryInpourAll(String startDate, String endDate)
			throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createSqlCondition(startDate,  endDate, null, sqlInpourDefaultDuring));
		logger.debug("queryInpourAll().sqlInpour: " + sql + ".");
		
		List<InpourDetailVO> lst = jdbc.query(sql, new RowMapper<InpourDetailVO>() {
			public InpourDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourDetailVO vo = new InpourDetailVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setUserName(rs.getString("user_name"));
				vo.setUserId(rs.getString("uid"));
				vo.setSummy(rs.getFloat("summy"));
				vo.setWay(rs.getString("way"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourDetailVO> queryInpourByBaofoo(String startDate,
			String endDate) throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createSqlCondition(startDate,  endDate, sqlInpourParamBaofoo, sqlInpourDefaultDuring));
		logger.debug("queryInpourByBaofoo().sqlInpour: " + sql + ".");
		
		List<InpourDetailVO> lst = jdbc.query(sql, new RowMapper<InpourDetailVO>() {
			public InpourDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourDetailVO vo = new InpourDetailVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setUserName(rs.getString("user_name"));
				vo.setUserId(rs.getString("uid"));
				vo.setSummy(rs.getFloat("summy"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourDetailVO> queryInpourByEasypay(String startDate,
			String endDate) throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createSqlCondition(startDate,  endDate, sqlInpourParamEasypay, sqlInpourDefaultDuring));
		logger.debug("queryInpourByEasypay().sqlInpour: " + sql + ".");
		
		List<InpourDetailVO> lst = jdbc.query(sql, new RowMapper<InpourDetailVO>() {
			public InpourDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourDetailVO vo = new InpourDetailVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setUserName(rs.getString("user_name"));
				vo.setUserId(rs.getString("uid"));
				vo.setSummy(rs.getFloat("summy"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourDetailVO> queryInpourByOff(String startDate,
			String endDate) throws Exception {
		String sql = sqlInpour.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createSqlCondition(startDate,  endDate, sqlInpourParamOff, sqlInpourDefaultDuring));
		logger.debug("queryInpourByoff().sqlInpour: " + sql + ".");
		
		List<InpourDetailVO> lst = jdbc.query(sql, new RowMapper<InpourDetailVO>() {
			public InpourDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourDetailVO vo = new InpourDetailVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setUserName(rs.getString("user_name"));
				vo.setUserId(rs.getString("uid"));
				vo.setSummy(rs.getFloat("summy"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourDetailVO> queryInpourCustomer(String id,
			String startDate, String endDate, String way) throws Exception {
		String sql = sqlInpourCustomer.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createCusSqlCondition(id, startDate,  endDate, way, sqlInpourDefaultDuring));
		logger.debug("queryInpourCustomer().sqlInpourCustomer: " + sql + ".");
		
		List<InpourDetailVO> lst = jdbc.query(sql, new RowMapper<InpourDetailVO>() {
			public InpourDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourDetailVO vo = new InpourDetailVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setUserName(rs.getString("user_name"));
				vo.setWay(rs.getString("way"));
				vo.setMoney(rs.getFloat("money"));
				return vo;
			}
		});
		return lst;
	}

	@Override
	public List<InpourDetailVO> queryInpourCustomer(String id,
			String inpourTime, String way) throws Exception {
		String sql = sqlInpourCustomer.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, 
				createCusDaySqlCondition(id, inpourTime, way, sqlInpourDefaultDuring));
		logger.debug("queryInpourCustomer().sqlInpourCustomer: " + sql + ".");
		
		List<InpourDetailVO> lst = jdbc.query(sql, new RowMapper<InpourDetailVO>() {
			public InpourDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourDetailVO vo = new InpourDetailVO();
				vo.setInpourTime(rs.getString("inpour_time"));
				vo.setUserName(rs.getString("user_name"));
				vo.setWay(rs.getString("way"));
				vo.setMoney(rs.getFloat("money"));
				return vo;
			}
		});
		return lst;
	}
}
