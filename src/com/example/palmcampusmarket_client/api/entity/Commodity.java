package com.example.palmcampusmarket_client.api.entity;


import java.io.Serializable;



public class Commodity implements Serializable{
	Integer id;
	String CommName;         //商品名称
	String CommPrice;        //商品价格
	int CommNumber;          //商品数量
	String CommDescribe;     //商品描述
	String CommImage;        //商品图片

	String avatar;
	String nickname;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	}
