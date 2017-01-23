package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // 스프링에서 관리하는 bean
@Aspect    // 
public class LogAdvice {
	private static Logger logger= LoggerFactory.getLogger(LogAdvice.class);
	
	public Object logprint(ProceedingJoinPoint joinPoint) 
			throws Throwable {
		
		String type=joinPoint.getSignature().getDeclaringTypeName();
		String name="";
		logger.info(name+type+"."+joinPoint.getSignature().getName()+"()");
		logger.info(Arrays.toString(joinPoint.getArgs()));
		return joinPoint.proceed();
	}
	
	
}
