package edu.kh.project.mypage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;

public interface MypageService {

	int changePw(Map<String, Object> paramMap, int memberNo);

	int updateInfo(Member inputMember, String[] memberAddress);

	int secession(String memberPw, int memberNo);

	String fileUpload1(MultipartFile uploadFile) throws Exception;


	
	
}
