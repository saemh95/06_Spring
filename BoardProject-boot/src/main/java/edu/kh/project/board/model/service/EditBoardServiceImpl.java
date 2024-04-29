package edu.kh.project.board.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.exception.BoardInsertException;
import edu.kh.project.board.model.exception.ImageDeleteException;
import edu.kh.project.board.model.exception.ImageUpdateException;
import edu.kh.project.board.model.mapper.EditBoardMapper;
import edu.kh.project.common.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class EditBoardServiceImpl implements EditBoardService{

	private final EditBoardMapper mapper;
	
	@Value("${my.board.web-path}")
	private String webPath; 
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) throws Exception{

//		1) insert board table
		int result = mapper.boardInsert(inputBoard);
		
		if(result==0) return 0;
		
		int boardNo = inputBoard.getBoardNo();
		
//		log.debug("boardNo : " + boardNo);
//		2) img
		List<BoardImg> uploadList = new ArrayList<>();
		
		for (int i=0;i<images.size();i++) {
			
			if(!images.get(i).isEmpty()) {
				String originalName = images.get(i).getOriginalFilename();
				String rename = Utility.fileRemane(originalName);
				
				BoardImg img = BoardImg.builder().imgPath(webPath).imgOriginalName(originalName)
				.imgRename(rename).imgOrder(i).boardNo(boardNo).uploadFile(images.get(i)).build();
				
				uploadList.add(img);
				
			}
			
		}
		
		if(uploadList.isEmpty()) {
			return boardNo;
		}
		
		result = mapper.insertUploadList(uploadList);
		
		if(result == uploadList.size()) {
			
			for (BoardImg img : uploadList) {
				img.getUploadFile().transferTo(new File(folderPath+img.getImgRename()));
			}
			
		} else {
			throw new BoardInsertException("Error Uploading Image");
		}
		
		
		return boardNo;
	}

	@Override
	public int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrder) throws Exception{
		
		int result = mapper.boardUpdate(inputBoard);
		
		if (result == 0) return 0;
		
		if (deleteOrder != null && !deleteOrder.equals("")) {
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("deleteOrder", deleteOrder);
			map.put("boardNo", inputBoard.getBoardNo());
			
			result = mapper.deleteImage(map);
			
			if (result==0) {
				throw new ImageDeleteException();
			}
			
		}
		
		List<BoardImg> uploadList = new ArrayList<>();
		
		for (int i=0;i<images.size();i++) {
			
			if(!images.get(i).isEmpty()) {
				String originalName = images.get(i).getOriginalFilename();
				String rename = Utility.fileRemane(originalName);
				
				BoardImg img = BoardImg.builder().imgPath(webPath).imgOriginalName(originalName)
				.imgRename(rename).imgOrder(i).boardNo(inputBoard.getBoardNo()).uploadFile(images.get(i)).build();
				
				uploadList.add(img);
				
				result = mapper.updateImage(img);
				
				if(result==0) {
					result = mapper.insertImage(img);
				}
				
			}
			
			if (result==0) {
				throw new ImageUpdateException();
			}
			
			
		}
		if(uploadList.isEmpty()) {
			return result;
		}
		
		for (BoardImg img : uploadList) {
			img.getUploadFile().transferTo(new File(folderPath+img.getImgRename()));
		}
		
		return result;
	}
	
}
