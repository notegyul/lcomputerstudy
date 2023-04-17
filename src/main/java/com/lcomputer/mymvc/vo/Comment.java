package com.lcomputer.mymvc.vo;

public class Comment {

	private int c_idx;
	private int b_idx;
	private int u_idx;
	private String b_comment;
	private String c_date;
	
	public String getDate() {
		return c_date;
	}
	public void setDate(String c_date) {
		this.c_date = c_date;
	}
	
	public int getC_idx() {
		return c_idx;
	}
	public void setC_idx(int c_idx) {
		this.c_idx = c_idx;
	}
	public int getB_idx() {
		return b_idx;
	}
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	public int getU_idx() {
		return u_idx;
	}
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	public String getComment() {
		return b_comment;
	}
	public void setComment(String b_comment) {
		this.b_comment = b_comment;
	}
	
	
	
	
}
