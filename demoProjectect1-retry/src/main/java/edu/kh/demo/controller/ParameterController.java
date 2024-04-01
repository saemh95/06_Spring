package edu.kh.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("param")
@Controller
@Slf4j
public class ParameterController {

	@GetMapping("main")
	public String paramMain() {
		
		
		return "param/param-main";
	}
	
	@PostMapping("test1")
	public String paramTest1(HttpServletRequest req) {
		
		String inputName = req.getParameter("inputName");
		int inputAge = Integer.parseInt( req.getParameter("inputAge") );
		String inputAddress = req.getParameter("inputAddress");
		
		log.debug("name : " + inputName);
		log.debug("age : " + inputAge);
		log.debug("address : " + inputAddress);
		
		return "redirect:/param/main";
	}
	
	
	@PostMapping("test2")
	public String paramTest2(@RequestParam(name="title", defaultValue="HELLO") String title, @RequestParam("author") String author, @RequestParam("price") int price, @RequestParam(value="publisher", required=false, defaultValue="1") String publisher) {

		String inputTitle = title;
		String inputAuthor = author;
		int inputPrice = price;
		
		
		log.debug("title : " + inputTitle);
		log.debug("author : " + inputAuthor);
		log.debug("price : "+inputPrice);
		log.debug("publisher : " + publisher);
		
		return "redirect:/param/main";
	}
	
}
