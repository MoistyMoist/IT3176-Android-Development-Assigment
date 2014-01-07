package com.btrading.utils;
import java.util.ArrayList;

import com.btrading.models.*;

public class StaticObjects {
	
	static String token="token";
	
	//ALL THE PRODUCTS WITH USER DATA
	static ArrayList<Product> AllProducts;
	//THE CURRENT LOGIN USER
	static User currentUser;
	//THE SELECTED SINGLE PRODUCT
	static Product selectedProduct;
	//THE REQUEST STATUS
	static int RequestStatus;
	//THE REQUEST MESSAGE
	static String RequestMessage;
	//THE CURRENT USER PRODUCTS
	static ArrayList<Product> userProducts;
	//PRODUCT TO CREATE
	static Product productToCreate;
	//THE CURRENT USER WISHLIST
	static ArrayList<Wish> userWishlist;
	//TRANSCATIONS
	static ArrayList<Transaction> allTrades;
	


	
	
	
	
	
	
	public static ArrayList<Transaction> getAllTrades() {
		return allTrades;
	}
	public static void setAllTrades(ArrayList<Transaction> allTrades) {
		StaticObjects.allTrades = allTrades;
	}
	public static ArrayList<Wish> getUserWishlist() {
		return userWishlist;
	}
	public static void setUserWishlist(ArrayList<Wish> userWishlist) {
		StaticObjects.userWishlist = userWishlist;
	}
	public static void setToken(String token) {
		StaticObjects.token = token;
	}
	public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User currentUser) {
		StaticObjects.currentUser = currentUser;
	}
	public static Product getSelectedProduct() {
		return selectedProduct;
	}
	public static void setSelectedProduct(Product selectedProduct) {
		StaticObjects.selectedProduct = selectedProduct;
	}
	public static int getRequestStatus() {
		return RequestStatus;
	}
	public static void setRequestStatus(int requestStatus) {
		RequestStatus = requestStatus;
	}
	public static String getRequestMessage() {
		return RequestMessage;
	}
	public static void setRequestMessage(String requestMessage) {
		RequestMessage = requestMessage;
	}
	public static ArrayList<Product> getAllProducts() {
		return AllProducts;
	}
	public static void setAllProducts(ArrayList<Product> allProducts) {
		AllProducts = allProducts;
	}
	public static ArrayList<Product> getUserProducts() {
		return userProducts;
	}
	public static void setUserProducts(ArrayList<Product> userProducts) {
		StaticObjects.userProducts = userProducts;
	}
	public String getToken()
	{
		return StaticObjects.token;
	}
	public static Product getProductToCreate() {
		return productToCreate;
	}
	public static void setProductToCreate(Product productToCreate) {
		StaticObjects.productToCreate = productToCreate;
	}
	//Set all null for logout
	public static void setStaticEmpty(){
		AllProducts = null;
		currentUser = null;
		selectedProduct = null;
		RequestStatus = 0;
		RequestMessage = null;
		userProducts = null;
		productToCreate = null;
		userWishlist = null;
	}
	
}
