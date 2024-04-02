package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

	@RequestMapping("/")
	public String mainPage() {
		
//		thymeleaf - before -> claspath:/templates/* after -> .html
		
		return "common/main";
	}
	
}
