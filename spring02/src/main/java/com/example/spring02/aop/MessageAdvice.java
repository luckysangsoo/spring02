package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


// Advice : 공통 업무를 지원하는 클래스
@Component // 기타 bean
@Aspect // aop bean
public class MessageAdvice {
	private static final Logger logger = LoggerFactory.getLogger(MessageAdvice.class);
	
	// @Before (호출전), @After (호출후), @Around(호출 전후)
	@Before("execution(* com.example.spring02.service.message.MessageService*.*(..))")
	public void startLog(JoinPoint jp){
		logger.info("클래스 : " + jp.getSignature());
		logger.info("method : " + jp.getSignature().getName());
		logger.info("매개 변수 : " + Arrays.toString(jp.getArgs()) );
	}
	

	@Around("execution(* com.example.spring02.service.message.MessageService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		Object result=pjp.proceed();
		long end = System.currentTimeMillis();
		logger.info(pjp.getSignature().getName() + ":" +(end-start));
		logger.info("====================================");
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
