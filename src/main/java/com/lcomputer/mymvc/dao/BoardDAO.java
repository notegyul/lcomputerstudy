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
		String sql = "insert into board(b_title,b_content,b_date,b_writer,b_count,u_idx, b_group, b_order, b_depth) values(?,?,now(),?,?,?,1,1,0)";
		
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
			pstmt.close();
			sql = "UPDATE board SET b_group = last_insert_id() where b_idx = last_insert_id()";
			pstmt = conn.prepareStatement(sql);
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
	//리스트 
	public ArrayList<Board> getBoardList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		String sql = "select * from board order by b_group, b_depth";
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
				
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
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
	
	//리스트 클릭 -> 게시글 상세 
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
				
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(1);
				board.setB_depth(0);
			
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
	
	
	public int replyTo(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into board (b_title, b_content, b_date,u_idx, b_group, b_order, b_depth) values (?,?,now(),?,?,?,?)";
		int result = 0;
		
		
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getU_idx());
			pstmt.setInt(4, board.getB_group());
			pstmt.setInt(5, board.getB_order());
			pstmt.setInt(6, board.getB_depth());
			pstmt.executeUpdate();
			pstmt.close();
			sql = "update board set b_order = b_order+1 where b_group = ? and b_order >= ? and b_idx != last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_group());
			pstmt.setInt(2, board.getB_order()); 
			result = pstmt.executeUpdate();
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
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	

}
