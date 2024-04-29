package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {

	private final CommentService service;

	@GetMapping("")
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		
		return service.select(boardNo);
	}
	
	@PostMapping("")
	public int insert(@RequestBody Comment comment) {
		return service.insert(comment);
		
	}
	
	
}
