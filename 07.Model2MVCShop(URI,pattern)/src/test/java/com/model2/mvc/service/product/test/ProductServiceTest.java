package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
											"classpath:config/context-aspect.xml",
											"classpath:config/context-mybatis.xml",
											"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProductName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("2018-10-18");
		product.setPrice(1000000);
		product.setFileName("testProductFile");
		
		productService.addProduct(product);
		
		//product = productService.getProduct(product.getProdNo());

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("2018-10-18", product.getManuDate());
		Assert.assertEquals(1000000, product.getPrice());
		Assert.assertEquals("testProductFile", product.getFileName());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> �ʿ��ϴٸ�...
		//user.setUserId("testUserId");
		//user.setUserName("testUserName");
		//user.setPassword("testPasswd");
		//user.setSsn("1111112222222");
		//user.setPhone("111-2222-3333");
		//user.setAddr("��⵵");
		//user.setEmail("test@test.com");
		
		product = productService.getProduct(10027);

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		
		//Assert.assertEquals("testProductName", product.getProdName());
		//Assert.assertEquals("testProdDetail", product.getProdDetail());
		//Assert.assertEquals("20181018", product.getManuDate());
		//Assert.assertEquals(1000000, product.getPrice());
		//Assert.assertEquals("testProductFile", product.getFileName());

		Assert.assertNotNull(productService.getProduct(10000));
		Assert.assertNotNull(productService.getProduct(10001));
		
	}
	
	//@Test
	 public void testUpdateUser() throws Exception{
		 
		Product product = productService.getProduct(10066);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20181018", product.getManuDate());
		Assert.assertEquals(1000000, product.getPrice());
		Assert.assertEquals("testProductFile", product.getFileName());

		product.setProdName("changeProductName");
		product.setProdDetail("changeProdDetail");
		product.setPrice(100);
		product.setFileName("changeProductFile");
	
		productService.updateProduct(product);
		
		product = productService.getProduct(10066);
		Assert.assertNotNull(product);	
		
		//==> console Ȯ��
		System.out.println(product);
			
		//==> API Ȯ��
		Assert.assertEquals("changeProductName", product.getProdName());
		Assert.assertEquals("changeProdDetail", product.getProdDetail());
		Assert.assertEquals("20181018", product.getManuDate());
		Assert.assertEquals(100, product.getPrice());
		Assert.assertEquals("changeProductFile", product.getFileName());
	 }
	 

	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println("product list all ��Żī��� : "+totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	System.out.println("product list all ����Ʈ : "+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println("product list all 3�� �� ����Ʈ ���� : "+totalCount);
	 }
	 
	@Test
		 public void testGetProductListByProductN0() throws Exception{
			 
			 Search search = new Search();
			 	search.setCurrentPage(1);
			 	search.setPageSize(3);
			 	//search.setSearchCondition("1");
			 	//search.setSearchKeyword("�Ｚ");
			 	search.setSearchPriceCondition("0");
			 	Map<String,Object> map = productService.getProductList(search);
			 	
			 	List<Object> list = (List<Object>)map.get("list");
			 	Assert.assertEquals(18, list.size());
			 	
				//==> console Ȯ��
			 	System.out.println(list);
			 	
			 	Integer totalCount = (Integer)map.get("totalCount");
			 	System.out.println(totalCount);
			 	
			 	System.out.println("=======================================");
			 	
			 	//search.setSearchCondition("1");
			 	//search.setSearchKeyword("��ǰ");	//����ð� �� �Է�?
			 	//map = productService.getProductList(search);
			 	
			 	//list = (List<Object>)map.get("list");
			 	//Assert.assertEquals(3, list.size());
			 	
				//==> console Ȯ��
			 	//System.out.println(list);
			 	
			 	//totalCount = (Integer)map.get("totalCount");
			 	//System.out.println(totalCount);
		 }
	 
	 //@Test
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("changeProductName");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());	//����ð� �� �Է�?
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByPrice() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("100");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
	 
}