package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;
import java.util.Date;

public class PurchaseHistory implements Serializable{ //消费记录表

	Integer Id;
	User user;
	Commodity commodity;
	int buyNumber;
	int totalPrice;
	Date createDate;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}
	
	
	
}
