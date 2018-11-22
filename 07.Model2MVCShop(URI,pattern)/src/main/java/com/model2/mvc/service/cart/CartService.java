package com.model2.mvc.service.cart;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Cart;
import com.model2.mvc.service.domain.Purchase;

public interface CartService {
	//INSERT
	public void addCart(Cart cart) throws Exception;
		
	//SELECT LIST
	public Map<String, Object> getCartList(Search search, String userCartId) throws Exception;
		
	//DELETE
	public void deleteCart(Cart cart) throws Exception;

	
}