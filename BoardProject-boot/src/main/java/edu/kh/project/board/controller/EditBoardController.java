package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.service.EditBoardService;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("editBoard")
public class EditBoardController {

	private final EditBoardService service;
	
	
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode) {
		
		
		return "board/boardWrite";
	}
	
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode, @ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember, @RequestParam("images") 
			List<MultipartFile> images, RedirectAttributes ra) throws Exception{
		
		int memberNo = loginMember.getMemberNo();
		
		inputBoard.setBoardCode(boardCode);
		inputBoard.setMemberNo(memberNo);
		
		int boardNo = service.boardInsert(inputBoard, images);
		
		String path = null;
		String message = null;
		
		if (boardNo > 0) {
			path = "/board/"+boardCode+"/"+boardNo;
			message = "Board Created";
		} else {
			path = "insert";
			message = "Board Create Error";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:"+path;
	}
	
	
}
