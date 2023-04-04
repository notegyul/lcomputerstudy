package com.lcomputer.mymvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputer.mymvc.database.DB_Connection;
import com.lcomputer.mymvc.vo.Board;

public class BoardDAO {

	private static BoardDAO dao = null;
	
	private BoardDAO() {
		
	}
	public static BoardDAO getInstance() {
		if(dao == null)
			dao = new BoardDAO();
		return dao;
	}
	public void regist(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into board(b_title,b_content,b_date,b_writer,b_count,u_idx) values(?,?,now(),?,?,?)";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,board.getB_title());
			pstmt.setString(2,board.getB_content());
			//pstmt.setString(3, board.getB_date());
			pstmt.setString(3, board.getB_writer());
			pstmt.setInt(4,board.getB_count());
			pstmt.setInt(5,board.getU_idx());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Board> getBoardList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		String sql = "select * from board";
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				board.setB_writer(rs.getString("b_writer"));
				board.setB_count(rs.getInt("b_count"));
				list.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public Board getBoard(int idx) {
		Board board = new Board();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board where b_idx=?";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				board.setB_writer(rs.getString("b_writer"));
				board.setB_count(rs.getInt("b_count"));
			
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
		
		return board;
	}
	
	public int edit(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update board set b_title=?,b_content=? where b_idx=?";
		
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getB_idx());
			
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
	
	public int delete(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from board where b_idx=?";
		
		
		try {
			conn= DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	

}
