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
import com.pandadai.finance.dao.IInpourWithdrawDao;
import com.pandadai.finance.vo.InpourPendingWithdrawVO;
import com.pandadai.finance.vo.InpourWithdrawVO;

/**
 * @author 仵作
 * 2014-8-30 下午3:44:07
 */
public class InpourWithdrawDaoImpl implements IInpourWithdrawDao {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 配置注入
	 */
//	数据源
	private JdbcTemplate jdbc;
	private String sqlWithdraw;
	private String sqlWithdrawDefaultDuring;
	
	private String sqlPendingWithdraw;
	
	/**
	 * @param sqlPendingWithdraw the sqlPendingWithdraw to set
	 */
	public void setSqlPendingWithdraw(String sqlPendingWithdraw) {
		this.sqlPendingWithdraw = sqlPendingWithdraw;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setSqlWithdraw(String sqlWithdraw) {
		this.sqlWithdraw = sqlWithdraw;
	}

	public void setSqlWithdrawDefaultDuring(String sqlWithdrawDefaultDuring) {
		this.sqlWithdrawDefaultDuring = sqlWithdrawDefaultDuring;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IFinanceWithdrawDao#queryWithdrawList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<InpourWithdrawVO> queryWithdrawList(String startDate, String endDate, String type) throws Exception {
		StringBuffer sub = new StringBuffer();
		if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			sub.append(" ").append(sqlWithdrawDefaultDuring).append(" ");
		} else {
			if (StringUtils.isNotBlank(startDate)) {
				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(mw.add_time), '%Y-%m-%d') ");
//				sub.append(" and DATE_FORMAT('").append(startDate).append("','%Y-%m-%d') <= DATE_FORMAT(FROM_UNIXTIME(mw.deal_time), '%Y-%m-%d') ");
			}
			if (StringUtils.isNotBlank(endDate)) {
				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(mw.add_time), '%Y-%m-%d') ");
//				sub.append(" and DATE_FORMAT('").append(endDate).append("','%Y-%m-%d') >= DATE_FORMAT(FROM_UNIXTIME(mw.deal_time), '%Y-%m-%d') ");
			}
		}
		
		//匹配提现状态
		if (StringUtils.isNotBlank(type) && (type.equalsIgnoreCase("0") || type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2") || type.equalsIgnoreCase("3"))) {
			sub.append(" and mw.withdraw_status = " + type + " ");
		} else {	//默认为处理中
			sub.append(" and mw.withdraw_status = 1 ");
		}
		
		String sql = sqlWithdraw.replace(Constants.SQL_DEFAULT_CONDITION_1_EQ_1, sub.toString());
		logger.debug("queryWithdrawList().sql: " + sql + ".");
		
		List<InpourWithdrawVO> lst = jdbc.query(sql, new RowMapper<InpourWithdrawVO>() {
			public InpourWithdrawVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourWithdrawVO vo = new InpourWithdrawVO();
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setBankNum(rs.getString("bank_num"));
				vo.setBankProv(rs.getString("bank_province"));
				vo.setBankCity(rs.getString("bank_city"));
				vo.setBankAddr(rs.getString("bank_address"));
				vo.setBankName(rs.getString("bank_name"));
				vo.setWithdrawMoney(rs.getFloat("withdraw_money"));
				vo.setWithdrawFee(rs.getFloat("withdraw_fee"));

				vo.setWithdrawId(rs.getString("withdraw_id"));
				vo.setWithdrawTime(rs.getString("withdraw_time"));
				vo.setWithdrawStatus(rs.getString("withdraw_status"));
				vo.setSuccessMoney(rs.getFloat("success_money"));
				return vo;
			}
		});
		return lst;
	}

	/* (non-Javadoc)
	 * @see com.pandadai.finance.dao.IInpourWithdrawDao#queryPendingWithdrawList()
	 */
	@Override
	public List<InpourPendingWithdrawVO> queryPendingWithdrawList()
			throws Exception {
		logger.debug("queryPendingWithdrawList().sql: " + sqlPendingWithdraw + ".");
		
		List<InpourPendingWithdrawVO> lst = jdbc.query(sqlPendingWithdraw, new RowMapper<InpourPendingWithdrawVO>() {
			public InpourPendingWithdrawVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				InpourPendingWithdrawVO vo = new InpourPendingWithdrawVO();
				vo.setUserId(rs.getString("id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setWithdrawTime(rs.getString("withdraw_time"));
				vo.setWithdrawMoney(rs.getFloat("withdraw_money"));
				vo.setAccountMoney(rs.getFloat("account_money"));
				vo.setInpourMoney(rs.getFloat("inpour_money"));
				
//				-- 1. 免手续费金额 = 账户内余额（mmy.account_money + mmy.back_money + mmy.money_freeze） - 15天内充值金额
//				-- 		(账户内余额 - 15天内充值金额 < 0 时，免手续费金额为 0)
				float free = vo.getAccountMoney() - vo.getInpourMoney();
				if (free < 0) free = 0;
				vo.setFreeMoney(free);

//				-- 2. 手续费 = （申请提现金额 - 免手续费金额） * 0.005
//				-- 		(申请提现金额 - 免手续费金额 < 0 时，手续费为 0)
				float fee = Float.parseFloat(String.valueOf((vo.getWithdrawMoney() - free) * 0.005));
				if (fee < 0) fee = 0;
				vo.setFeeMoney(fee);

//				-- 3. 最终提现金 = 申请提现金额 - 手续费
				vo.setFinalMoney(vo.getWithdrawMoney() - fee);
				return vo;
			}
		});
		return lst;
	}

}
