package com.example.palmcampusmarket_client.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/21.
 */
public class Bill implements Serializable{
    Integer id;
    Date createDate;
    Date editDate;
    Commodity commodity;
    User user;
    int buyNumber;
    int totalPrice;
    
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

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
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

    
}
