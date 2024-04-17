package edu.kh.project.mypage.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.mypage.model.dto.UploadFile;

public interface MypageService {

	int changePw(Map<String, Object> paramMap, int memberNo);

	int updateInfo(Member inputMember, String[] memberAddress);

	int secession(String memberPw, int memberNo);

	String fileUpload1(MultipartFile uploadFile) throws Exception;

	int insertUploadFile(MultipartFile uploadFile, int memberNo) throws IOException;

	List<UploadFile> fileList();

	int multipleFile(List<MultipartFile> aaaList, List<MultipartFile> bbbList, int memberNo) throws IOException;

	int profile(MultipartFile profileImg, Member loginMember)throws Exception;

	
}
