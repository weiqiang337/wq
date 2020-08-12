package com.xiaoshu.entity;

public class ContentVo extends Content{
	private String cname;

	public ContentVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContentVo(String cname) {
		super();
		this.cname = cname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "ContentVo [cname=" + cname + "]";
	}
	
}
