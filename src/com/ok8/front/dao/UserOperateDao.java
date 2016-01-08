package com.ok8.front.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.ok8.common.db.DBManipulation;
import com.ok8.common.db.JdbcBaseDao;

/**
 * tb_usr_operate(帐号表)
 */
public class UserOperateDao extends JdbcBaseDao {
	
	public UserOperateDao(DBManipulation dbConn) {
		super(dbConn);
	}
	
	/**
	 * 登录校验，校验成功，带出员工信息和管理员标志
	 * @author caiwl
	 */
	public HashMap<String, Object> checkLogin(String user, String password) throws Exception {
		String[] fields = {"user","tenantId","password","staffCode","staffName","adminFlag"};
//		String sql = "select" +
//				" uo.c_usr," +
//				" uo.c_tenantid," +
//				" uo.c_pwd," +
//				" s.c_stfcod," +
//				" s.c_stfnam," +
//				" s.c_adminflg" +
//				" from tb_usr_operate uo" +
//				" left join (select rss.c_tenantid,rss.c_stfcod,rss.c_stfnam,r.c_adminflg" +
//					" from (select rs.c_tenantid,rs.c_stfcod,s.c_stfnam,rs.c_role_id" +
//						" from tb_role_stf rs,tb_stf s" +
//						" where s.c_tenantid = rs.c_tenantid" +
//						" and s.c_mrk = 'Y'" +
//						" and s.c_stfcod = rs.c_stfcod) rss" +
//					" left join tb_role r" +
//					" on (r.c_tenantid = rss.c_tenantid and r.c_role_id = rss.c_role_id and r.c_mrk = 'Y')) s" +
//				" on (uo.c_tenantid = s.c_tenantid and uo.c_stfcod = s.c_stfcod)" +
//				" where uo.c_usestalbl = 'U'" +
//				" and uo.c_type = 'I'" + // 内部员工账号
//				" and uo.c_usr = ?" +
//				" and binary uo.c_pwd = ?";
		String sql = "select" +
				" u.c_usr," +
				" u.c_tenantid," +
				" u.c_pwd," +
				" u.c_stfcod," +
				" u.c_stfnam," +
				" rsr.c_adminflg" +
				" from tb_usr_operate u" +
				" left join (select" +
					" rs.c_tenantid," +
					" rs.c_stfcod," +
					" group_concat(distinct r.c_adminflg) c_adminflg" +
					" from tb_role_stf rs, tb_role r" +
					" where rs.c_tenantid = r.c_tenantid" +
					" and rs.c_role_id = r.c_role_id" +
					" and r.c_mrk = 'Y'" + // 角色启用
					" group by rs.c_tenantid, rs.c_stfcod) rsr" +
				" on (u.c_tenantid = rsr.c_tenantid" +
				" and u.c_stfcod = rsr.c_stfcod)" +
				" where u.c_usr = ?" +
				" and binary u.c_pwd = ?" +
				" and u.c_usestalbl = 'U'" + // 可用
				" and u.c_type = 'I'"; // 内部账号
		return readMap(fields, sql, user, password);
	}
	
	/**
	 * 更新登录时间
	 * @author caiwl
	 */
	public boolean updateLoginTime(String user) throws Exception {
		String sql = "update tb_usr_operate set c_lastlogin = ? where c_usr = ?";
		return update(sql, new Date(), user);
	}
	
	/**
	 * 重置密码
	 * @author caiwl
	 */
	public boolean resetPassword(String user, String password) throws Exception {
		String sql = "update tb_usr_operate set c_pwd = ? where c_usr = ?";
		return update(sql, password, user);
	}
	
	/**
	 * 员工首次绑定账号时，判断账号是否重复
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int readSameUserCount(String user) throws SQLException {
		String sql = "select count(1) from tb_usr_operate where c_usr = ?";
		return readCount(sql, user);
	}
	
	/**
	 * 员工非首次绑定账号时，判断账号是否重复
	 * @param user
	 * @param staffCode
	 * @return
	 * @throws SQLException
	 */
	public int readSameUserCount(String user, String staffCode) throws SQLException {
		String sql = "select count(1) from tb_usr_operate where c_usr = ? and c_stfcod = ?";
		return readCount(sql, user, staffCode);
	}
	
	public boolean delete(String type, String staffCode) throws SQLException {
		String sql = "delete from tb_usr_operate where c_type = ? and c_stfcod = ?";
		return update(sql, type, staffCode);
	}
	
	public boolean deleteBatch(String type, String[] staffCodes) throws SQLException {
		String sql = "delete from tb_usr_operate where c_type = ? and c_stfcod = ?";
		ArrayList<Object[]> argsList = new ArrayList<Object[]>();
		for (int i = 0, len = staffCodes.length; i < len; i++) {
			Object[] args = {type, staffCodes[i]};
			argsList.add(args);
		}
		return updateBatch(sql, argsList);
	}
	
	public boolean create(HashMap<String, Object> params) throws SQLException {
		String sql = "insert into tb_usr_operate (" +
				"c_usr," +
				"c_tenantid," +
				"c_pwd," +
				"c_stfcod," +
				"c_stfnam," +
				"c_usestalbl," +
				"c_lastlogin," +
				"c_usrstadat," +
				"c_usrstpdat," +
				"c_idpof," +
				"c_type," +
				"c_qy_weixin_usr" +
				") values (?,?,?,?,?,?,?,?,?,?,?,?)";
		String useStateCode = (String) params.get("useStateCode");
		Object[] args = {
				params.get("user"),
				params.get("tenantId"),
				params.get("password"),
				params.get("staffCode"),
				params.get("staffName"),
				useStateCode,
				null,
				("U".equals(useStateCode) ? new Date() : null),
				("F".equals(useStateCode) ? new Date() : null),
				params.get("periodOfValidity"),
				params.get("type"),
				params.get("weixinCompanyUser")
		};
		return update(sql, args);
	}
	
	public boolean update(HashMap<String, Object> params) throws SQLException {
		String sql = "update tb_usr_operate set" +
				" c_pwd = ?," +
				" c_stfnam = ?," +
				" c_usestalbl = ?," +
				" c_usrstadat = ?," +
				" c_usrstpdat = ?," +
				" c_idpof = ?," +
				" c_qy_weixin_usr = ?" +
				" where c_usr = ?";
		String useStateCode = (String) params.get("useStateCode");
		Object[] args = {
				params.get("password"),
				params.get("staffName"),
				useStateCode,
				("U".equals(useStateCode) ? new Date() : null),
				("F".equals(useStateCode) ? new Date() : null),
				params.get("periodOfValidity"),
				params.get("weixinCompanyUser"),
				params.get("user")
		};
		return update(sql, args);
	}
	
	public HashMap<String, Object> readByStaffCode(String type, String staffCode) throws SQLException {
		String[] fields = {"user","password","useStateCode","weixinCompanyUser"};
		String sql = "select c_usr ,c_pwd ,c_usestalbl,c_qy_weixin_usr " +
				" from tb_usr_operate where c_type = ? and c_stfcod = ?";
		return readMap(fields, sql, type, staffCode);
	}
	
	/**
	 * 回收订单指派回收员时，读取回收员的微信企业账号
	 * @param staffCode
	 * @return
	 * @throws SQLException
	 */
	public String readWeixinCompanyUser(String staffCode) throws SQLException {
		String sql = "select c_qy_weixin_usr from tb_usr_operate" +
				" where c_usestalbl = 'U' and c_type = 'I' and c_stfcod = ?";
		return (String) readObject(sql, staffCode);
	}

}
