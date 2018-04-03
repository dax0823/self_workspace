package com.pandadai.common.dao;

import java.util.List;

import com.pandadai.bean.AdminUserBean;
import com.pandadai.common.vo.AdminUserVO;

public interface IAdminDao {
//		public void insert(UserVO uservo);
//		public void delete(int id);
//		public void update(UserVO uservo);
//		public UserVO select(int id);
//		public List find();
//		public UserVO selectByName(String name,String pwd);
	
	/**
	 * 根据管理员信息查询数据
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public List<AdminUserVO> query(AdminUserBean userInfo) throws Exception;
	
	/**
	 * 获取全体管理员数据
	 * @return
	 * @throws Exception
	 */
	public List<AdminUserVO> query() throws Exception;

	/**
	 * 根据管理员登录账号、密码、口令查询账号
	 * @param username：登录账号
	 * @param pwd：密码
	 * @param userword：登录口令
	 * @return
	 * @throws Exception
	 */
	public List<AdminUserVO> queryByCondition(String username, String pwd, String userword) throws Exception;
}
