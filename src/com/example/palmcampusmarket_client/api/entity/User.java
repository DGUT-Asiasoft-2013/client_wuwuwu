package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;

public class User implements Serializable{
	Integer id;
	String account; 
	String passwordHash;
	String telephone;
	String nickname;
	String avatar;
	int money;

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Integer getId(){
		return id;
	}
	public void setId(){
		this.id=id;
	}
	public String getAccount(){
		return account;
	}
	public void setAccount(){
		this.account=account;
	}
	public String getPasswordHash(){
		return passwordHash;
	}
	public void setPasswordHash(){
		this.passwordHash = passwordHash;
	}
	public String getTelephone(){
		return telephone;
	}
	public void setTelephone(){
		this.telephone = telephone;
	}
	public String getNickname(){
		return nickname;
	}
	public void setNickname(){
		this.nickname = nickname;
	}
	public String getAvatar(){
		return avatar;
	}
	public void setAvatar(){
		this.avatar = avatar;
	}
}
