package edu.kh.project.board.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.member.model.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	
	/**
	 * @param boardCode : 게시판 종류 구분 번호 
	 * @param cp : 현재 조회를 요청한 페이지 (없으면 1)
	 * @return
	 *
	 * - /board/xxx 
	 * 	 /board 이하 1레벨 자리에 숫자로된 요청 주소가 
	 * 	 작성되어 있을 때만 동작 -> 정규표현식 이용 
	 * 
	 * [0-9] : 한 칸에 0~9사이 숫자 입력 가능 
	 * + : 하나 이상 
	 * 
	 * [0-9]+ : 모든 숫자 
	 * 
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(@PathVariable("boardCode") int boardCode, @RequestParam(value="cp", required=false, defaultValue="1") int cp, Model model) {
		
//		log.debug("boardCode : " + boardCode);
		
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
//		 log.debug("map : "+map);
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		return "board/boardList";
	}
	
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo
			, Model model, RedirectAttributes ra, @SessionAttribute(value="loginMember", required=false) Member loginMember,
			HttpServletRequest req, HttpServletResponse resp
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		
		Board board = service.selectOne(map);
		
		String path = null;
		
		if(board == null) {
			path = "redirect:/board/" + boardCode;
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
		} else {
			
			if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				
				for(Cookie temp : cookies) {
					
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
						break;
					}
				}
				
				int result = 0;
				
				if(c == null) {
					c = new Cookie("readBoardNo", "[" + boardNo + "]");
					result = service.updateReadCount(boardNo);
				} else {
					if(c.getValue().indexOf("[" + boardNo + "]") == -1) {
						c.setValue(c.getValue()+"[" + boardNo + "]");
						result = service.updateReadCount(boardNo);
					}
				}
				
				if (result > 0) {
					board.setReadCount(result);
					
					c.setPath("/");
					
					LocalDateTime now = LocalDateTime.now();
					
					LocalDateTime nextDay = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					
					long secondsUntilNextDay = Duration.between(now, nextDay).getSeconds();
					
					c.setMaxAge((int) secondsUntilNextDay);
					
					resp.addCookie(c);
				}
				
			}
			
			
			
			path = "board/boardDetail";
			
//			board -> 게시글 일반 내용 + imageList + commentList
			model.addAttribute("board", board);
			
			if(!board.getImageList().isEmpty()) {
				BoardImg thumbnail = null;
				
				if(board.getImageList().get(0).getImgOrder() == 0) {
					thumbnail = board.getImageList().get(0);
				}
				model.addAttribute("thumbnail", thumbnail);
				model.addAttribute("start", thumbnail != null ? 1 : 0);
			}
		}
		
		return path;
	}
	
	@ResponseBody
	@PostMapping("like")
	public int boardLike(@RequestBody Map<String, Integer> map) {
		
		return service.boardLike(map);
	}
	
}
