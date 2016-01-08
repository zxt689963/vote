package com.ok8.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManipulation {

	private Connection con; 
	private Statement stmt;
	private PreparedStatement pStmt;	//预编译

	private ResultSet rs; 

	private static DBConnectionManager manager = DBConnectionManager.getInstance();
//	private int maxDbLine = 100000000;
//	private int maxHashSize = 1000;

	private String sql = "";		//用于存储需要执行的sql
	private long btime = 0;			//开始时间
	private long etime = 0;			//结束时间
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * 根据租户id和禁用自动提交模式，进行初始化连接<br>
	 * 注意：该构造器必须配合commit和rollback方法使用
	 * @param tenantID 租户id
	 * @param autoCommit true为启用自动提交模式；false为禁用
	 */
	public DBManipulation(String tenantID, boolean autoCommit){
		con = manager.getConnection(tenantID);
		if (con != null) {
			try {
				con.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				con = null;
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据tenantID,判断不同的数据源,进行初始化连接。SaaS
	 * add by caod on 2008-04-21
	 */
	public DBManipulation(String tenantID){
		con=manager.getConnection(tenantID);
	}
	public DBManipulation(boolean autoCommit){
		con = manager.getConnection();
		if (con != null) {
			try {
				con.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				con = null;
				e.printStackTrace();
			}
		}
	}
	/** 
	 * 根据poolName,判断不同的数据源,进行初始化连接
	 */
	public DBManipulation(){
		con=manager.getConnection();
	}

	/** 
	 * 释放连接
	 */
	public void disConnect(){
		try {
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
			if(pStmt != null){
				pStmt.close();
				pStmt = null;
			}
			if (con != null){//如果连接不为空,关闭数据库连接
				manager.freeConnection(con);
				con = null;
			}
		} catch (SQLException e) {
			System.out.println("Error!!!!!! DBManipulation-->disConnect() catch SQLException!");
			e.printStackTrace();
		}
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 执行查询sql add by caod on 2013-10-15
	 * @param strSql
	 * @return ResultSet
	 */
	public ResultSet executeSQL(String strSql) throws SQLException {
		try {
			btime = System.currentTimeMillis();	//开始时间			
			stmt = con.createStatement();
			rs = stmt.executeQuery(strSql);
		} catch (SQLException e) {
			System.out.println("Error!!!!!! DBManipulation-->executeSQL(String strSql) catch SQLException： "+ e.getMessage());
			throw e;
		} finally {
			//这里不需要释放rs、stmt，因为在disConnect()中统一释放了。 
			etime = System.currentTimeMillis(); 
			if((etime - btime)>5000) System.out.println("Warning!!!!!! SQL语句用时:"+(etime - btime)+" strSql="+strSql);
		}
		
		return rs;
	}
	
	/**
	 * Update表
	 * @param strSql
	 * @return
	 * @throws SQLException
	 */
	public boolean executeUpdate(String strSql) throws SQLException {
		boolean resultFlag = true;
		try {
			btime = System.currentTimeMillis();	//开始时间			
			stmt = con.createStatement();
			stmt.executeUpdate(strSql);
			
		} catch (SQLException e) {
			resultFlag = false;
			System.out.println("Error!!!!!! DBManipulation-->executeUpdate(String strSql) catch SQLException： "+ e.getMessage());
			throw e;
		} finally {
			//这里不需要释放rs、stmt，因为在disConnect()中统一释放了。 
			etime = System.currentTimeMillis(); 
			if((etime - btime)>5000) System.out.println("Warning!!!!!! SQL语句用时:"+(etime - btime)+" strSql="+strSql);
		}
		return resultFlag;
	}
	
	/**
	 * 批量更新sql
	 * @param alSql
	 * @return boolean
	 */
	public boolean executeUpdate(ArrayList<String> alSql) throws SQLException {
		boolean resultFlag = false;
		int num = 0;
		Statement stmt2 = null;
		String strSqli="";
		try {
			//设置为非自动提交,改为false
			con.setAutoCommit(false);
			stmt2 = con.createStatement();
			for (int i = 0; i < alSql.size(); i++,num++) {
				strSqli = alSql.get(i);
				stmt2.executeUpdate(strSqli);
			}
			con.commit();
			resultFlag = true;
		} catch (SQLException se1) {
			//产生的任何SQL异常都需要进行回滚,并设置为系统默认的提交方式,即为TRUE   
            if (con != null) con.rollback();
            
            System.out.println("Error!!!!!! DBManipulation-->execUpdate(ArrayList alSql) catch SQLException： 第"+num+"条sql: "+strSqli);
            se1.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error!!!!!! DBManipulation-->execUpdate(ArrayList alSql) catch Exception： 第"+num+"条sql: "+strSqli);
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			if(stmt2!=null) stmt2.close();
		}
		return resultFlag;
	}
	
	/**
	 * 预编译sql语句对象
	 * @param strSql
	 * @return
	 */
	public PreparedStatement prepareSQL(String strSql) throws SQLException{
		try {
			sql = strSql;
			btime = System.currentTimeMillis();	//开始时间
			pStmt = con.prepareStatement(strSql);
		} catch (SQLException e) {
			System.out.println("Error!!!!!! DBManipulation-->prepareSQL() catch SQLException："+ e.getMessage());
			throw e;
		} finally {
		}
		return pStmt;
	}
	
	/**
	 * 执行查询sql(预编译)
	 * @return ResultSet
	 */
	public ResultSet executePrepareQuery() throws SQLException {
		try {
			rs = pStmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Error!!!!!! DBManipulation-->executePrepareQuery() catch SQLException："+ e.getMessage());
			throw e;
		} finally {
			//这里不需要释放rs、stmt，因为在disConnect()中统一释放了。 
			etime = System.currentTimeMillis(); 
			if((etime - btime)>5000) System.out.println("Warning!!!!!! SQL语句用时:"+(etime - btime)+" strSql="+this.sql);
		}
		
		return rs;
	}
	
	/**
	 * Update sql(预编译)
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean executePrepareUpdate() throws SQLException {
		boolean resultFlag = true;
		try {
			pStmt.executeUpdate();			
		} catch (SQLException e) {
			resultFlag = false;
			System.out.println("Error!!!!!! DBManipulation-->executePrepareUpdate() catch SQLException： "+ e.getMessage());
			throw e;
		} finally {
			//这里不需要释放rs、stmt，因为在disConnect()中统一释放了。 
			etime = System.currentTimeMillis(); 
			if((etime - btime)>5000) System.out.println("Warning!!!!!! SQL语句用时:"+(etime - btime)+" strSql="+this.sql);
		}
		return resultFlag;
	}
	
	/**
	 * 自动提交模式禁用状态下，手动提交当前数据库连接下的所有数据库操作<br>
	 * 数据库访问错误发生时，手动回滚当前数据库连接下的所有数据库操作<br>
	 * 手动提交或手动回滚执行完毕，必须手动启用当前数据库连接的自动提交模式
	 * @return 所有数据库操作提交成功，返回true
	 * @throws SQLException 如果数据库访问错误发生，抛出异常
	 */
	public boolean commit() throws SQLException {
		try {
			con.commit();
			return true;
		} catch (SQLException e) {
			throw e;
		} finally {
			con.setAutoCommit(true);
		}
	}
	
	/**
	 * 自动提交模式禁用状态下，手动提交当前数据库连接下的所有数据库操作<br>
	 * 数据库访问错误发生时，手动回滚当前数据库连接下的所有数据库操作<br>
	 * 手动提交或手动回滚执行完毕，必须手动启用当前数据库连接的自动提交模式
	 * @return 数据库操作提交失败，返回true
	 */
	public void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 禁用自动提交模式后，在放回数据库连接池之前，必须启用自动提交模式
	 */
	public void enableAutoCommit() {
		try {
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

