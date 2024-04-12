package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@SessionAttributes({"loginMember"})
@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	/**
	 * @param inputMember
	 * @param ra
	 * @param model
	 * @return
	 */
	@PostMapping("login")
	public String login(Member inputMember, RedirectAttributes ra, Model model, 
						@RequestParam(value="saveId", required= false) String saveId,
						HttpServletResponse resp) {
//		checkbox -> checked : "on" / unchecked : null
		
		Member loginMember = service.login(inputMember);
		
//		log.debug(inputMember.getMemberPw());
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "Login Error");
		} 
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
			//@SessionAttributes() -> to sessionScope
			
//			save ID -> cookie
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			
			cookie.setPath("/");
			
			if (saveId != null) {
				cookie.setMaxAge(60*60*24*30);
			} else {
				cookie.setMaxAge(0);
			}
			resp.addCookie(cookie);
			
		}
		
		return "redirect:/";
	}
	
	/**
	 * @param status
	 * @return 
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:/";
	}
	
	@GetMapping("signup")
	public String signup() {
		
		return "member/signup";
	}
	
	@PostMapping("signup")
	public String singup() {
		return "redirect:/";
	}
	
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		return service.checkEmail(memberEmail);
		
	}
	
	
	
}
