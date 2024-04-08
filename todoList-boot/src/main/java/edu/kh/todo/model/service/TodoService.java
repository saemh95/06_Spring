package edu.kh.todo.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.todo.model.dto.Todo;

public interface TodoService {


	Map<String, Object> selectAll();

	int addTodo(String todoTitle, String todoContent);

	Todo getContent(int todoNo);

	int changeComplete(Todo todo);

	int todoUpdate(Todo todo);

	int todoDelete(int todoNo);

	int getTotalCount();

	List<Todo> selectList();
	
}
