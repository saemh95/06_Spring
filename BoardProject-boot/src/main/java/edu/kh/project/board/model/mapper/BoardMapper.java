package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	List<Map<String, Object>> selectBoardTypeList();

	int getListCount(int boardCode);

	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);

	Board selectOne(Map<String, Integer> map);

	int boardLikeDelete(Map<String, Integer> map);

	int boardLikeInsert(Map<String, Integer> map);

	int selectLikeCount(int temp);

	int updateReadCount(int boardNo);

	int selectReadCount(int boardNo);

}
