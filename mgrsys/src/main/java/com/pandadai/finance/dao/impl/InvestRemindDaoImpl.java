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
import com.pandadai.finance.dao.IInvestRemindDao;
import com.pandadai.finance.vo.InvestAdminUserVO;
import com.pandadai.finance.vo.InvestRemindCapitalDetailVO;
import com.pandadai.finance.vo.InvestRemindCapitalVO;
import com.pandadai.finance.vo.InvestRemindDetailCustomerInfoVO;
import com.pandadai.finance.vo.InvestRemindInterestDetailVO;
import com.pandadai.finance.vo.InvestRemindInterestVO;
import com.pandadai.finance.vo.InvestRemindVO;

/**
 * @author 仵作
 * 2014-9-7 上午9:49:12
 */
public class InvestRemindDaoImpl implements IInvestRemindDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
	// 数据源
	private JdbcTemplate jdbc;
	private String sqlRemind;
	private String sqlRemindDefaultDuring;
	private String sqlRemindCapital;
	private String sqlRemindInterest;
	private String sqlRemindCapitalDetail;
	private String sqlRemindInterestDetail;
	private String sqlRemindDetailCustomerInfo;
	
	/**
	 * @param sqlRemindDetailCustomerInfo the sqlRemindDetailCustomerInfo to set
	 */
	public void setSqlRemindDetailCustomerInfo(String sqlRemindDetailCustomerInfo) {
		this.sqlRemindDetailCustomerInfo = sqlRemindDetailCustomerInfo;
	}

	/**
	 * @param sqlRemindCapitalDetail the sqlRemindCapitalDetail to set
	 */
	public void setSqlRemindCapitalDetail(String sqlRemindCapitalDetail) {
		this.sqlRemindCapitalDetail = sqlRemindCapitalDetail;
	}

	/**
	 * @param sqlRemindInterestDetail the sqlRemindInterestDetail to set
	 */
	public void setSqlRemindInterestDetail(String sqlRemindInterestDetail) {
		this.sqlRemindInterestDetail = sqlRemindInterestDetail;
	}

	/**
	 * @param sqlRemindCapital the sqlRemindCapital to set
	 */
	public void setSqlRemindCapital(String sqlRemindCapital) {
		this.sqlRemindCapital = sqlRemindCapital;
	}

	/**
	 * @param sqlRemindInterest the sqlRemindInterest to set
	 */
	public void setSqlRemindInterest(String sqlRemindInterest) {
		this.sqlRemindInterest = sqlRemindInterest;
	}

	/**
	 * @param jdbc the jdbc to set
	 */
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * @param sqlRemind the sqlRemind to set
	 */
	public void setSqlRemind(String sqlRemind) {
		this.sqlRemind = sqlRemind;
	}

	/**
	 * @param sqlRemindDefaultDuring the sqlRemindDefaultDuring to set
	 */
	public void setSqlRemindDefaultDuring(String sqlRemindDefaultDuring) {
		this.sqlRemindDefaultDuring = sqlRemindDefaultDuring;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestRemindDao#queryRemindInterestList(java.lang.String)
	 */
	@Override
	public List<InvestRemindVO> queryRemindList(String date)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(date)) {
			sub.append(" ").append(sqlRemindDefaultDuring).append(" ");
		} else {
			sub.append(" and DATE_FORMAT('").append(date).append("','%Y-%m') = DATE_FORMAT(FROM_UNIXTIME(id.deadline), '%Y-%m') ");
		}
		String sql = sqlRemind.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryRemindInterestList().sql: " + sql + ".");
		List<InvestRemindVO> lst = jdbc.query(sql, new RowMapper<InvestRemindVO>() {
			public InvestRemindVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestRemindVO vo = new InvestRemindVO();
				vo.setRemindTime(rs.getString("remind_time"));
				vo.setSumCapital(rs.getFloat("sum_capital"));
				vo.setSumInterest(rs.getFloat("sum_interest"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestRemindDao#queryRemindBorrowCapitalList(java.lang.String)
	 */
	@Override
	public List<InvestRemindCapitalVO> queryRemindBorrowCapitalOneDayList(String date)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(date)) {
			return null;
		} else {
			sub.append(" and DATE_FORMAT(FROM_UNIXTIME(idt.deadline), '%Y-%m-%d') = '" + date + "'");
		}
		String sql = sqlRemindCapital.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryRemindBorrowCapitalList().sql: " + sql + ".");
		List<InvestRemindCapitalVO> lst = jdbc.query(sql, new RowMapper<InvestRemindCapitalVO>() {
			public InvestRemindCapitalVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestRemindCapitalVO vo = new InvestRemindCapitalVO();
				vo.setId(rs.getString("id"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setBorrowUserid(rs.getString("borrow_uid"));
				vo.setBorrowUserName(rs.getString("user_name"));
				vo.setBorrowMoney(rs.getFloat("borrow_money"));
				vo.setBorrowInterest(rs.getFloat("borrow_interest"));
				vo.setBorrowInterestRate(rs.getFloat("borrow_interest_rate"));
				vo.setBorrowDuration(rs.getString("borrow_duration"));
				vo.setSumReceiveCapital(rs.getFloat("sum_receive_capital"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestRemindDao#queryRemindBorrowInterestList(java.lang.String)
	 */
	@Override
	public List<InvestRemindInterestVO> queryRemindBorrowInterestOneDayList(String date)
			throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(date)) {
			return null;
		} else {
			sub.append(" and DATE_FORMAT(FROM_UNIXTIME(idt.deadline), '%Y-%m-%d') = '" + date + "'");
		}
		String sql = sqlRemindInterest.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryRemindBorrowInterestList().sql: " + sql + ".");
		List<InvestRemindInterestVO> lst = jdbc.query(sql, new RowMapper<InvestRemindInterestVO>() {
			public InvestRemindInterestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestRemindInterestVO vo = new InvestRemindInterestVO();
				vo.setId(rs.getString("id"));
				vo.setBorrowName(rs.getString("borrow_name"));
				vo.setBorrowUserid(rs.getString("borrow_uid"));
				vo.setBorrowUserName(rs.getString("user_name"));
				vo.setBorrowMoney(rs.getFloat("borrow_money"));
				vo.setBorrowInterest(rs.getFloat("borrow_interest"));
				vo.setBorrowInterestRate(rs.getFloat("borrow_interest_rate"));
				vo.setBorrowDuration(rs.getString("borrow_duration"));
				vo.setSumReceiveInterest(rs.getFloat("sum_receive_interest"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestRemindDao#queryRemindBorrowCapitalDetailOneDayList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<InvestRemindCapitalDetailVO> queryRemindBorrowCapitalDetailOneDayList(
			String date, String borrowId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(date)) {
			return null;
		} else {
			sub.append(" and DATE_FORMAT(FROM_UNIXTIME(idt.deadline), '%Y-%m-%d') = '" + date + "'");
		}
		
		if (StringUtils.isBlank(borrowId)) {
			return null;
		} else {
			sub.append(" and idt.borrow_id = " + borrowId );
		}
		
		String sql = sqlRemindCapitalDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryRemindBorrowInterestList().sql: " + sql + ".");
		List<InvestRemindCapitalDetailVO> lst = jdbc.query(sql, new RowMapper<InvestRemindCapitalDetailVO>() {
			public InvestRemindCapitalDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestRemindCapitalDetailVO vo = new InvestRemindCapitalDetailVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setDeadline(rs.getString("deadline"));
				vo.setCapital(rs.getFloat("capital"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestRemindDao#queryRemindBorrowInterestDetailOneDayList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<InvestRemindInterestDetailVO> queryRemindBorrowInterestDetailOneDayList(
			String date, String borrowId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(date)) {
			return null;
		} else {
			sub.append(" and DATE_FORMAT(FROM_UNIXTIME(idt.deadline), '%Y-%m-%d') = '" + date + "'");
		}
		
		if (StringUtils.isBlank(borrowId)) {
			return null;
		} else {
			sub.append(" and idt.borrow_id = " + borrowId );
		}
		
		String sql = sqlRemindInterestDetail.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryRemindBorrowInterestList().sql: " + sql + ".");
		List<InvestRemindInterestDetailVO> lst = jdbc.query(sql, new RowMapper<InvestRemindInterestDetailVO>() {
			public InvestRemindInterestDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestRemindInterestDetailVO vo = new InvestRemindInterestDetailVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setDeadline(rs.getString("deadline"));
				vo.setInterest(rs.getFloat("interest"));
				vo.setSortOrder(rs.getInt("sort_order"));
				vo.setTotal(rs.getInt("total"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInvestRemindDao#queryRemindBorrowDetailCustomerInfo(java.lang.String)
	 */
	@Override
	public InvestRemindDetailCustomerInfoVO queryRemindBorrowDetailCustomerInfo(
			String userId) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(userId)) {
			return null;
		} else {
			sub.append(" and mm.id = " + userId );
		}
		
		String sql = sqlRemindDetailCustomerInfo.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("InvestRemindDetailCustomerInfoVO().sql: " + sql + ".");
		List<InvestRemindDetailCustomerInfoVO> lst = jdbc.query(sql, new RowMapper<InvestRemindDetailCustomerInfoVO>() {
			public InvestRemindDetailCustomerInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InvestRemindDetailCustomerInfoVO vo = new InvestRemindDetailCustomerInfoVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setMobile(rs.getString("user_phone"));
				vo.setEmail(rs.getString("user_email"));
				vo.setIDCard(rs.getString("idcard"));
				vo.setRegTime(rs.getString("reg_time"));
				vo.setUsableMoney(rs.getFloat("usable_money"));
				vo.setCollectMoney(rs.getFloat("money_collect"));
				return vo;
			}
		});
		
		InvestRemindDetailCustomerInfoVO vo = null;
		if (lst != null && lst.size() == 1) {
			vo = lst.get(0);
		}
		
		return vo;
	}
}
