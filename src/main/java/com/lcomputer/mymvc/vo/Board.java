package com.lcomputer.mymvc.vo;

public class Board {
	private int b_idx;
	private String b_title;
	private String b_content;
	private int b_count;
	private String b_writer;
	private String b_date;
	private int u_idx;
	private int b_group;
	private int b_order;
	private int b_depth;
	private String b_comment;
	
	public String getB_comment() {
		return b_comment;
	}
	public void setB_comment(String b_comment) {
		this.b_comment = b_comment;
	}
	
	
	public int getB_group() {
		return b_group;
	}
	public void setB_group(int b_group) {
		this.b_group = b_group;
	}
	public int getB_order() {
		return b_order;
	}
	public void setB_order(int b_order) {
		this.b_order = b_order;
	}
	public int getB_depth() {
		return b_depth;
	}
	public void setB_depth(int b_depth) {
		this.b_depth = b_depth;
	}
	
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	public int getU_idx() {
		return u_idx;
	}
	
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	public int getB_idx() {
		return b_idx;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_count(int b_count) {
		this.b_count = b_count;
	}
	public int getB_count() {
		return b_count;
	}
	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}
	public String getB_writer() {
		return b_writer;
	}
	public void setB_date(String b_date) {
		this.b_date = b_date;
	}
	public String getB_date() {
		return b_date;
	}
}
