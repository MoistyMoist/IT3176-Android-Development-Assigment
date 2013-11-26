package com.btrading.models;

public class Wish {
	
	private int wishID;
	private String name;
	private String status;
	
	private User user;

	public int getWishID() {
		return wishID;
	}

	public void setWishID(int wishID) {
		this.wishID = wishID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
