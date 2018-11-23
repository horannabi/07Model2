package com.model2.mvc.service.domain;

public class Cart {
	private Product cartProd;	//name, price
	private int cartNo;
	private String cartUserId;
	private int cartAmount;
	private String cartCode;
	
	
	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Product getCartProd() {
		return cartProd;
	}

	public void setCartProd(Product cartProd) {
		this.cartProd = cartProd;
	}

	

	public int getCartNo() {
		return cartNo;
	}

	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}

	public String getCartUserId() {
		return cartUserId;
	}


	public void setCartUserId(String cartUserId) {
		this.cartUserId = cartUserId;
	}


	public int getCartAmount() {
		return cartAmount;
	}


	public void setCartAmount(int cartAmount) {
		this.cartAmount = cartAmount;
	}


	public String getCartCode() {
		return cartCode;
	}


	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}

	@Override
	public String toString() {
		return "Cart [cartProd=" + cartProd + ", cartNo=" + cartNo + ", cartUserId=" + cartUserId + ", cartAmount="
				+ cartAmount + ", cartCode=" + cartCode + "]";
	}

	
}
