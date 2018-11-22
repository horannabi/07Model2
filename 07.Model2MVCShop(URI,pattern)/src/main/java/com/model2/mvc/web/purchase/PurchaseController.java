package com.model2.mvc.web.purchase;

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
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController() {
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
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET )
	public ModelAndView addPurchase(@RequestParam("prod_no") int prodNo) throws Exception{
		System.out.println("/addPurchase : GET");
		Product product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);

		return modelAndView;
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase, @RequestParam("prodNo") int prodNo, HttpSession session) throws Exception{
		System.out.println("/addPurchase : POST");
		Product product = productService.getProduct(prodNo);
		int prodAmount = product.getProdAmount();
		product.setProTranCode("1");
		product.setProdAmount(prodAmount-purchase.getTranAmount());
		productService.updateProduct(product);
		purchase.setTranCode("1");
		purchase.setPurchaseProd(product);
		purchase.setBuyer((User)session.getAttribute("user"));
		purchaseService.addPurchase(purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase")
	public String getPurchase(@RequestParam("tranNo") int tranNo, Model model)  throws Exception{
		System.out.println("/getPurchase");
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping("/getPurchase2")
	public String getPurchase2(@RequestParam("prodNo") int prodNo, Model model)  throws Exception{
		System.out.println("/getPurchase2");
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping("/updatePurchaseView")
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
	}
}