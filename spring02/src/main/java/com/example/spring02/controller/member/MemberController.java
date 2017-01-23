package com.example.spring02.controller.member;


import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.member.dto.MemberVO;
import com.example.spring02.service.member.MemberService;

@Controller
//@RequestMapping("/memebr/*")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService memberService;
	
	@RequestMapping("member/login.do")
	public String login(){
		
		return "member/login"; //member/login.jsp 로 이동
	}
	
	@RequestMapping("member/loginCheck.do")
	public ModelAndView login_check(@ModelAttribute MemberVO vo, HttpSession session){
		boolean result = memberService.loginCheck(vo,session);
		ModelAndView mav = new ModelAndView();
		
		
		if( result == true){
			mav.setViewName("home"); //home.jsp 로 이동
			mav.addObject("message", "success");
			System.out.println("login_check() -> " + "userid : " +session.getAttribute("userid") + 
			" / username : "+ session.getAttribute("username"));
		}else{
			mav.setViewName("member/login"); //login.jsp 로 이동
			mav.addObject("message", "error");
		}
		return mav;
	}

	@RequestMapping("member/logout.do")
	public ModelAndView logout(HttpSession session){
		memberService.logout(session);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/login");
		mav.addObject("message", "logout");
		return mav;
	}
	
	
}
