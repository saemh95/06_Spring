package edu.kh.todo.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.todo.model.dto.Todo;

@Mapper
public interface TodoMapper {

	List<Todo> selectAll();

	int getCompleteCount();

	int addTodo(Todo todo);

}
