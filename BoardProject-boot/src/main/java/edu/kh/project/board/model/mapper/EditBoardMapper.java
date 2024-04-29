package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	int boardInsert(Board inputBoard);

	int insertUploadList(List<BoardImg> uploadList);

	int boardUpdate(Board inputBoard);

	int deleteImage(Map<String, Object> map);

	int updateImage(BoardImg img);

	int insertImage(BoardImg img);

}
