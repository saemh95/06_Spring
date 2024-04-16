package edu.kh.project.mypage.model.service;

import java.io.File;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.mypage.model.mapper.MypageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService{

	private final MypageMapper mapper;

	private final BCryptPasswordEncoder bcrypt;
	
	
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
	
}
