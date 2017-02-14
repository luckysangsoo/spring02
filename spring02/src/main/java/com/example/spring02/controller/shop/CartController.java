package com.example.spring02.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
		cartService.insert(vo);
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

}
