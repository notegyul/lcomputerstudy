package com.lcomputer.mymvc.service;

import java.util.ArrayList;

import com.lcomputer.mymvc.dao.BoardDAO;
import com.lcomputer.mymvc.vo.Board;
import com.lcomputer.mymvc.vo.Comment;

public class BoardService {

	private static BoardService service = null;
	private static BoardDAO dao = null;
	
	private BoardService() {
		
	}
	public static BoardService getInstance() {
		if(service == null) {
			service = new BoardService();
			dao = BoardDAO.getInstance();
		}
		return service;
	}
	public void regist(Board board) {
		dao.regist(board);
	}
	public ArrayList<Board> getBoardList(){
		return dao.getBoardList();
	}
	public Board getBoard(int idx) {
		return dao.getBoard(idx);
	}
	public int edit(Board board) {
		return dao.edit(board);
	}
	public int delete(Board board) {
		return dao.delete(board);
	}
	
	
	public int replyTo(Board board) {
		return dao.replyTo(board);
	}
	
	public int commentTo(Comment comment) {
		return dao.commentTo(comment);
	}
	
	public ArrayList<Comment> getCommentList(Board board){
		return dao.getCommentList(board);
	}
	
}
