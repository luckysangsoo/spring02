package com.example.spring02.controller.shop;

import java.io.File;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.ProductVO;
import com.example.spring02.service.shop.ProductService;

@Controller
@RequestMapping("/shop/product/*")
public class ProductController {
	
	@Inject
	ProductService productService;
	
	@RequestMapping("/list.do")
	public ModelAndView list(ModelAndView mav){
		mav.setViewName("/shop/product_list");
		mav.addObject("list", productService.listProduct());
		return mav;
	}
	
	
	// http://localhost:8080/spring02/shop/product/detail/6
	@RequestMapping("/detail/{product_id}")
	public ModelAndView detail(
			@PathVariable("product_id") int product_id,
			ModelAndView mav){
		mav.setViewName("shop/product_detail");
		mav.addObject("vo", productService.detailProduct(product_id));
		return mav;
	}
	
	@RequestMapping("write.do")
	public String write(){
		// views/shop/product_write.jsp 로 이동
		return "shop/product_write";
	}
	
	@RequestMapping("insert.do")
	public String insert(ProductVO vo){
		String filename="";
		if(!vo.getFile1().isEmpty()){ // 첨부 파일이 있으면
			filename = vo.getFile1().getOriginalFilename();
			// 파일 업로드 경로
			// src\main\WEB-INF\view\images 디렉토리 마우스 오른쪽 클릭 -> properties 값 읽어서
			// images\\ 디렉토리에 \\ 슬래쉬를 붙여준다.
			String path = "C:\\Users\\shim\\git\\spring02\\spring02\\src\\main\\webapp\\WEB-INF\\views\\images\\";
			
			try {
				new File(path).mkdir();
				// 서버의 임시 디렉토리에 저장되고 지정된 디렉토리로 이동				
				vo.getFile1().transferTo(new File(path+filename));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setPicture_url(filename);
			productService.insertProduct(vo);
			
		}
		return "redirect:/admin/product/list.do";
	}

}
