package com.lcomputer.mymvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lcomputer.mymvc.service.BoardService;
import com.lcomputer.mymvc.service.UserService;
import com.lcomputer.mymvc.vo.Board;
import com.lcomputer.mymvc.vo.Comment;
import com.lcomputer.mymvc.vo.Pagination;
import com.lcomputer.mymvc.vo.Search;
import com.lcomputer.mymvc.vo.User;

@WebServlet("*.test")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		doPost(req,res);
	}
	
	String checkSession(HttpServletRequest req, HttpServletResponse res, String command) {
		HttpSession session = req.getSession();
		
		String[] authList = {
				"/user-list.test",
				"/user-insert.test",
				"/user-insert-process.test",
				"/user-detail.test",
				"/user-edit.test",
				"/user-edit-process.test",
				"/logout.test"
				
		};
		
		for(String item : authList) {
			if (item.equals(command)) {
				if(session.getAttribute("user") ==  null) {
					command = "/access-denied.test";
				}
			}
		}
		
		return command;
		
		
		
		
	}
	
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int count = 0;
		int page = 1;
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		User user = null;
		String num;
		HttpSession session = null;
		String pw = null;
		Search search = null;
		command = checkSession(req,res,command);
		
		boolean isRedirected = false;
		
		switch(command) {
			case "/newjoin.test":
				view = "myuser/newjoin";
				break;
			case "/newjoin-process.test":
				user = new User();
				user.setU_id(req.getParameter("id"));
				user.setU_pw(req.getParameter("password"));
				user.setU_name(req.getParameter("name"));
				user.setU_tel(req.getParameter("tel1")+"-"+req.getParameter("tel2")+"-"+req.getParameter("tel3"));
				user.setU_age(req.getParameter("age"));
				
				UserService userService = UserService.getInstance();
				userService.insertUser(user);
				
				view = "myuser/insert-result";
				break;
			case "/user-list.test":
				String reqPage = req.getParameter("page");
				if(reqPage != null) {
					page = Integer.parseInt(reqPage);
					
				}
				userService = UserService.getInstance();
				count = userService.getUsersCount();
				
				Pagination pagination = new Pagination();
				pagination.setCount(count);
				pagination.setPage(page);
				pagination.init();
				
				ArrayList<User> list = userService.getUsers(pagination);
				
				
				req.setAttribute("list", list);
				req.setAttribute("pagination", pagination);
				
				view = "myuser/list";
				break;
			case "/user-detail.test":
				userService = UserService.getInstance();
				num = req.getParameter("u_idx");
				user = userService.getUser(Integer.parseInt(num));
				view = "myuser/user-detail";
				req.setAttribute("user", user);
				break;
				
				/*error code-----------------------------------
				userService = UserService.getInstance();									|
				ArrayList<User> users = userService.getUsers();						|
				String t1 = req.getParameter("u_idx");    									|
				int num = Integer.parseInt(t1);												|
				user = num<=users.size() ? users.get(num-1) : userService.getUser();
																											|
			    view = "myuser/user-detail";													|
			    req.setAttribute("user", user);													|
			    break;																					|
				--------------------------------------------
				user = userService.getUser();													|	
				String no = req.getParameter("u_idx");										|
				user.setU_idx(Integer.parseInt(no));											|
				--------------------------------------------
				*/
			    
			case "/user-edit.test":
				userService = UserService.getInstance();
				num = req.getParameter("u_idx");
				user = userService.getUser(Integer.parseInt(num));
				
				view = "myuser/user-edit";
				req.setAttribute("user", user);
				break;
				
			case "/user-editProcess.test":
				
				userService = UserService.getInstance();
				num = req.getParameter("u_idx");
				user = userService.getUser(Integer.parseInt(num));
				//user-edit.jsp에서 hidden타입으로 폼태그 넘겨줘야 넘버어쩌고 오류 안 뜸
				String id = req.getParameter("edit_id");
				pw = req.getParameter("edit_pw");
				String name = req.getParameter("edit_name");
				String tel = req.getParameter("edit_tel1") +"-"+req.getParameter("edit_tel2")+"-"+req.getParameter("edit_tel3");
				String age = req.getParameter("edit_age");
				user.setU_id(id);
				user.setU_pw(pw);
				user.setU_name(name);
				user.setU_tel(tel);
				user.setU_age(age);
				
				userService.getEditUser(user);	
				view = "myuser/edit-complete";
				req.setAttribute("user", user);
				
				break;
				
			case "/user-delete.test":
				
				userService = UserService.getInstance();
				num = req.getParameter("u_idx");
				user = userService.getUser(Integer.parseInt(num));
				
				userService.deleteUser(user);
				view = "myuser/user-delete";
				req.setAttribute("user", user);
				break;
				
			case "/user-login.test":
				view = "myuser/login";
				break;
			case "/user-login-process.test":
				String idx = req.getParameter("login_id");
				pw = req.getParameter("login_password");
				
				userService = UserService.getInstance();
				user = userService.loginUser(idx,pw);
				
				if(user != null) {
					session = req.getSession();
				//	session.getAttribute("u_idx", user.getU_idx());
				//	session.getAttribute("u_id", user.getU_id());
				//	session.getAttibute("u_pw", user.getU_pw());
				//	session.getAttribute("u_name", user.getU_name());
					session.setAttribute("user", user);
					
					view = "myuser/login-result";
				} else {
					view = "myuser/login-fail";
				}
				
				break;
				
			case "/logout.test":
				session = req.getSession();
				session.invalidate();
				view = "myuser/login";
				break;
				
			case "/access-denied.test":
				
				view = "myuser/acess-denied";
				break;
				
				
			case "/change-authority.test":
				userService = UserService.getInstance();
				
				user = userService.getUser(Integer.parseInt(req.getParameter("u_idx")));
				int memberLevel = Integer.parseInt(req.getParameter("u_manage"));
				user.setU_manage(memberLevel);
				
				userService.changeAuthority(user);
				view = "user-list.test?page=1";
				
				isRedirected = true;
				break;
				
				
			case "/register.test":
				view = "board/put-on-record";
				break;
			case "/register-process.test":
				session = req.getSession();
				user = (User)session.getAttribute("user");
				
				Board board = new Board();
				
				board.setB_title(req.getParameter("title"));
				board.setB_content(req.getParameter("content"));
				//board.setB_date("20230403");
				board.setB_writer(user.getU_name());
				board.setB_count(1);
				board.setU_idx(user.getU_idx());
				
				
				
				
				BoardService boardService = BoardService.getInstance();
				boardService.regist(board);
				
				view = "board/record-complete";
				break;
				
			case "/title-list.test":
				
				/*
				search = new Search();
				search.setType(req.getParameter("search"));		//셀렉트 박스
				search.setKeyword(req.getParameter("keyword"));		//검색한 내용
				
				pagination = new Pagination();
				pagination.setSearch(search);
				
				boardService = BoardService.getInstance();
				ArrayList<Board> bList = boardService.getBoardList(pagination);
				view = "board/b-list";
				req.setAttribute("bList", bList);
				break;
				
				*/ //원래 코드(위)
				
				boardService = BoardService.getInstance();
				
				
				
				search = new Search();
				search.setType(req.getParameter("type"));
				search.setKeyword(req.getParameter("keyword"));
				
				
				reqPage = req.getParameter("page");
				if(reqPage != null) {
					page = Integer.parseInt(reqPage);
				}
				
				count = boardService.getBoardsCount(search);		//매개변수 search (5.18com)
				
				pagination = new Pagination();
				pagination.setSearch(search);	
				pagination.setCount(count);
				pagination.setPage(page);
				pagination.init();
				
				ArrayList<Board> bList = boardService.getBoardList(pagination);
				req.setAttribute("bList", bList);
				req.setAttribute("pagination", pagination);
				
				view = "board/b-list";
				break;
				
				
				
				/* 참고(아래)
				 
				String reqPage = req.getParameter("page");
				if(reqPage != null) {
					page = Integer.parseInt(reqPage);
					
				}
				userService = UserService.getInstance();
				count = userService.getUsersCount();
				
				Pagination pagination = new Pagination();
				pagination.setCount(count);
				pagination.setPage(page);
				pagination.init();
				
				ArrayList<User> list = userService.getUsers(pagination);
				
				
				req.setAttribute("list", list);
				req.setAttribute("pagination", pagination);
				
				view = "myuser/list";
				break;
				
				*/
				
				
			case "/content-detail.test":
				boardService = BoardService.getInstance();
				idx = req.getParameter("b_idx");
				board = boardService.getBoard(Integer.parseInt(idx));
				
				view = "board/content-detail";
				req.setAttribute("board", board);
				
				ArrayList<Comment> cList = boardService.getCommentList(board);
				req.setAttribute("cList", cList);
				
				
				break;
				
			case "/content-edit.test":
				boardService = BoardService.getInstance();
				idx = req.getParameter("b_idx");
				board = boardService.getBoard(Integer.parseInt(idx));
				
				
				
				view = "board/edit";
				req.setAttribute("board", board);
				break;
				
			case "/content-edit-process.test":
				boardService = BoardService.getInstance();
				idx = req.getParameter("b_idx");
				board = boardService.getBoard(Integer.parseInt(idx));
				
				board.setB_title(req.getParameter("edit-title"));
				board.setB_content(req.getParameter("edit-content"));
				
				boardService.edit(board);
				view = "board/edit-complete";
				req.setAttribute("board", board);
				break;
				
			case "/content-delete.test":
				boardService = BoardService.getInstance();
				idx = req.getParameter("b_idx");
				board = boardService.getBoard(Integer.parseInt(idx));
				boardService.delete(board);
				
				view = "board/delete";
				req.setAttribute("board", board);
				break;
				
				
			case "/reply.test":
				board = new Board();
				String b_group = req.getParameter("b_group");
				String b_order = req.getParameter("b_order");
				String b_depth = req.getParameter("b_depth");
				board.setB_group(Integer.parseInt(b_group));
				board.setB_order(Integer.parseInt(b_order));
				board.setB_depth(Integer.parseInt(b_depth));
				
				req.setAttribute("board", board);
				view = "board/reply";
				break;
				
			case "/reply-process.test":
				boardService = BoardService.getInstance();
				
				
				session = req.getSession();
				user = (User) session.getAttribute("user");
				user.setU_idx(Integer.parseInt(req.getParameter("u_idx")));
				
				board = new Board();
				board.setB_title(req.getParameter("title"));
				board.setB_content(req.getParameter("content"));
				board.setB_group(Integer.parseInt(req.getParameter("b_group")));
				board.setB_order(Integer.parseInt(req.getParameter("b_order"))+1);
				board.setB_depth(Integer.parseInt(req.getParameter("b_depth"))+1);
				board.setU_idx(user.getU_idx());
				
				boardService.replyTo(board);
				view = "board/reply-complete";
				break;
				
			case "/comment.test":
				
				boardService = BoardService.getInstance();
				session = req.getSession();
				user = (User) session.getAttribute("user");
				 
				Comment comment = new Comment();
				
				comment.setB_idx(Integer.parseInt(req.getParameter("b_idx")));
				comment.setB_comment(req.getParameter("b_comment"));
				comment.setU_idx(Integer.parseInt(req.getParameter("u_idx")));
				boardService.commentTo(comment);
				

				view = "content-detail.test?b_idx=" + req.getParameter("b_idx");
				isRedirected = true;
				
				
				break;
			case "/aj-write-comment.test":
				boardService = BoardService.getInstance();
				session = req.getSession();
				user = (User) session.getAttribute("user");
				board = boardService.getBoard(Integer.parseInt(req.getParameter("b_idx")));
				
				comment = new Comment();
				comment.setB_idx(Integer.parseInt(req.getParameter("b_idx")));
				comment.setB_comment(req.getParameter("b_comment"));
				comment.setU_idx(Integer.parseInt(req.getParameter("u_idx")));
				comment.setC_group(Integer.parseInt(req.getParameter("c_group")));
				comment.setC_order(Integer.parseInt(req.getParameter("c_order")));
				comment.setC_depth(Integer.parseInt(req.getParameter("c_depth")));
				
				
				boardService.commentTo(comment);
				cList = boardService.getCommentList(board);
				
				req.setAttribute("cList", cList);
				req.setAttribute("comment", comment);
				
				view = "board/test";
				break;
				
			case "/aj-edit-comment.test":
				boardService = BoardService.getInstance();
				session = req.getSession();
				user = (User) session.getAttribute("user");
				//
				board = boardService.getBoard(Integer.parseInt(req.getParameter("b_idx")));
				
				comment = new Comment();
				comment.setB_comment(req.getParameter("b_comment"));
				comment.setB_idx(Integer.parseInt(req.getParameter("b_idx")));
				comment.setU_idx(Integer.parseInt(req.getParameter("u_idx")));
				comment.setC_idx(Integer.parseInt(req.getParameter("c_idx")));
				comment.setC_date(req.getParameter("c_date"));
				boardService.editComment(comment);
				
				//
				cList = boardService.getCommentList(board);
				req.setAttribute("cList", cList);
				
				req.setAttribute("comment", comment);
				view = "board/test";
				break;
				
			case "/aj-delete-comment.test":
				boardService = BoardService.getInstance();
				
				session = req.getSession();
				user = (User) session.getAttribute("user");
				
				int c_idx = Integer.parseInt(req.getParameter("c_idx"));
				boardService.deleteComment(c_idx);
				
				comment = boardService.getComment(c_idx);
				req.setAttribute("comment", comment);
						
				board = boardService.getBoard(Integer.parseInt(req.getParameter("b_idx")));
				cList = boardService.getCommentList(board);
				req.setAttribute("cList", cList);
				
				view = "board/test";
				
				
				break;
				
			case "/aj-re-comment.test":
				boardService = BoardService.getInstance();
				session = req.getSession();
				user = (User) session.getAttribute("user");
				
				board = boardService.getBoard(Integer.parseInt(req.getParameter("b_idx")));
				
				comment = new Comment();
				
				comment.setB_idx(Integer.parseInt(req.getParameter("b_idx")));
				
				comment.setU_idx(Integer.parseInt(req.getParameter("u_idx")));
				
				comment.setB_comment(req.getParameter("b_comment"));
				comment.setC_group(Integer.parseInt(req.getParameter("c_group")));
				comment.setC_order(Integer.parseInt(req.getParameter("c_order"))+1);
				comment.setC_depth(Integer.parseInt(req.getParameter("c_depth"))+1);
				comment.setU_idx(user.getU_idx());
				comment.setB_idx(board.getB_idx());
				
				
				boardService.reComment(comment);
				
				cList = boardService.getCommentList(board);
				req.setAttribute("cList", cList);
				
				req.setAttribute("comment", comment);
				view = "board/test";
				break;
		}
		
		if (isRedirected) {
			res.sendRedirect(view);
		} else {
			RequestDispatcher rd = req.getRequestDispatcher(view+".jsp");
			rd.forward(req, res);
		}
		
	}
	
	
}
