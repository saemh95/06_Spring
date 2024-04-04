package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@Autowired
	private TodoService service;
	
	
	@GetMapping("/")
	public String mainPage(Model model) {
		
		log.debug("service : " + service);
		
		Map<String, Object> todoMap = service.selectAll();
		
		List<Todo> todoList = (List<Todo>) todoMap.get("todoList");
		int count = (int) todoMap.get("count");
		
		model.addAttribute("todoList", todoList);
		model.addAttribute("count", count);
		
		return "common/main";
	}
	
	
}
