package com.example.spring02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor 
	extends HandlerInterceptorAdapter{
	
	// Ctrl+Space : 입력 preH
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 세션 객체 생성
		HttpSession session=request.getSession();
		if(session.getAttribute("userid") == null){
			// 세션이 없으면 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath()+"/member/login.do?message=nologin");
			// 컨트롤러를 실행하지 않음
			return false;
		}else{ // 컨트롤러를 실행 함
			return true;
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	

}
