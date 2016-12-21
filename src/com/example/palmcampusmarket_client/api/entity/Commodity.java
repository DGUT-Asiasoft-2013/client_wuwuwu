package com.example.palmcampusmarket_client.api.entity;

import java.util.Date;

public class Commodity {
	User user;
	String CommName;         //商品名称
	String CommPrice;        //商品价格
	int CommNumber;          //商品数量
	String CommDescribe;     //商品描述
	String CommImage;        //商品图片

	Integer id;
	Date createDate;
	Date editDate;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCommName() {
		return CommName;
	}
	public void setCommName(String commName) {
		CommName = commName;
	}
	public String getCommPrice() {
		return CommPrice;
	}
	public void setCommPrice(String commPrice) {
		CommPrice = commPrice;
	}
	public int getCommNumber() {
		return CommNumber;
	}
	public void setCommNumber(int commNumber) {
		CommNumber = commNumber;
	}
	public String getCommDescribe() {
		return CommDescribe;
	}
	public void setCommDescribe(String commDescribe) {
		CommDescribe = commDescribe;
	}
	public String getCommImage() {
		return CommImage;
	}
	public void setCommImage(String commImage) {
		CommImage = commImage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	            
	
	
}
