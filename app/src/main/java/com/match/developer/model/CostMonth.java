package com.match.developer.model;

/**
 * 每月费用类
 * @author Tanner
 *
 */
public class CostMonth{

	private String month;
	private double adds;
	private double reduces;
	private double surplus;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getAdds() {
		return adds;
	}
	public void setAdds(double adds) {
		this.adds = adds;
	}
	public double getReduces() {
		return reduces;
	}
	public void setReduces(double reduces) {
		this.reduces = reduces;
	}
	public double getSurplus() {
		return adds-reduces;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	
	
	
	
}
