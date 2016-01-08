package com.ok8.common.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ok8.common.utils.Result;

/**
 * 使用 JDBC 操作数据库的基础类
 * @author caiwl
 */
public abstract class JdbcBaseDao {
	
	/** 租户ID */
	protected String tenantId;

	/**
	 * 私有变量：数据库连接
	 */
	private DBManipulation dbConn;

	/**
	 * 构造器
	 * @param dbConn 数据库连接
	 */
	protected JdbcBaseDao(DBManipulation dbConn) {
		this.dbConn = dbConn;
	}

	/**
	 * 构造器
	 * @param dbConn 数据库连接
	 * @param tenantId 租户ID
	 */
	protected JdbcBaseDao(DBManipulation dbConn, String tenantId) {
		this.dbConn = dbConn;
		this.tenantId = tenantId;
	}

	/**
	 * 读取多条记录，建议：参数<=3个时可直接传递，>3个使用数组传递
	 * @param fields 属性名
	 * @param sql 查询语句
	 * @param args 查询参数
	 * @return 结果集
	 * @throws SQLException
	 */
	protected ArrayList<HashMap<String, Object>> readList(String[] fields, String sql,
			Object... args) throws SQLException {
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbConn.prepareSQL(sql);
			JdbcBaseUtil.setParameters(pst, args);
			rs = dbConn.executePrepareQuery();
			int len = fields.length;
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < len; i++) {
					Object value = rs.getObject(i + 1);
					map.put(fields[i], value == null ? "" : value);
				}
				result.add(map);
			}
			return result;
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, rs);
		}
	}

	/**
	 * 读取单条记录，建议：参数<=3个时可直接传递，>3个使用数组传递
	 * @param fields 属性名
	 * @param sql 查询语句
	 * @param args 查询参数
	 * @return 结果
	 * @throws SQLException
	 */
	protected HashMap<String, Object> readMap(String[] fields, String sql, Object... args)
			throws SQLException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbConn.prepareSQL(sql);
			JdbcBaseUtil.setParameters(pst, args);
			rs = dbConn.executePrepareQuery();
			int len = fields.length;
			if (rs.next()) {
				for (int i = 0; i < len; i++) {
					Object value = rs.getObject(i + 1);
					result.put(fields[i], value == null ? "" : value);
				}
			}
			return result;
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, rs);
		}
	}

	/**
	 * 读取单个字段
	 * @param sql 查询语句
	 * @param args 查询参数
	 * @return 字段值
	 * @throws SQLException
	 */
	protected Object readObject(String sql, Object... args) throws SQLException {
		Object result = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbConn.prepareSQL(sql);
			JdbcBaseUtil.setParameters(pst, args);
			rs = dbConn.executePrepareQuery();
			if (rs.next()) {
				result = rs.getObject(1);
			}
			return (result == null ? "" : result); // NULL需要转换为""
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, rs);
		}
	}

	/**
	 * 读取记录数
	 * @param sql 查询语句
	 * @param args 查询参数
	 * @return 字段值
	 * @throws SQLException
	 */
	protected int readCount(String sql, Object... args) throws SQLException {
		int count = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbConn.prepareSQL(sql);
			JdbcBaseUtil.setParameters(pst, args);
			rs = dbConn.executePrepareQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, rs);
		}
	}

	/**
	 * 更新单条记录
	 * @param sql 更新语句
	 * @param args 更新参数
	 * @return 更新成功，返回true
	 * @throws SQLException
	 */
	protected boolean update(String sql, Object... args) throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = dbConn.prepareSQL(sql);
			JdbcBaseUtil.setParameters(pst, args);
			return dbConn.executePrepareUpdate();
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, null);
		}
	}

	/**
	 * 更新多条记录（单参）<br>
	 * update t1 set c1 = c2 * c3 where cw1 = ?<br>
	 * delete from t1 where cw1 = ?
	 * @param sql 更新语句
	 * @param args 更新参数数组
	 * @return 更新成功，返回true
	 * @throws SQLException
	 */
	protected boolean updateBatch(String sql, Object[] args) throws SQLException {
		if (args == null || args.length == 0) {
			return false;
		}
		PreparedStatement pst = null;
		try {
			pst = dbConn.prepareSQL(sql);
			for (int i = 0, len = args.length; i < len; i++) {
				JdbcBaseUtil.setParameters(pst, args[i]);
				pst.addBatch();
			}
			pst.executeBatch();
			return true;
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, null);
		}
	}

	/**
	 * 更新多条记录
	 * @param sql 更新语句
	 * @param argsList 更新参数集
	 * @return 更新成功，返回true
	 * @throws SQLException
	 */
	protected boolean updateBatch(String sql, ArrayList<Object[]> argsList)
			throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = dbConn.prepareSQL(sql);
			for (int i = 0, size = argsList.size(); i < size; i++) {
				JdbcBaseUtil.setParameters(pst, argsList.get(i));
				pst.addBatch();
			}
			pst.executeBatch();
			return true;
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, null);
		}
	}
	
	/**
	 * 分页查询
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	protected HashMap<String, Object> paginate(String sql, int pageNumber, int pageSize,
			Object... args) throws SQLException {
		int total = 0;
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		String sqlCount = "select count(1) " + sql.substring(sql.indexOf("from"));
		String sqlList = sql + " limit ?,?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbConn.prepareSQL(sqlCount);
			JdbcBaseUtil.setParameters(pst, args);
			rs = dbConn.executePrepareQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			if (total == 0) {
				return Result.getPage(null, pageNumber, pageSize, 0, 0);
			}
			int totalPage = total / pageSize;
			if (total % pageSize != 0) {
				totalPage++;
			}
			pst = dbConn.prepareSQL(sqlList);
			JdbcBaseUtil.setParameters(pst, args, (pageNumber - 1) * pageSize, pageSize);
			rs = dbConn.executePrepareQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount(); 
			String[] fields = new String[count];
			JdbcBaseUtil.buildFields(rsmd, fields);
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {
					Object value = rs.getObject(i + 1);
					map.put(fields[i], value == null ? "" : value);
				}
				rows.add(map);
			}
			return Result.getPage(rows, pageNumber, pageSize, totalPage, total);
		} finally {
			JdbcBaseUtil.closeSqlObject(pst, rs);
		}
	}
	
}
