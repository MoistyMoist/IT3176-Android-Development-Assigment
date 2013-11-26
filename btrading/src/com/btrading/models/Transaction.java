package com.btrading.models;

public class Transaction {
	
	private int transactionID;
	private Product offeringProduct;
	private Product takingProduct;
	private String status;
	private User offeringUser;
	private User takingUser;
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public Product getOfferingProduct() {
		return offeringProduct;
	}
	public void setOfferingProduct(Product offeringProduct) {
		this.offeringProduct = offeringProduct;
	}
	public Product getTakingProduct() {
		return takingProduct;
	}
	public void setTakingProduct(Product takingProduct) {
		this.takingProduct = takingProduct;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getOfferingUser() {
		return offeringUser;
	}
	public void setOfferingUser(User offeringUser) {
		this.offeringUser = offeringUser;
	}
	public User getTakingUser() {
		return takingUser;
	}
	public void setTakingUser(User takingUser) {
		this.takingUser = takingUser;
	}
	
	

}
