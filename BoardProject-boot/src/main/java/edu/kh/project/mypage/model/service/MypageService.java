package edu.kh.project.mypage.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.member.model.dto.Member;

public interface MypageService {

	int changePw(Map<String, Object> paramMap, int memberNo);

	int updateInfo(Member inputMember, String[] memberAddress);

	int secession(String memberPw, int memberNo);


	
	
}
