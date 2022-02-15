package com.airfrance.testoffer.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("execution(* com.airfrance.testoffer.UserController.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("Received HTTP request in {}", joinPoint);
	}

	@AfterReturning(value = "execution(* com.airfrance.testoffer.UserController.*(..))", returning = "result")
	public void after(JoinPoint joinPoint, Object result) {
		logger.info("{} sent response {} ", joinPoint, result);
	}

	@AfterThrowing("execution(* com.airfrance.testoffer.UserController.*(..))")
	public void afterThrowing(JoinPoint joinPoint) {
		logger.info("{} threw an exception!", joinPoint);
	}
}
