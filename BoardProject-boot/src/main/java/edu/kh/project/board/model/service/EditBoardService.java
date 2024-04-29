package edu.kh.project.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.dto.Board;

public interface EditBoardService {

	int boardInsert(Board inputBoard, List<MultipartFile> images) throws Exception;

	int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrder) throws Exception;

}
