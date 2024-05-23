package edu.kh.project.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.kh.project.member.model.dto.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

	@Before("PointCutBundle.controllerPointCut()")
	public void beforeController(JoinPoint jp) {
		
//		get class name
		String className = jp.getTarget().getClass().getSimpleName();
//		get method name
		String methodName = jp.getSignature().getName() + "()";

//		get HttpServlet from request client
		HttpServletRequest req = ( (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes() ).getRequest();
		
		//		get Ip
		String ip = getRemoteAddr(req);
	
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format( "[%s.%s] Request / IP : %s" , className, methodName, ip) );
		
		if (req.getSession().getAttribute("loginMember") != null) {
			
			String memberEmail =((Member)req.getSession().getAttribute("loginMember")).getMemberEmail();
			
			sb.append(String.format(", Request User : %s",memberEmail));
			
		}
		
		log.info(sb.toString());
		
	}
	
	@Around("PointCutBundle.serviceImplPointCut()")
	public Object aroundServiceImpl(ProceedingJoinPoint pjp) throws Throwable{

//		@Before

//		get class name
		String className = pjp.getTarget().getClass().getSimpleName();
		
		//		get method name
		String methodName = pjp.getSignature().getName();

		log.info("--------------{}.{} Service ------------" , className, methodName);
		
//		parameter
		log.info("Parameter : {}", Arrays.toString(pjp.getArgs()));
		
		long startMs = System.currentTimeMillis();
		
		Object obj = pjp.proceed();
		
//		@After
		long endMs = System.currentTimeMillis();
		
		log.info("Running Time : {}ms", endMs - startMs);
		log.info("======================================");
		
		return obj;
	}
	
	@AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional) && PointCutBundle.serviceImplPointCut()", throwing = "ex")
	public void transactionRollback(JoinPoint jp, Throwable ex) {
		log.info("****** Transaction Rollback {} ******", jp.getSignature().getName());
		log.error("[Rollback Error] : {}", ex.getMessage());
	}
	
	
	
	
	
	/** 접속자 IP 얻어오는 메서드
	 * @param request
	 * @return ip
	 */
	private String getRemoteAddr(HttpServletRequest request) {
		String ip = null;
		ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
}
