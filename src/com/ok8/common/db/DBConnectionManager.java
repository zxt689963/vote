package com.ok8.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 管理类DBConnectionManager支持对一个或多个由属性文件定义的数据库连接
 * 池的访问.客户程序可以调用getInstance()方法访问本类的唯一实例.
 */
class DBConnectionManager {

	private static InitialContext initial;
	private static final String DEFAULT_POOL = "recycle";
	private static Map<String, DataSource> cacheMap    = new ConcurrentHashMap<String, DataSource>(); 	/* 数据源缓存 */
	private static ReentrantLock sychroLock = new ReentrantLock();
	private static DBConnectionManager instance = new DBConnectionManager(); 							/* 唯一实例 */

	/** @see singleton 模式 */  
	public static DBConnectionManager getInstance() {
		return instance;
	}

	private DBConnectionManager(){}

	/**
	 * 根据tenantID，获得一个可用的(空闲的)连接。
	 */
	public Connection getConnection(String tenantID) {
		Connection con = null; 
		String c_connectionPool = ""; // 连接池名称

//		tenantID     = Utils.nullToSpace(tenantID);
		
		// 根据tenantID获取连接池名称
		//c_connectionPool = EntAsp.getConnectionPool(tenantID);
		//c_connectionPool = Utils.nullToSpace(c_connectionPool); 
		c_connectionPool = "recycle";
		
		for (int i = 0; true; i++) { 
			con = cachingDataSource(c_connectionPool);
			try {
				if (!con.isClosed())
					break; // 链接未关闭则跳出循环，链接已经关闭的情况下重新获取连接，最多获取5次
				this.freeConnection(con);
			} catch (SQLException e) {
				//e.printStackTrace();
			}
			System.out.print("获取第" + (i + 1) + "次连接失败！");
			if (i++ == 5)
				break;// 最多5次
		}
		return con;
	}

	public Connection getConnection(){
		return cachingDataSource(DEFAULT_POOL);
	}

	/**
	 * 将连接对象返回给JBOSS的连接池
	 * @param con 连接对象
	 */
	public synchronized void freeConnection(Connection con){
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception ex) {
			System.out.println("Error!!!!!! DBConnectionManager-->freeConnection() catch Exception!");
			ex.printStackTrace();
		}
	}

	/**
	 * 从缓存中获取连接对象
	 * 如果数据源缓存中存在了数据源,直接取出来去获得连接,如果没有,进行JNDI查找,并缓存数据源
	 * 如果取出来对象已被JVM回收,则删除该对象,重新生成数据源
	 * @author caod
	 * @Date 2014-10-17
	 * @param name 连接名称（atsoft；可扩展为atsoft01）
	 * @return Connection 
	 */
	private synchronized Connection cachingDataSource(String name){
		try {
			if(cacheMap.containsKey(name)){
				DataSource ds = cacheMap.get(name);
				if (ds != null) {
					return ds.getConnection();
				}
			}
			return cachingOneDatasource(name);
		} catch (Exception e) {
			System.out.println("Error!!!!!! DBConnectionManager-->cachingDataSource() catch Exception:系统无法获取连接...java:/jdbc/" + name);
			e.printStackTrace();
		} finally {
		}
		
		
		return null;
	}

	/**
	 * 载入连接池
	 * @author caod
	 * @Date 2014-10-17
	 * @param name 连接名称（atsoft；可扩展为atsoft01）
	 * @return Connection
	 */
	private Connection cachingOneDatasource(String name) throws Exception{
		Connection conn = null;
		try {
			sychroLock.lock();			
			if ( !cacheMap.containsKey(name) ) {
				initial = new InitialContext();
				DataSource newDataSource = (DataSource) initial.lookup("java:/MySqlDS/" + name);
				cacheMap.put(name, newDataSource);
				System.out.println("载入连接池 "+name);
				conn = newDataSource.getConnection();
			} else {
				conn = (cacheMap.get(name)).getConnection();
			}
			return conn;
		} catch (Exception ex) {
			System.out.println("Error!!!!!! DBConnectionManager-->cachingOneDatasource() catch Exception:系统无法获取连接...java:/jdbc/" + name);
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (initial != null) initial.close();
			sychroLock.unlock();
		}
	}
}


