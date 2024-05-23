package edu.kh.project.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// normal Bean
@Component
//@Aspect
@Slf4j
public class TestAspect {

	// advice : advice code
	// Pointcut : where Advice will be implemented in Joinpoint
	
	// <how to use Pointcut>
	// execution([졉근제한자] return type classname methodname ([parameter]) )
	// * classname = full package name
	
	// main anotation
	// - @Aspect : To appoint it as an aspect to a class
	// - @Before : run Advice before current method
	// - @After : run Advice after current method
	// - @Around : run Advice before/after current method
	
	//@Before(Pointcut)
	//@Before("execution( * edu.kh.project..*Controller*.*(..) )")
	public void testAdvice() {
		log.info("----------- testAdvice() running -----------");
	}
	
	@After("execution( * edu.kh.project..*Controller*.*(..) )")
	public void controllerEnd(JoinPoint jp) {
		// JoinPoint : AOP running
		
		// get class name
		String className = jp.getTarget().getClass().getSimpleName();
		// get method name
		String methodName = jp.getSignature().getName();
		
		log.info("------------- {}.{} Running End -------------" , className, methodName);
		
	}
	
}
