package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Java Object : new object()-> heap
// instance -> object / dev direct manage
// Bean -> Spring container manage

@Controller
public class TestController {
	
	@RequestMapping("test") // /test request -> respond without get/post method
	public String testMethod() {
		System.out.println("/test respond");
		
		
//		class -> requestmapping
		/* @RequestMapping("todo")
		 * @Controller
		 * public class TodoController {
		 * 
		 * 		@RequestMapping("insert")
		 * 		public String insert() {} 
		 * 
		 * }
		 * 
		 * Thymeleaf -> JSP change template engine
		 * classpath : /templates/XXX.html
		 * classpath -> src/main/resources
		 * */
			
		return "test";	
	}

}