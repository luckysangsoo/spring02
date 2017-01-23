package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // 스프링에서 관리하는 bean
@Aspect    // 
public class LogAdvice {
	private static Logger logger= LoggerFactory.getLogger(LogAdvice.class);
	
	@Around("execution"+ 
	        "(* com.example.spring02.controller..*Controller.*(..))"
+ " or execution(* com.example.spring02.service..*Impl.*(..))"
+ " or execution(* com.example.spring02.model..dao.*Impl.*(..))")
	public Object logprint(ProceedingJoinPoint joinPoint) 
			throws Throwable {
		
		String type=joinPoint.getSignature().getDeclaringTypeName();
		String name="";
		if(type.indexOf("Controller") > -1){
			name = "Controller \t: ";	
		} else if (type.indexOf("Service") > -1){
			name = "ServiceImpl \t";
		} else if (type.indexOf("DAO") > -1){
			name = "DAO \t";
		}
			
		logger.info(name+type+"."+joinPoint.getSignature().getName()+"()");
		logger.info(Arrays.toString(joinPoint.getArgs()));
		return joinPoint.proceed();
	}
	
	
}
