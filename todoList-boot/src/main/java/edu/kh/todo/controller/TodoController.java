package edu.kh.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.service.TodoService;

@Controller
@RequestMapping("todo")
public class TodoController {

	@Autowired
	private TodoService service;
	
	@PostMapping("add")
	public String addTodo(@RequestParam("todoTitle") String todoTitle, @RequestParam("todoContent") String todoContent, RedirectAttributes ra) {

		int result = service.addTodo(todoTitle, todoContent);
		
		String message = null;
		
		if (result>0) {
			message = "Todo Add Completed";
		} else {
			message = "Todo Add Error";
		}
		
//		change scope to session from req before redirecting
		ra.addFlashAttribute("message", message);
		
		return "redirect:/";
	}
	
	
}
