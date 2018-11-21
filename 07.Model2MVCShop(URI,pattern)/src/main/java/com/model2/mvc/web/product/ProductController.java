package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	/// Field
	Cookie cookie;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties , classpath:config/commonservice.xml
	// 참조 할것
	// ==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	/*@RequestMapping("/addProductView.do")
	public String addProduceView() throws Exception {
		System.out.println("addProductView.do");

		return "redirect:/product/addProduct.jsp";

	}*/
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception {
		System.out.println("/addProduct");

		return "redirect:/product/addProduct.jsp"; //왜 포워드
	}
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {
		System.out.println("/addProduct");
		productService.addProduct(product);

		return "forward:/product/addProduct.jsp"; //왜 포워드
	}

	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model, HttpServletResponse response, @CookieValue(value="history",required=false) String history) throws Exception { // 이부분 먹나 확인(-)
		System.out.println("/getProduct");
		Product product = productService.getProduct(prodNo);
		
		//if(cookie!=null&&cookie.getName().equals("history")) {
		if(history!=null) {
			//String history = cookie.getValue();
			System.out.println(history); //
			boolean boo = false;
			String[] h = history.split(",");
			for (int i = 0; i < h.length; i++) {
				if (h[i].equals(""+prodNo)) {
					boo = true;
					break;
				}
			} if(!boo) {
					cookie = new Cookie("history", history+","+prodNo);
					System.out.println(history+","+prodNo); //
				}
		}else {
			cookie = new Cookie("history", ""+prodNo);
			System.out.println(""+prodNo); //
		}
		System.out.println(cookie.getName().equals("history")); //
		cookie.setPath("/");
		response.addCookie(cookie);
		
		model.addAttribute("product", product);

		return "forward:/product/getProduct.jsp";	
	}
	
	/*@RequestMapping
	public String history() throws Exception {
		return "forward:/history.jsp";	
	}*/
	
	
	/*@RequestMapping
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		System.out.println("/updateProductView");
		
		//Product product = productService.getProduct(Integer.parseInt(prodNo));
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);

		return "forward:/product/updateProduct.jsp";
	}*/
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		System.out.println("/updateProductView");
		
		//Product product = productService.getProduct(Integer.parseInt(prodNo));
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);

		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception{
		System.out.println("/updateProduct");
		
		productService.updateProduct(product);
		
		return "redirect:/getProduct?prodNo="+product.getProdNo();
		//포워드하면 string array 오류?
	}


	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search , Model model) throws Exception{
		System.out.println("/listProduct");
	
	
	if(search.getCurrentPage() ==0 ){
		search.setCurrentPage(1);
	}
	search.setPageSize(pageSize);
	
	// Business logic 수행
	Map<String , Object> map=productService.getProductList(search);
	
	Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	System.out.println(resultPage);
	
	// Model 과 View 연결
	model.addAttribute("list", map.get("list"));
	model.addAttribute("resultPage", resultPage);
	model.addAttribute("search", search);
	
	return "forward:/product/listProduct.jsp";
	}
}
