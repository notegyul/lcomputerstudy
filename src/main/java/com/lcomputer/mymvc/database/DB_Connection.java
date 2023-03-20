package com.lcomputer.mymvc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		
		String url = "jdbc.mysql://localhost:3306/mytest";
		String id = "root";
		String pw = "ckatkflekd!99";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url,id,pw);
		
		return conn;
	}
	
	
	
}
