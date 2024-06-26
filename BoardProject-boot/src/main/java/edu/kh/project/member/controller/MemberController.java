package edu.kh.project.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@SessionAttributes({"loginMember"})
@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	@GetMapping("quickLogin")
	public String quickLogin(Model model, Member inputMember, RedirectAttributes ra) {
		
		Member loginMember = service.quickLogin(inputMember);
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "Login Error");
		}
		if(loginMember != null) {			
			model.addAttribute("loginMember", loginMember);
		}
		return "redirect:/";
	}
	
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
//		log.debug("loginMember : " + loginMember);
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
	
//	signup btn form
	@PostMapping("signup")
	public String singup(Member inputMember, @RequestParam("memberAddress") String[] memberAddress, RedirectAttributes ra) {
		
		int result = service.signup(inputMember, memberAddress);
		
		String path = null;
		String message = null;
		
		if (result > 0 ) {
			path = "/";
			message = inputMember.getMemberNickname() + " Welcome To our Family :)";
		} else {
			path = "signup";
			message = "Signup Error";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		return service.checkEmail(memberEmail);
		
	}
	
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String memberNickname) {
		
		
		return service.checkNickname(memberNickname);
	}
	
	@ResponseBody
	@GetMapping("selectMemberList")
	public List<Member> selectMemberList() {
		
		return service.selectMemberList();
		
	}
	
	@ResponseBody
	@PutMapping("resetPw")
	public int resetPw(@RequestBody int resetMemberNo) {
	
		int result = service.resetPw(resetMemberNo);
		return result;
	}
	
	@ResponseBody
	@PutMapping("restorationMember")
	public int restorationMember(@RequestBody int restorationMember) {
		
		return service.restorationMember(restorationMember);
	}
	
	
}
