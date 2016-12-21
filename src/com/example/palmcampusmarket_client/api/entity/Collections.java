package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;
import java.util.Date;


import android.R.integer;

public class Collections implements Serializable{
	public static class Key implements Serializable{
		User user;
		Commodity commodity;
		
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		

		public Commodity getCommodity() {
			return commodity;
		}
		public void setCommodity(Commodity commodity) {
			this.commodity = commodity;
		}
		

		public boolean equals(Object obj) {
			if(obj instanceof Key){
				Key other = (Key)obj;
				return commodity.getId() == other.commodity.getId() && user.getId() == other.user.getId();
			}else{
				return false;
			}
		}


		public int hashCode() {
			return commodity.getId();
		}	
	}

	

	Key id;
	Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}


	void onPrePersist(){
		createDate = new Date();
	}

}
