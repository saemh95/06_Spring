package edu.kh.todo.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.mapper.TodoMapper;

@Service
public class TodoServiceImpl implements TodoService{	
	@Autowired
	private TodoMapper mapper;

	@Override
	public Map<String, Object> selectAll() {
		
		Map<String, Object> todoMap = new HashMap<String, Object>();
		
		List<Todo> todoList = mapper.selectAll();;
		int count = mapper.getCompleteCount();
		
		todoMap.put("todoList", todoList);
		todoMap.put("count", count);
		
		return todoMap;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addTodo(String todoTitle, String todoContent) {
		
		Todo todo = new Todo();
		todo.setTodoTitle(todoTitle);
		todo.setTodoContent(todoContent);
		
		return mapper.addTodo(todo);
	}

	@Override
	public Todo getContent(int todoNo) {
		
		return mapper.getContent(todoNo);
	}

	@Override
	public int changeComplete(Todo todo) {
		
		return mapper.changeComplete(todo);
	}

	@Override
	public int todoUpdate(Todo todo) {
		
		return mapper.todoUpdate(todo);
	}

	@Override
	public int todoDelete(int todoNo) {
		
		return mapper.todoDelete(todoNo);
	}

	@Override
	public int getTotalCount() {
		
		return mapper.getTotalCount();
	}

	@Override
	public List<Todo> selectList() {
		
		return mapper.selectAll();
	}
	
}