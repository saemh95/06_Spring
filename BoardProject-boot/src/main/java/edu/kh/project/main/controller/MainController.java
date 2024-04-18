package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MainController {

	@GetMapping("/")
	public String mainPage() {
		return "common/main";
	}
	
	@GetMapping("/loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "Please login first");
		
		
		return "redirect:/";
	}
	
}
