package edu.kh.project.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public int checkEmail(String memberEmail) {
		
		return mapper.checkEmail(memberEmail);
	}

	@Override
	public int checkNickname(String memberNickname) {
		
		return mapper.checkNickname(memberNickname);
	}

	@Override
	public int signup(Member inputMember, String[] memberAddress) {

		if (!inputMember.getMemberAddress().equals(",,")) {
			
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(address);
			
		} else {
			inputMember.setMemberAddress(null);
		}
		
		String inputPw = bCrypt.encode(inputMember.getMemberPw());
		
		inputMember.setMemberPw(inputPw);
		
		return mapper.signup(inputMember);
	}

	
	@Override
	public Member quickLogin(Member inputMember) {
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		if (loginMember == null) return null;
		
		loginMember.setMemberPw(null);
		
		return loginMember;
	}

	@Override
	public List<Member> selectMemberList() {
		
		return mapper.selectMemberList();
	}

	@Override
	public int resetPw(int memberNo) {

		String enc = bCrypt.encode("pass01!");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("enc", enc);
		map.put("memberNo", memberNo);
	
//		log.debug("memberNo(service) : " + memberNo);
		
		return mapper.resetPw(map);
	}

	@Override
	public int restorationMember(int memberNo) {
		
		return mapper.restorationMember(memberNo);
		
	}
	
	
//	BCrypt -> Spring Security lib
	
//	salt -> ByCryptPasswordEncoder.matches(inputPw, encryptedPw)
	
	
	
}
