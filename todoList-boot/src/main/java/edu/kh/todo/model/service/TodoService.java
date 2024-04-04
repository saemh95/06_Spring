package edu.kh.todo.model.service;

import java.util.Map;

public interface TodoService {


	Map<String, Object> selectAll();

	int addTodo(String todoTitle, String todoContent);
	
	
	
}
