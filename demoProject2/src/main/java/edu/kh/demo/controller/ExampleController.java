package edu.kh.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.demo.model.dto.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("example")
@Controller
@Slf4j
public class ExampleController {


	@GetMapping("ex1")
	public String ex1(HttpServletRequest req, Model model) {
		
//		log.debug("hi");
		req.setAttribute("test1", "HttpServletRequest -> sent value");
		
		model.addAttribute("test2", "Model -> sent value");
		
		model.addAttribute("productName", "Paper Cup");
		model.addAttribute("price", 2000);
		
		List<String> fruitList = new ArrayList<>();
		
		fruitList.add("Apple");
		fruitList.add("Pear");
		fruitList.add("Banana");
		
//		log.debug(fruitList.toString());
		model.addAttribute("fruitList", fruitList);
		
		Student std = new Student();
		
		
		std.setStdAge(6);
		std.setStdNo("111");
		std.setStdName("fish");
		
		Student std1 = new Student("321","squid",12);
		Student std2 = new Student("456","octopus",10);
		Student std3 = new Student("15135","Shrimp",18);
		
		List<Student> stdList = new ArrayList<>();
		stdList.add(std1);
		stdList.add(std2);
		stdList.add(std3);
		
		model.addAttribute("stdList", stdList);
		
		model.addAttribute("std", std);
		
//		log.debug(std.toString());
		
		
		return "example/ex1";
	}
	
	@PostMapping("ex2")
	public String ex2(HttpServletRequest req, Model model) {
		
//		List<String> colorList
//		String color = req.getParameter("color");
//		String[] color2 = req.getParameterValues("color");
//		
//		for (String c : color2) {
//			
//			log.debug(c.toString());
//		}
		log.debug("hi");
		model.addAttribute("str", "<h1>Testing &times;</h1>");
		return "example/ex2";
	}
	
	
}
