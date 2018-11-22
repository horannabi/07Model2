package com.model2.mvc.service.cart.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartDao;
import com.model2.mvc.service.domain.Cart;


@Repository("cartDaoImpl")
public class CartDaoImpl implements CartDao {
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public CartDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addCart(Cart cart) throws Exception {
		sqlSession.insert("CartMapper.addCart", cart);
	}

	@Override
	public List<Cart> getCartList(Search search, String userCartId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("userCartId", userCartId);
		return sqlSession.selectList("CartMapper.getCartList", map);
	}

	@Override
	public void deleteCart(Cart cart) throws Exception {
		sqlSession.delete("CartMapper.deleteCart", cart);
	}

	@Override
	public int getTotalCount(String userCartId) throws Exception {
		return sqlSession.selectOne("CartMapper.getTotalCount", userCartId);
	}
	

}
