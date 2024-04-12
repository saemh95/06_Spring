package edu.kh.project.email.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	private final EmailMapper mapper;
	private final JavaMailSender mailSender;
	
	@Override
	public String sendEmail(String htmlName, String email) {
		
		String authKey = createAuthKey();
		
		try {
//			title
			String subject = null;
			switch(htmlName) {
			case "signup" : 
				subject = "[boardProject] Signup Verification Code";
				break;
			}
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(email);
			helper.setSubject(subject);
			
			helper.setText(authKey);
			
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
	* @return authKey
	*/
	public String createAuthKey() {
		String key = "";
		for(int i=0 ; i< 6 ; i++) {
			int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
			
			if(sel1 == 0) {
				
				int num = (int)(Math.random() * 10); // 0~9
				
				key += num;
				
			}else {
				
				char ch = (char)(Math.random() * 26 + 65); // A~Z

				int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
				
				if(sel2 == 0) {
					ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
					}
				key += ch;
				}
			}
		return key;
	}

//	Google SMTP (Simple Mail Transfer Protocol)
//	Java Mail Sender -> Google SMTP
}
