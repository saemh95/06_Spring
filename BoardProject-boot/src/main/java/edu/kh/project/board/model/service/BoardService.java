package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.board.model.dto.Board;

public interface BoardService {

	Map<String, Object> selectBoardList(int boardCode, int cp);

	Board selectOne(Map<String, Integer> map);

	List<Map<String, Object>> selectBoardTypeList();

	int boardLike(Map<String, Integer> map);

	int updateReadCount(int boardNo);

	
	
}
