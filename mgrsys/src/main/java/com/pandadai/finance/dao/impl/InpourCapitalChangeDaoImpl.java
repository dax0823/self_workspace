package com.pandadai.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pandadai.common.utils.Constants;
import com.pandadai.finance.dao.IInpourCapitalChangeDao;
import com.pandadai.finance.vo.InpourCapitalChangeVO;

public class InpourCapitalChangeDaoImpl implements IInpourCapitalChangeDao {
	Logger logger = Logger.getLogger(this.getClass());

	Map<String, String> typeMap = new HashMap<String, String>();
	
	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlCapitalChange;
	private String sqlCapitalChangeDefaultDuring;
	private String typeName;

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlCapitalChange the sqlCapitalChange to set
	 */
	public void setSqlCapitalChange(String sqlCapitalChange) {
		this.sqlCapitalChange = sqlCapitalChange;
	}

	/**
	 * @param sqlCapitalChangeDefaultDuring the sqlCapitalChangeDefaultDuring to set
	 */
	public void setSqlCapitalChangeDefaultDuring(
			String sqlCapitalChangeDefaultDuring) {
		this.sqlCapitalChangeDefaultDuring = sqlCapitalChangeDefaultDuring;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public List<InpourCapitalChangeVO> queryCapitalChangeList()
			throws Exception {
		return queryCapitalChangeList(null, null, null, null);
	}

	@Override
	public List<InpourCapitalChangeVO> queryCapitalChangeList(String userName,
			String realName, String startDate, String endDate) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlCapitalChangeDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('")
						.append(startDate)
						.append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(mml.add_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('")
						.append(endDate)
						.append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(mml.add_time), '%Y-%m-%d') ");
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			sub.append(" and mm.user_name like '%" + userName + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			sub.append(" and mmi.real_name like '%" + realName + "%' ");
		}

		//读取类型映射
		if (typeMap.isEmpty()) {
			if (typeName.trim().length() > 0) {
				String[] typeNames = typeName.split(";");
				for (int i=0; i<typeNames.length; i++) {
					if (typeNames[i].trim().length() > 0) {
						String[] keyValue = typeNames[i].split(":");
						typeMap.put(keyValue[0], keyValue[1]);
					}
				}
			}
		}
		
		String sql = sqlCapitalChange.replace(
				Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryCapitalChangeList().sql: " + sql + ".");
		List<InpourCapitalChangeVO> lst = jdbc.query(sql, new RowMapper<InpourCapitalChangeVO>() {
					public InpourCapitalChangeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						InpourCapitalChangeVO vo = new InpourCapitalChangeVO();
						vo.setUserId(rs.getString("id"));
						vo.setUserName(rs.getString("user_name"));
//						vo.setRealName(rs.getString("real_name"));
						vo.setType(rs.getString("type"));
						//将类型编码映射成文字描述
						vo.setTime(rs.getString("time"));
						vo.setTypeName(typeMap.get(vo.getType()));
						vo.setAffectMoney(rs.getFloat("affect_money"));
						vo.setAccountMoney(rs.getFloat("account_money"));
						vo.setCollectMoney(rs.getFloat("collect_money"));
						vo.setFreezeMoney(rs.getFloat("freeze_money"));
						vo.setTargetUid(rs.getString("target_uid"));
						vo.setTargetUid(rs.getString("target_uname"));
						vo.setInfo(rs.getString("info"));
						return vo;
					}
				});
		return lst;
	}

}
