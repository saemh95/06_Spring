package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
@RequestMapping("todo")
public class TodoController {

	@Autowired
	private TodoService service;
	
	/**
	 * @param todoTitle
	 * @param todoContent
	 * @param ra
	 * @return
	 */
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
	
	/**
	 * @param todoNo
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("detail")
	public String todoDetail(@RequestParam("todoNo") int todoNo, Model model, RedirectAttributes ra) {
		
		Todo todo = service.getContent(todoNo);
		
		String path = null;
		
		if(todo != null) {
			path = "todo/detail";
			model.addAttribute("todo", todo);
		} else {
			path = "redirect:/";
			ra.addFlashAttribute("message", "No Detail Found");
		}
		return path;
	}
	
	/**
	 * @param todo
	 * @param ra
	 * @return
	 */
	@GetMapping("changeComplete")
	public String changeComplete(Todo todo, RedirectAttributes ra) {
		
		int result = service.changeComplete(todo);
		String message = null;
		if (result>0) message = "Completed";
		else message = "Complete Error";
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:detail?todoNo=" + todo.getTodoNo();
	}
	
	@GetMapping("update")
	public String updateTodo(@RequestParam("todoNo") int todoNo, Model model) {
		
		Todo todo  = service.getContent(todoNo);
		
		model.addAttribute("todo", todo);
		
		return "todo/update";
	}
	
	@PostMapping("update")
	public String todoUpdate(Todo todo, RedirectAttributes ra) {

		int result = service.todoUpdate(todo);
		log.debug("todoTitle : " + todo.getTodoTitle());
		log.debug("content : " + todo.getTodoContent());
		String path = "redirect:";
		String message = null;
		
		if (result>0) {
			path += "detail?todoNo=" + todo.getTodoNo();
			message = "Update Complete";
		} else {
			path += "update?todoNo=" + todo.getTodoNo();
			message = "Update Error";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	@GetMapping("delete")
	public String deleteTodo(@RequestParam("todoNo") int todoNo, RedirectAttributes ra) {
		
		int result = service.todoDelete(todoNo);
		
		String path = null;
		String message = null;
		
		if (result>0) {
			path = "/";
			message = "Delete Complete";
		} else {
			path = "/todo/detail?todoNo=" + todoNo;
			message = "Delete Error";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}
	
	
}
