package com.match.developer.model;

import cn.bmob.v3.BmobObject;


public class User extends BmobObject {

	private static final long serialVersionUID = 1L;
	private String name;
	private String pwd;
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



}
