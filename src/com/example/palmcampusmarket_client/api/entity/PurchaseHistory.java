package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;
import java.util.Date;

public class PurchaseHistory implements Serializable{ //消费记录表

	User user;
	Integer commodity_Id;
	int buyNumber;
	int totalPrice;
	int commodityPrice;
	Date createDate;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getCommodity() {
		return commodity_Id;
	}
	public void setCommodity(Integer commodity) {
		this.commodity_Id = commodity;
	}
	public int getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCommodityPrice() {
		return commodityPrice;
	}
	public void setCommodityPrice(int commodityPrice) {
		this.commodityPrice = commodityPrice;
	}
	
	
}
