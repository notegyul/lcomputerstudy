package com.lcomputer.mymvc.vo;

import java.util.Arrays;

public class User {
	private int u_idx;
	private String u_id;
	private String u_pw;
	private String u_name;
	private String u_tel;
	private String u_age;
	private String[] user_tel;
	private int rownum;
	private int u_manage;
	
	public static final int LEVEL = 1;
	public static final int MAN_LEVEL = 9;
	
	public int getU_manage() {
		return u_manage;
	}
	public void setU_manage(int u_manage) {
		this.u_manage = u_manage;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	public int getU_idx() {
		return u_idx;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}
	public String getU_pw() {
		return u_pw;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_tel(String u_tel) {
		this.u_tel = u_tel;
	}
	public String getU_tel() {
		return u_tel;
	}
	public void setU_age(String u_age) {
		this.u_age = u_age;
	}
	public String getU_age() {
		return u_age;
	}
	public void setUser_tel(String[] user_tel) {
		this.user_tel = user_tel;
	}
	public String[] getUser_tel() {
		user_tel = u_tel.split("-");
		return user_tel;
	}
	@Override
	public String toString() {
		return "User [u_idx=" + u_idx + ", u_id=" + u_id + ", u_pw=" + u_pw + ", u_name=" + u_name + ", u_tel=" + u_tel
				+ ", u_age=" + u_age + ", user_tel=" + Arrays.toString(user_tel) + ", rownum=" + rownum + "]";
	}
}






