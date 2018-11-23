package com.model2.mvc.web.cart;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartService;
import com.model2.mvc.service.domain.Cart;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


@Controller
@RequestMapping("/cart/*")
public class CartController {
	
	///Field
	@Autowired
	@Qualifier("cartServiceImpl")
	private CartService cartService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public CartController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	/*@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prod_no") int prodNo) throws Exception{
		System.out.println("/addPurchaseView.do");
		Product product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);

		return modelAndView;
	}*/
	
	@RequestMapping(value="addCart", method=RequestMethod.GET )
	public String addCart(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		System.out.println("/addCart : GET");
		//ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/cart/addCartView.jsp";
	}
	
	@RequestMapping(value="addCart", method=RequestMethod.POST)
	public String addCart(@ModelAttribute("cart") Cart cart, @RequestParam("cartProdNo") int cartProdNo, HttpSession session) throws Exception{
		System.out.println("/addCart : POST"); 
		Product cartProd = productService.getProduct(cartProdNo);
		cart.setCartProd(cartProd);
		cart.setCartUserId(((User)session.getAttribute("user")).getUserId());
		cart.setCartCode("1");
		cartService.addCart(cart);
		return "redirect:/cart/listCart";
	}
	
	@RequestMapping(value="listCart")
	public String listCart(@ModelAttribute("search")Search search, HttpSession session, Model model) throws Exception{
		System.out.println("/listCart");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		User user = (User)session.getAttribute("user");
		// Business logic 수행
		//Map<String , Object> map=purchaseService.getPurchaseList(search);
		Map<String , Object> map=cartService.getCartList(search, user.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/cart/listCart.jsp";
	}
	
	@RequestMapping(value="deleteCart")
	public String deleteCart(@RequestParam("prodNo") int prodNo, HttpSession session, Model model) throws Exception{
		System.out.println("/deleteCart");
		//ProductService productService = new ProductServiceImpl();
		Cart cart = new Cart();
		Product cartProd=new Product();
		cartProd.setProdNo(prodNo);
		cart.setCartProd(cartProd);
		cart.setCartUserId(((User)session.getAttribute("user")).getUserId());
		
		//model.addAttribute("cart", cart);
		cartService.deleteCart(cart);
		
		return "redirect:/cart/listCart";
	}
	/*@RequestMapping("/updatePurchaseView")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception{
		System.out.println("/updatePurchaseView");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
	@RequestMapping("/updatePurchase")
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception{
		System.out.println("/updatePurchase");
		
		purchaseService.updatePurchase(purchase);
		
		return "redirect:/purchase/getPurchase?tranNo="+purchase.getTranNo();
	}
	
	@RequestMapping("/listPurchase")
	public String listProduct(@ModelAttribute("search")Search search , HttpSession session, Model model) throws Exception{
		System.out.println("/listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		User user = (User)session.getAttribute("user");
		// Business logic 수행
		//Map<String , Object> map=purchaseService.getPurchaseList(search);
		Map<String , Object> map=purchaseService.getPurchaseList(search, user.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping("/updateTranCode")
	public String updateTranCode(@RequestParam("tranCode") String tranCode, @RequestParam("tranNo") int tranNo) throws Exception{
		System.out.println("/updateTranCode");
		Purchase purchase = purchaseService.getPurchase(tranNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		return "redirect:/purchase/listPurchase";
	}
	@RequestMapping("/updateTranCodeByProd")
	public String updateTranCodeByProd(@RequestParam("tranCode") String tranCode, @RequestParam("purchaseProd.prodNo") int prodNo) throws Exception{
		System.out.println("/updateTranCodeByProd");
		
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCodeByProd(purchase);
		return "redirect:/product/listProduct?menu=manage";
	}*/
}