package com.pandadai.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.pandadai.bean.AdminUserBean;
import com.pandadai.common.dao.IAdminDao;
import com.pandadai.common.vo.AdminUserVO;

@Service
//public class LoginImpl extends JdbcDaoSupport implements ILogin {
public class AdminDaoImpl implements IAdminDao {
	Logger logger = Logger.getLogger(this.getClass());
	
//	@ Autowired
	private JdbcTemplate jdbc;

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUserVO> query() throws Exception {
		String sql = "select id, user_name, u_group_id, real_name, last_log_time, last_log_ip, is_kf, qq, phone "
				+ "from lzh_ausers ";
		logger.debug("query().sql: " + sql + ".");
		return jdbc.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminUserVO vo = new AdminUserVO();
				vo.setId(rs.getInt("id"));
				vo.setUsername(rs.getString("user_name"));
				vo.setRealName(rs.getString("real_name"));
				vo.setLastLogTime(rs.getLong("last_log_time"));
				vo.setLastLogIp(rs.getString("last_log_ip"));
				vo.setIsKf(rs.getInt("is_kf"));
				vo.setQq(rs.getString("qq"));
				vo.setPhone(rs.getString("phone"));
				return vo;
			}
		});
	}
	
	@Override
	public List<AdminUserVO> query(AdminUserBean userInfo) throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUserVO> queryByCondition(String username, String pwd,
			String userword) throws Exception {
		String sql = "select id, user_name, u_group_id, real_name, last_log_time, last_log_ip, is_kf, qq, phone "
				+ "from lzh_ausers "
				+ "where user_name = '" + username + "' and user_pass = '" + pwd + "' and user_word = '" + userword + "'";
//				+ "where user_pass = '" + pwd + "'";
		logger.debug("queryByCondition().sql: " + sql + ".");
		
		return jdbc.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminUserVO vo = new AdminUserVO();
				vo.setId(rs.getInt("id"));
				vo.setUsername(rs.getString("user_name"));
				// vo.setPwd(rs.getString("user_pass"));
				// vo.setUserword(rs.getString("user_word"));
				vo.setRealName(rs.getString("real_name"));
				vo.setLastLogTime(rs.getLong("last_log_time"));
				vo.setLastLogIp(rs.getString("last_log_ip"));
				vo.setIsKf(rs.getInt("is_kf"));
				vo.setQq(rs.getString("qq"));
				vo.setPhone(rs.getString("phone"));
				
				//管理员角色
				vo.setuGroupId(rs.getInt("u_group_id"));
				return vo;
			}
		});
	}

}
