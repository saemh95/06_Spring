package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Override
	public Member login(Member inputMember) {
		
//		String bCryptPw = bCrypt.encode(inputMember.getMemberPw());
//		log.debug(inputMember.getMemberPw());
//		log.debug("encoded : " + bCryptPw);
//		
//		boolean result = bCrypt.matches(inputMember.getMemberPw(), bCryptPw);
//		
//		log.debug("result : "+result);
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		if (loginMember == null) return null;
		
		if (!bCrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		} 
		
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
	
	
//	BCrypt -> Spring Security lib
	
//	salt -> ByCryptPasswordEncoder.matches(inputPw, encryptedPw)
	
	
	
}
