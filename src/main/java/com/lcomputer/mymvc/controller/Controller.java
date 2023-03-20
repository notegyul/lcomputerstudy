package com.lcomputer.mymvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lcomputer.mymvc.service.UserService;
import com.lcomputer.mymvc.vo.User;

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
				user.setU_tel(req.getParameter("tel1")+"-"+req.getParameter("tel21")+"-"+req.getParameter("tel3"));
				user.setU_age(req.getParameter("age"));
				
				UserService userService = UserService.getInstance();
				userService.insertUser(user);
				
				view = "myuser/insert-result";
		}
		
		RequestDispatcher rd = req.getRequestDispatcher(view+".jsp");
		rd.forward(req, res);
		
	}
	
	
}
