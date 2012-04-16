package com.ehl.tira.duty;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelp {
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:oracle";
	private static final String userName = "ehl_atmsic_gd";
	private static final String password = "ehl1234";
	
	private static Connection conn = null;
	static{
		createConn();
	}
	
	private static void createConn(){
		if(conn == null){
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url,userName,password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConn(){
		createConn();
		return conn;
	}
	
	public static boolean execute(String sql){
		boolean isSuccess =  execute(sql, conn);
		closeAll(null, null, conn);
		return isSuccess;
	}
	public static boolean execute(String sql, Connection conn){
		boolean isSuccess = false;
		if(conn != null){
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(sql);
				isSuccess = ps.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		}
		return isSuccess;
	}
	
	public static List<Map<String,String>> executeQuery(String sql){
		List<Map<String,String>> dataL = executeQuery(sql, conn);
		closeAll(null, null, conn);
		return dataL;
	}
	
	public static Object[][] executeQuery2(String sql){
		Object[][] data = null;
		if(conn != null){
			Statement stat = null;
			ResultSet rs = null;
			try {
				stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = stat.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				rs.last();
				int rowCount = rs.getRow();
				data = new Object[rowCount][columnCount];
				System.out.println("row:"+rowCount+";column:"+columnCount);
				rs.beforeFirst();
				int ri = 0;
				while(rs.next()){
					for(int i=0;i<columnCount;i++){
						data[ri][i] = rs.getObject(i+1);
					}
					ri++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				closeAll(rs, stat, null);
			}
		}
		return data;
	}
	
	public static List<Map<String,String>> executeQuery(String sql, Connection conn){
		List<Map<String,String>> dataL = null;
		if(conn != null){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				dataL = new ArrayList<Map<String, String>>();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int cc = rsmd.getColumnCount();
				while(rs.next()){
					Map<String, String> rowData = new HashMap<String, String>();
					for(int i = 1; i <= cc; i ++){
						rowData.put(rsmd.getColumnName(i), rs.getString(i));
					}
					dataL.add(rowData);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				closeAll(rs, ps, null);
			}
		}
		return dataL;
	}
	
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn){
		try {
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			rs = null;
			stmt = null;
			conn = null;
		}
	}
	
	public static void main(String[] args) {
	}
	
	
	
}
