package edu.kh.project.main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@PropertySource("classpath:/config.properties")
public class MainController {

	@Value("${my.public.data.service.key.decode}")
	private String serviceKeyDec;
	
	@GetMapping("/")
	public String mainPage() {
		return "common/main";
	}
	
	@GetMapping("/loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "Please login first");
		
		
		return "redirect:/";
	}
	
//	service key return
	@GetMapping("/getServiceKey")
	@ResponseBody
	public String getServiceKey() {
		return serviceKeyDec;
	}
	
}
