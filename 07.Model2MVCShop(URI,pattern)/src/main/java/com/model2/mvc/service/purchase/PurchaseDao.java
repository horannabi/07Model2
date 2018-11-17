package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {
	//INSERT
	public void addPurchase(Purchase purchase) throws Exception;
	
	//SELECT ONE(판매코드)
	public Purchase getPurchase(int tranNo) throws Exception;
	
	//SELECT ONE(상품 번호)
	public Purchase getPurchase2(int prodNo) throws Exception;
	
	//SELECT LIST
	public List<Purchase> getPurchaseList(Search search,String buyerId) throws Exception;
	
	public List<Purchase> getSaleList(Search search) throws Exception;
	
	//UPDATE
	public void updatePurchase(Purchase purchase) throws Exception;
	
	//코드 UPDATE 구매자
	public void updateTranCode(Purchase purchase) throws Exception;
	
	//코드 UPDATE 판매자
	public void updateTranCodeByProd(Purchase purchase) throws Exception;
	
	// 게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(String buyerId) throws Exception ;
}
