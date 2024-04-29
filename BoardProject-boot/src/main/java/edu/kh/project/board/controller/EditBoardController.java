package edu.kh.project.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.kh.project.board.model.service.BoardService;
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
	private final BoardService boardService;
	
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
	
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
			RedirectAttributes ra, @SessionAttribute("loginMember") Member loginMember, Model model) {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		Board board = boardService.selectOne(map);
		
		String message = null;
		String path = null;
		
		if( board==null ) {
			message = "No Board";
			path = "redirect:/";
			
			ra.addFlashAttribute("message", message);
			
		} else if ( loginMember.getMemberNo() != board.getMemberNo()) {
			message = "Can't Edit board";
			path = String.format("redirect:/board/%d/%d", boardCode, boardNo);
			
			ra.addFlashAttribute("message", message);
		} else {
			path = "board/boardUpdate";
			model.addAttribute("board", board);
		}
		
		return path;
	}
	
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
			@ModelAttribute Board inputBoard, @SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images, RedirectAttributes ra, 
			@RequestParam(value = "deleteOrder", required=false) String deleteOrder,
			@RequestParam(value = "querystring", required=false, defaultValue = "") String querystring) throws Exception{
		
		inputBoard.setBoardCode(boardCode);
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		int result = service.boardUpdate(inputBoard, images, deleteOrder);
		
		String message = null;
		String path = null;
		
		if(result>0) {
			message = "Board Updated";
			path = String.format("/board/%d/%d%s", boardCode, boardNo, querystring);
		} else {
			message = "Board Update Error";
			path = "update";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
}
