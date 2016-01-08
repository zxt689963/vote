package com.ok8.common.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * JdbcBaseDao 辅助类
 * @author caiwl
 */
class JdbcBaseUtil {

	/**
	 * 关闭 {@link Statement}、{@link ResultSet} 等 JDBC SQL 对象
	 * @param st : {@link Statement}
	 * @param rs : {@link ResultSet}
	 */
	public static void closeSqlObject(Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close(); // RowSet is included !!
			} catch (SQLException e) {
			}
			rs = null;
		}
		if (st != null) {
			try {
				st.close(); // PreparedStatement and CallableStatement are included !!
			} catch (SQLException e) {
			}
			st = null;
		}
	}

	/**
	 * 设置预编译参数
	 * @param pst  : {@link PreparedStatement}
	 * @param args : 预编译参数
	 * @throws SQLException
	 */
	public static void setParameters(PreparedStatement pst, Object... args)
			throws SQLException {
		for (int i = 0, len = args.length; i < len; i++) {
			Object arg = args[i];
			if (arg == null || "".equals(arg)) {
				pst.setNull(i + 1, Types.NULL);
			} else {
				pst.setObject(i + 1, arg);
			}
		}
	}
	
	public static void buildFields(ResultSetMetaData rsmd, String[] fields) throws SQLException {
		for (int i = 0, len = fields.length; i < len; i++) {
			fields[i] = rsmd.getColumnLabel(i + 1);
		}
	}

}
