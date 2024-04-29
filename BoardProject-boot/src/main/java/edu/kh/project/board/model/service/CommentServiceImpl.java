package edu.kh.project.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService{

	private final CommentMapper mapper;

	@Override
	public List<Comment> select(int boardNo) {
		
		return mapper.select(boardNo);
	}

	@Override
	public int insert(Comment comment) {

		return mapper.insert(comment);
	}
	
}
