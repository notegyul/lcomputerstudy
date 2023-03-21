package com.lcomputer.mymvc.service;



import java.util.ArrayList;

import com.lcomputer.mymvc.dao.UserDAO;
import com.lcomputer.mymvc.vo.User;


public class UserService {
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		return service;
	}
	
	public void insertUser(User user) {
		dao.insertUser(user);
	}
	
	public ArrayList<User> getUsers(){
		return dao.getUsers();
	}
	
	public User getUser() {
		return dao.getUserInfo(User.getUser());
	}
	
	
}
