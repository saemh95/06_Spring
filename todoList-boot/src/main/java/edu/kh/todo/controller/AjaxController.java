package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("ajax")
@Controller
public class AjaxController {

	@Autowired
	private TodoService service;
	
	@GetMapping("main")
	public String ajaxMain() {
		
		
		
		return "/ajax/main";
	}
	
	@ResponseBody
	@GetMapping("totalCount")
	public int getTotalCount() {
		
//		Map <String, Object> selectAll = service.selectAll();
//		
//		int count = (int) selectAll.get("count");
		
		int totalCount = service.getTotalCount();
		
		return totalCount;
	}
	
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		
		Map <String, Object> map = service.selectAll();
		
		int count = (int) map.get("count");
		
		return count;
	}
	
	@ResponseBody
	@PostMapping("add")
	public int addTodo(@RequestBody Todo todo) {

//		log.debug(todo.toString());
		return service.addTodo(todo.getTodoTitle(), todo.getTodoContent());
	}
	
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		
		List<Todo> todoList = service.selectList();
		
		return todoList;
	}
	
	@ResponseBody
	@GetMapping("detail")
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {
		
		
		
		return service.getContent(todoNo);
	}
	
	@ResponseBody
	@DeleteMapping("delete")
	public int deleteTodo(@RequestBody int todoNo) {
		
		return service.todoDelete(todoNo);
		
	}
	
	@ResponseBody
	@PutMapping("change")
	public int changeComplete(@RequestBody Todo todo) {
//		log.debug("complete : " + todo.getComplete());
//		log.debug("todoNo : " + todo.getTodoNo());
		return service.changeComplete(todo);
	}
	
	@ResponseBody
	@PutMapping("update")
	public int updateView(@RequestBody Todo todo) {
		return service.todoUpdate(todo);
	}
}
