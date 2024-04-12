package edu.kh.project.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("email")
@RequiredArgsConstructor // final || @NotNull
public class EmailController {

	private final EmailService service;
	
//	@Autowired -> field / setter / constructor
//	@RequiredArgsConstructor -> lombok
	
//	1) field 
//	@Autowired
//	private EmailService service;
	
//	2) setter
//	@Autowired
//	public void setService(EmailService service){
//		this.service = service;
//	}
	
//	3) constructor
//	private EmailService service;
//	private Memberservice service2;
	
//	@Autowired
//	public EmailController(EmailService service, MemberService service2) {
//		this.service = service;
//		this.service2 = service2;
//	}
	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		
		String authKey = service.sendEmail("signup", email);
		
		return 0;
	}
}
