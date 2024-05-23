package edu.kh.project.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

// Spring error hierarchy

// 1. Controller Try/Catch (throws)
// 2. @Exception Handler (class)
// 3. class -> project @ControllerAdvice

@ControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler(NoResourceFoundException.class)
	public String notFound() {
		return "error/404";
	}
	
	@ExceptionHandler(Exception.class)
	public String allExceptionHandler(Exception e, Model model) {
		
		e.printStackTrace();
		
		model.addAttribute("e", e);
		
		return "error/500";
	}
	
}
