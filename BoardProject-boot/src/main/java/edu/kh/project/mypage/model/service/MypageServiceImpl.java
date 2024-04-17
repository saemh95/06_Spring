package edu.kh.project.mypage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.common.util.Utility;
import edu.kh.project.member.model.dto.Member;
import edu.kh.project.mypage.model.dto.UploadFile;
import edu.kh.project.mypage.model.mapper.MypageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MypageServiceImpl implements MypageService{

	private final MypageMapper mapper;

	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		if (inputMember.getMemberAddress().equals(",,")) {

			inputMember.setMemberAddress(null);
			
		} else {
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(address);
		}
		
		return mapper.updateInfo(inputMember);
	}

	public int changePw(Map<String, Object> paramMap, int memberNo) {
		String originPw = mapper.selectPw(memberNo);
		
		if (!bcrypt.matches((String)paramMap.get("currentPw"), originPw)) {
			return 0;
		}
		
		String encPw = bcrypt.encode((String)paramMap.get("newPw"));
		
		paramMap.put("encPw", encPw);
		paramMap.put("memberNo", memberNo);
		
		
		return mapper.changePw(paramMap);
	}
	
	

	@Override
	public int secession(String memberPw, int memberNo) {

		String originPw = mapper.selectPw(memberNo);
		
		if (!bcrypt.matches(memberPw, originPw)) {
			return 0;
		}
		
		return mapper.secession(memberNo);
	}

	@Override
	public String fileUpload1(MultipartFile uploadFile) throws Exception{

		if(uploadFile.isEmpty()) {
			return null;
		}
		uploadFile.transferTo(new File("C:\\uploadFiles\\temp\\" + uploadFile.getOriginalFilename()));
		
//		Server : C:/uploadFiles/test/a.jpg
//		web : /myPage/file/a.jpg
		
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}

	@Override
	public int insertUploadFile(MultipartFile uploadFile, int memberNo) throws IOException{

		
		if(uploadFile.isEmpty()) {
			return 0;
		}
//		folder to save file in server
		String folderPath = "C:/uploadFiles/temp/";
//		path to save file in web (client)
		String webPath = "/myPage/file/";
//		rename file -> utility class
		String fileRename = Utility.fileRemane(uploadFile.getOriginalFilename());
		
		UploadFile uf = UploadFile.builder().memberNo(memberNo).filePath(webPath)
						.fileOriginalName(uploadFile.getOriginalFilename())
						.fileRename(fileRename).build();
		
		
		int result = mapper.insertUploadFile(uf);
		
		if(result==0) return 0;
		
		uploadFile.transferTo(new File(folderPath + fileRename));
		
		
		return result;
	}

	@Override
	public List<UploadFile> fileList() {
		
		
		return mapper.fileList();
	}

	@Override
	public int multipleFile(List<MultipartFile> aaaList, List<MultipartFile> bbbList, int memberNo) throws IOException{

		int result1 = 0;
		
		for (MultipartFile file : aaaList) {
			if(file.isEmpty()) {
				continue;
			}
			result1 += insertUploadFile(file, memberNo);
		}
		
		int result2 = 0;
		
		for(MultipartFile file : bbbList) {
			if(file.isEmpty()) {
				continue;
			}
			result2 += insertUploadFile(file, memberNo);
			
		}
		
		
		return result1+result2;
	}

	@Override
	public int profile(MultipartFile profileImg, Member loginMember) throws Exception{

		int memberNo = loginMember.getMemberNo();
		
		String updatePath = null;
		String rename = null;
		
		if(!profileImg.isEmpty()) {
			rename = Utility.fileRemane(profileImg.getOriginalFilename());
			
			updatePath = profileWebPath+rename;
		}
		
		Member member = Member.builder().memberNo(memberNo).profileImg(updatePath).build();
		
		int result = mapper.profile(member);
		
		if(result>0) {
			if(!profileImg.isEmpty()) {
				profileImg.transferTo(new File(profileFolderPath + rename));
				
			}
			loginMember.setProfileImg(updatePath);
		}
		
		return result;
	}
	
}
