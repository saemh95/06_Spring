package edu.kh.demo.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.demo.model.dto.MemberDTO;
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
	
	@PostMapping("test3")
	public String postMethodName(@RequestParam(value="color", required=false) String[] colorArr, @RequestParam(value="fruit", required=false) List<String> fruitList, @RequestParam Map<String, Object> paramMap) {

		log.debug("color : " + Arrays.toString(colorArr));
		log.debug("fruit : " + fruitList);
		log.debug("param : " + paramMap);
		
		return "redirect:/param/main";
	}
	
	@PostMapping("test4")
	public String postMethodName(/* @ModelAttribute */MemberDTO inputMember) {

//		@ModelAttribute 
		
//		@ModelAttribute -> DTO if name="value" same as DTO name="value" -> .setValue
//		MemberDTO inputMember -> command object
//		@annotation -> doesn't have to filled out
		
		log.debug(inputMember.toString());
		
		return "redirect:/param/main";
	}
	
	
	
}
