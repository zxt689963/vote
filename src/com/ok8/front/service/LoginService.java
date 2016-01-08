package com.ok8.front.service;

import java.sql.SQLException;
import java.util.HashMap;

import com.ok8.common.db.DBManipulation;
import com.ok8.front.dao.UserOperateDao;

public class LoginService {
	
	/**
	 * @author zxt
	 */
	public HashMap<String, Object> login(HashMap<String, Object> params) {
		HashMap<String, Object> data = null;
		DBManipulation dbConn = null;
		try {
			String user = (String) params.get("user");
			String password = (String) params.get("password");
			dbConn = new DBManipulation(false);
			UserOperateDao userOperateDao = new UserOperateDao(dbConn);
			data = userOperateDao.checkLogin(user, password);
			dbConn.commit();
		} catch (SQLException e) {
			if (dbConn != null) {
				dbConn.rollback();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return data;
	}
	
}
