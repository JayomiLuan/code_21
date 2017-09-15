package cn.itcast.day21.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	//持有C3P0连接池对象
	private static DataSource ds = new ComboPooledDataSource();
	
	//设置一个保存连接对象的ThreadLocal对象
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	
	/**
	 * 得到当前线程对应的Connection对象
	 * @return Connection
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = null;
		
		//从ThreadLocal中取得连接对象
		conn = local.get();
		
		//如果没有，则从连接池取得一个新的连接对象，并保存到ThreadLocal
		if( conn == null ){
			conn = ds.getConnection();
			local.set(conn);
		}
		
		return conn;
	}
	
	
	/**
	 * 重写方法，用于DML
	 * @param stmt
	 * @param conn
	 */
	public static void releaseResource( Statement stmt , Connection conn ){
		releaseResource( null , stmt , conn );
	}
	
	public static void releaseResource( Connection conn ){
		releaseResource( null , null , conn );
	}
	
	/**
	 * 用于释放数据库资源的方法
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void releaseResource( ResultSet rs , Statement stmt , Connection conn ){
		if( rs != null ){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs = null;
		
		if( stmt != null ){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stmt = null;
		
		if( conn != null ){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		conn = null;
		//从ThreadLocal中删除已经关闭的数据库连接对象
		local.remove();
	}
}
