package com.lcomputer.mymvc.vo;

public class Board {
	private int b_idx;
	private String b_title;
	private String b_content;
	private int b_count;
	private String b_writer;
	private String b_date;
	
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
