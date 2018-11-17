package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {

	//구매 추가
	public void addPurchase(Purchase purchase) throws Exception;
	
	//구매 정보 보기(판매코드)
	public Purchase getPurchase(int tranNo) throws Exception;
	
	//구매 정보 보기(상품 번호)
	public Purchase getPurchase2(int ProdNo) throws Exception;
	
	//구매 리스트
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception;
	
	public Map<String,Object> getSaleList(Search search) throws Exception;
	
	//구매 정보 수정
	public void updatePurchase(Purchase purchase) throws Exception;
	
	//판매 코드 수정 구매자
	public void updateTranCode(Purchase purchase) throws Exception;
	
	//판매 코드 수정 판매자
	public void updateTranCodeByProd(Purchase purchase) throws Exception;
}