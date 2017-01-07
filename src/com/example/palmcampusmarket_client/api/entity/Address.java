package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;

public class Address implements Serializable{
	User user;
	Integer id;
	String address;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
