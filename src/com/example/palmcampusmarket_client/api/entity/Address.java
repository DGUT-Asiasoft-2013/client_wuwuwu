package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;

public class Address implements Serializable{
	
	Integer id;
	String address_name;
	String address_telephone;
	String address;
	boolean isDefaultInfo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress_name() {
		return address_name;
	}
	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
	public String getAddress_telephone() {
		return address_telephone;
	}
	public void setAddress_telephone(String address_telephone) {
		this.address_telephone = address_telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isDefaultInfo() {
		return isDefaultInfo;
	}
	public void setDefaultInfo(boolean isDefaultInfo) {
		this.isDefaultInfo = isDefaultInfo;
	}
	

}
