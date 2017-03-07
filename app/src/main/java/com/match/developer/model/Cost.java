package com.match.developer.model;

import cn.bmob.v3.BmobObject;

/**
 * 费用类
 * @author Tanner
 *
 */
public class Cost extends BmobObject{

	private static final long serialVersionUID = 1L;
	private String date;
	private String type;//in out
	private String typeDesc;//out : 餐饮 或 水电气网
	private double money;
	private String desc;
	private String month;
	private String name;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
}
