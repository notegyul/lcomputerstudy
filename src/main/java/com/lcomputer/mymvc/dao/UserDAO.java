package com.lcomputer.mymvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputer.mymvc.database.DB_Connection;
import com.lcomputer.mymvc.vo.Pagination;
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
	
	//user-list
	public ArrayList<User> getUsers(Pagination pagination){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = null;
		int pageNum = pagination.getPageNum();
		
		try {
			conn = DB_Connection.getConnection();
			//String query = "select * from user limit ?,3";		
			String query = new StringBuilder()
					.append("select 	@rownum := @rownum -1 as rownum,\n")
					.append("           ta.*\n")
					.append("from       user ta,\n")
					.append("           (select @rownum := (select count(*)-?+1 from user ta)) tb\n")
					.append("limit      ?, ?\n").toString();
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,pageNum);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, Pagination.perPage);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			
			while(rs.next()) {
				User user = new User();
				user.setRownum(rs.getInt("rownum"));
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
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	/*
	public User getUserInfo(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = DB_Connection.getConnection();
			String sql = "select * from user where u_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getU_idx());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				user.setU_idx(rs.getInt("u_idx"));
				user.setU_id(rs.getString("u_id"));
				user.setU_name(rs.getString("u_name"));
				user.setU_tel(rs.getString("u_tel"));
				user.setU_age(rs.getString("u_age"));
				
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
	*/
	
	public User getUserInfo(int idx) {
		User user = new User();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where u_idx=?";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setU_idx(rs.getInt("u_idx"));
				user.setU_id(rs.getString("u_id"));
				user.setU_pw(rs.getString("u_pw"));
				user.setU_name(rs.getString("u_name"));
				user.setU_tel(rs.getString("u_tel"));
				user.setU_age(rs.getString("u_age"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
	
	public int editUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String sql = "update user set u_id=?, u_pw=?, u_name=?, u_tel=?, u_age=? where u_idx=?";
			
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());		//업데이트하려면 폼에서 받은 데이터를 여기 넣어야 할 듯?//
			pstmt.setString(2, user.getU_pw());
			pstmt.setString(3, user.getU_name());
			pstmt.setString(4, user.getU_tel());
			pstmt.setString(5, user.getU_age());
			pstmt.setInt(6, user.getU_idx());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("실패!!!");
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int delete(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from user where u_idx=?";
		int result = 0;
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,user.getU_idx());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int getUsersCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DB_Connection.getConnection();
			String query = "select count(*) count from user";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("count");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	public User loginUser(String idx, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			conn = DB_Connection.getConnection();
			String sql = "select * from user where u_id = ? and u_pw = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setU_idx(rs.getInt("u_idx"));
				user.setU_pw(rs.getString("u_pw"));
				user.setU_id(rs.getString("u_id"));
				user.setU_name(rs.getString("u_name"));
				
				
			}
		}catch(Exception e) {
			System.out.println("SQLException : " + e.getMessage());
		}finally {
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return user;
	} //end of loginUser
	
	
	
	
	
	
	
	
	
	
}
