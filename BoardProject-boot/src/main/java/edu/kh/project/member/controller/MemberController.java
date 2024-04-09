package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	@PostMapping("login")
	public String login(Member inputMember, RedirectAttributes ra, Model model) {
		
		Member loginMember = service.login(inputMember);
		
//		log.debug(inputMember.getMemberPw());
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "Login Error");
		} 
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
			//@SessionAttributes() -> to sessionScope
		}
		
		return "redirect:/";
	}
	
}
