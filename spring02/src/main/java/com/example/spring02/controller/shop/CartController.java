package com.example.spring02.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.CartVO;
import com.example.spring02.service.shop.CartService;

@Controller
@RequestMapping("/shop/cart/*")
public class CartController {
	@Inject
	CartService cartService;
	
	@RequestMapping("insert.do")
	// public ModelAndView insert(
	public String insert(
			@ModelAttribute CartVO vo, 
			//ModelAndView mav,
			HttpSession session){
		String userid=(String)session.getAttribute("userid");
		vo.setUserid(userid);
		// 장바구니에 기존 상품이 있는지 검사
		int count=cartService.countCart(userid, vo.getProduct_id());
		if(count==0){
			// 없으면 insert
			cartService.insert(vo);
		}else{
			cartService.updateCart(vo);
		}
		//mav.setViewName("shop/cart_list");
		//return mav;
		return "redirect:/shop/product/list.do";
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(HttpSession session,
			ModelAndView mav){
		Map<String,Object> map=new HashMap<>();
		// 세션에 저장된 userid
		String userid=(String)session.getAttribute("userid");
		List<CartVO> list=cartService.listCart(userid);
		
		int sumMoney=cartService.sumMoney(userid);
		// 배송료( 3만원 이상 =? 무료, 미만 => 2500원 )
		int fee=sumMoney>=30000 ? 0 : 2500;
		map.put("list", list);
		map.put("count", list.size());
		map.put("sumMoney", sumMoney);
		
		map.put("fee", fee);
		
		// 총합계(sumMoney + 배송료)
		map.put("sum", sumMoney+fee);
		mav.setViewName("shop/cart_list");
		mav.addObject("map", map);
		return mav;
	}
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam int cart_id){
		cartService.delete(cart_id);
		return "redirect:/shop/cart/list.do";
	}
	
	/*@RequestMapping("update.do")
	public String update(int amount[], int[] product_id,
			HttpSession session){
	public String update(HttpServletRequest request, HttpSession session){
	
		// 레코드 갯수
		// <input type="hidden" name="count" value="${map.count}"> - cart_list.jsp
		int count=Integer.parseInt(request.getParameter("count"));
		System.out.println("==============count : " + count);
		String userid = (String)session.getAttribute("userid");
		
		for(int i=0; i<count; i++){
			int amount=Integer.parseInt(request.getParameter("amount["+i+"]"));
			int product_id=Integer.parseInt(request.getParameter("product_id["+i+"]"));
			CartVO vo = new CartVO();
			vo.setUserid(userid);
			//vo.setProduct_id(product_id[i]);
			vo.setProduct_id(product_id);
			//vo.setAmount(amount[i]);
			vo.setAmount(amount);
			cartService.modifyCart(vo);
		}
		return "redirect:/shop/cart/list.do";
	}*/

	@RequestMapping("update.do")
	public String update(@RequestParam int[] amount, @RequestParam int[] product_id, HttpSession session){
		String userid = (String)session.getAttribute("userid");
		
		for(int i=0; i<product_id.length; i++){
			CartVO vo = new CartVO();
			vo.setUserid(userid);
			vo.setProduct_id(product_id[i]);
			vo.setAmount(amount[i]);
			cartService.modifyCart(vo);
		}
		return "redirect:/shop/cart/list.do";
	}


}
