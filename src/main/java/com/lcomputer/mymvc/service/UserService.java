package com.lcomputer.mymvc.service;



import java.util.ArrayList;

import com.lcomputer.mymvc.dao.UserDAO;
import com.lcomputer.mymvc.vo.Pagination;
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
	
	public ArrayList<User> getUsers(Pagination pagination){
		return dao.getUsers(pagination);
	}
	
	public User getUser(int num) {
		return dao.getUserInfo(num);
	}
	
	public int getEditUser(User user) {
		return dao.editUser(user);
	}
	public int deleteUser(User user) {
		return dao.delete(user);
	}
	public int getUsersCount() {
		return dao.getUsersCount();
	}
	public User loginUser(String idx, String pw) {
		return dao.loginUser(idx,pw);
	}
	public int changeAuthority(User user) {
		return dao.changeAuthority(user);
	}
}
