package com.lcomputer.mymvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lcomputer.mymvc.service.UserService;
import com.lcomputer.mymvc.vo.User;
import com.lcomputer.mymvc.dao.UserDAO;

@WebServlet("*.test")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		doPost(req,res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		switch(command) {
			case "/newjoin.test":
				view = "myuser/newjoin";
				break;
			case "/newjoin-process.test":
				User user = new User();
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
				userService = UserService.getInstance();
				ArrayList<User> list = userService.getUsers();
				view = "myuser/list";
				req.setAttribute("list", list);
				break;
			case "/user-detail.test":

				/*
				userService = UserService.getInstance();
				ArrayList<User> users = userService.getUsers();
				String t1 = req.getParameter("u_idx");
				int num = Integer.parseInt(t1);
				user = num<=users.size() ? users.get(num-1) : userService.getUser();
				
			    view = "myuser/user-detail";
			    req.setAttribute("user", user);
			    break;
			    */
				User user2 = null;
				userService = UserService.getInstance();
				String num = req.getParameter("u_idx");
				user2 = userService.getUser(Integer.parseInt(num));
				view = "myuser/user-detail";
				req.setAttribute("user", user2);
				break;
				
				
				
				/*
				user = userService.getUser();
				String no = req.getParameter("u_idx");
				user.setU_idx(Integer.parseInt(no));
				*/
			    
			case "/user-edit":
				break;
			
			case "/user-delete":
				
				
				
			
		}
		
		RequestDispatcher rd = req.getRequestDispatcher(view+".jsp");
		rd.forward(req, res);
		
	}
	
	
}
