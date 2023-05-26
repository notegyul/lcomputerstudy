package com.lcomputer.mymvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputer.mymvc.database.DB_Connection;
import com.lcomputer.mymvc.vo.Board;
import com.lcomputer.mymvc.vo.Comment;
import com.lcomputer.mymvc.vo.Pagination;
import com.lcomputer.mymvc.vo.Search;
import com.lcomputer.mymvc.vo.User;

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
		String sql = "insert into board(b_title,b_content,b_date,b_writer,b_count,u_idx, b_group, b_order, b_depth, file_origin, file_serv) values(?,?,now(),?,?,?,1,1,0,?,?)";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,board.getB_title());
			pstmt.setString(2,board.getB_content());
			//pstmt.setString(3, board.getB_date());
			pstmt.setString(3, board.getB_writer());
			pstmt.setInt(4,board.getB_count());
			pstmt.setInt(5,board.getU_idx());
			pstmt.setString(6, board.getFile_origin());		//원본파일명 
			pstmt.setString(7, board.getFile_serv());			//서버저장파일명  
			
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
	public ArrayList<Board> getBoardList(Pagination pagination){
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		int pageNum = pagination.getPageNum();
		
		Search search = pagination.getSearch();
		String query = null;
		String where = "";
		
		
		if (!(search.getKeyword() == null || search.getKeyword().equals(""))) {
			where += "where ";
			
			switch (search.getType()) {
				//제목	
				case "1":
					where += " b_title like ? ";
					break;
				//제목 + 내용	
				case "2":
					where += " b_title like ? or b_content like ? ";
					break;
				//작성자	
				case "3":
					where += " b_writer like ? ";
					break;
				case "0":
					where += "  b_title like ? or b_content like ? or b_writer like ? ";
					break;
			}
		}
		
		try {
			conn = DB_Connection.getConnection();
			query = "select * from board "
					+ where	
					+ "order by b_group desc, b_order asc " 
					+ "limit ?,?";
			
			
			pstmt = conn.prepareStatement(query);
			if(!(search.getKeyword() == null || search.getKeyword().equals(""))) {
				switch(search.getType()) {
					case "1": 
					case "3":
						
						pstmt.setString(1, "%"+search.getKeyword()+"%");
						pstmt.setInt(2,pageNum);
						pstmt.setInt(3, Pagination.perPage);
						break;
					case "2":
						
						pstmt.setString(1, "%"+search.getKeyword()+"%");
						pstmt.setString(2, "%"+search.getKeyword()+"%");
						pstmt.setInt(3,pageNum);
						pstmt.setInt(4, Pagination.perPage);
						break;
					case "0":
						pstmt.setString(1, "%"+search.getKeyword()+"%");
						pstmt.setString(2, "%"+search.getKeyword()+"%");
						pstmt.setString(3, "%"+search.getKeyword()+"%");
						pstmt.setInt(4,pageNum);
						pstmt.setInt(5, Pagination.perPage);
						break;
				}
			}else {
				
				pstmt.setInt(1,pageNum);
				pstmt.setInt(2, Pagination.perPage);
				
			}
			
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
				board.setU_idx(rs.getInt("u_idx"));
				
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
				list.add(board);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
		//pagination 전 코드
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Search search = null;
		ArrayList<Board> list = null;
		String sql = "select * from board order by b_group desc, b_order asc";
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
				if(rs!= null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
		*/
	}
	
	//list count
	
	public int getBoardsCount(Search search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String whe = "";
		int count = 0;
		
		
		if(!(search.getKeyword() == null || search.getKeyword().equals(""))) {
			switch(search.getType()) {
				case "1":
					whe = "where b_title like ? ";
					break;
				case "2":
					whe = "where b_title like ? or b_content like ? ";
					break;
				case "3":
					whe = "where b_writer like ? ";
					break;
				case "0":
					whe = "where b_title like ? or b_content like ? or b_writer like ? ";
					break;
			}
		}
		
		String sql = "select count(*) count "
				+"from board "
				+ whe;
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);	
			
			if(!(search.getKeyword() == null || search.getKeyword().equals(""))) {
				switch(search.getType()) {
					case "1":
					case "3":
						pstmt.setString(1, "%"+search.getKeyword()+"%");
						break;
					case "2":
						pstmt.setString(1, "%"+search.getKeyword()+"%");
						pstmt.setString(2, "%"+search.getKeyword()+"%");
						break;
					case "0":
						pstmt.setString(1, "%"+search.getKeyword()+"%");
						pstmt.setString(2, "%"+search.getKeyword()+"%");
						pstmt.setString(3, "%"+search.getKeyword()+"%");
						break;
				}
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				count = rs.getInt("count");
			}
			
		}catch(Exception e){
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
				board.setFile_origin(rs.getString("file_origin"));
				board.setFile_serv(rs.getString("file_serv"));
				
				
				
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
			
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
	
	//게시글 조회수(hits)
	public int plusHits(int b_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = 0;
		String sql = "update board set b_count = b_count+1"
						   + " where b_idx=?";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_idx);
			
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
	
	public int commentTo(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into comment (b_comment,b_idx,u_idx,c_date,c_group,c_order,c_depth) values(?,?,?,now(),1,1,0)";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,comment.getB_comment());
			pstmt.setInt(2, comment.getB_idx());
			pstmt.setInt(3, comment.getU_idx());
			
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			sql = "update comment set c_group=last_insert_id() where c_idx=last_insert_id()";
			pstmt = conn.prepareStatement(sql);
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
	
	public ArrayList<Comment> getCommentList(Board board){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Comment> list = null;
		String sql = "select * from comment where b_idx = ? order by c_group, c_order";
															
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
			
			
			list = new ArrayList<>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setB_comment(rs.getString("b_comment"));
				comment.setB_idx(rs.getInt("b_idx"));
				comment.setU_idx(rs.getInt("u_idx"));
				comment.setC_date(rs.getString("c_date"));
				comment.setC_group(rs.getInt("c_group"));
				comment.setC_order(rs.getInt("c_order"));
				comment.setC_depth(rs.getInt("c_depth"));
				//페이지에 리스트 뿌릴 대 g,o,d 필요. 여기서 같이 넘겨주
				list.add(comment);
			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	//잘못 만든 메서드 ㅋ
	public Comment getComment(int cIdx) {
		Comment comment = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from comment where c_idx=?";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cIdx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comment = new Comment();
				comment.setB_comment(rs.getString("b_comment"));
				comment.setC_date(rs.getString("c_date"));
				comment.setB_idx(rs.getInt("b_idx"));
				comment.setU_idx(rs.getInt("u_idx"));
				
				comment.setC_group(rs.getInt("c_group"));
				comment.setC_order(rs.getInt("c_order"));
				comment.setC_depth(rs.getInt("c_depth"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return comment;
	}
	
	public int editComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql ="update comment set b_comment=?, c_date= now() where c_idx=?";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getB_comment());
			pstmt.setInt(2, comment.getC_idx());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null ) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public int deleteComment(int c_idx) {		//매개변수로 c_idx
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from comment where c_idx=?";
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, c_idx);		//(1,c_idx)
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	public int reComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into comment (b_comment,u_idx,b_idx,c_date,c_group,c_order,c_depth) values (?,?,?,now(),?,?,?)";
		int result = 0;
		
		try {
			conn = DB_Connection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getB_comment());
			pstmt.setInt(2, comment.getU_idx());
			pstmt.setInt(3, comment.getB_idx());
			pstmt.setInt(4, comment.getC_group());
			pstmt.setInt(5, comment.getC_order());
			pstmt.setInt(6, comment.getC_depth());
			result = pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update comment set c_order = c_order+1 where c_group = ? and c_order >= ? and c_idx != last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getC_group());
			pstmt.setInt(2, comment.getC_order());
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
