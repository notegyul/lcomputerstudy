package com.lcomputer.mymvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lcomputer.mymvc.database.DB_Connection;
import com.lcomputer.mymvc.vo.User;


public class UserDAO {
	
	private static UserDAO dao = null;
	
	private UserDAO() {
		
	}
	
	public static UserDAO getInstance() {
		if(dao == null)
			dao = new UserDAO();
		
		return dao;
	}
	
	public void insertUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DB_Connection.getConnection();
			String sql = "insert into user(u_id,u_pw,u_name,u_tel,u_age) values(?,?,?,?,?)";
			//insert into mytest -> user(user_~ --> u_~~) #####
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());
			pstmt.setString(2, user.getU_pw());
			pstmt.setString(3, user.getU_name());
			pstmt.setString(4, user.getU_tel());
			pstmt.setString(5, user.getU_age());
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<User> getUsers(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = null;
		
		try {
			conn = DB_Connection.getConnection();
			String query = "select * from user";		//from mytest -> user(mysql 테이블 변경 필요)
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			
			while(rs.next()) {
				User user = new User();
				user.setU_idx(rs.getInt("u_idx"));
				user.setU_id(rs.getString("u_id"));
				user.setU_name(rs.getString("u_name"));
				user.setU_tel(rs.getString("u_tel"));
				user.setU_age(rs.getString("u_age"));
				list.add(user);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	public User getUserInfo(User paramUser) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = new User();
		
		try {
			conn = DB_Connection.getConnection();
			String sql = "select * from user where u_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, paramUser.getU_idx());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				user.setU_idx(rs.getInt("u_idx"));
				user.setU_id(rs.getString("u_id"));
				user.setU_name(rs.getString("name"));
				user.setU_tel(rs.getString("u_tel"));
				user.setU_age(rs.getString("age"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return user;
	}
	
	
}
