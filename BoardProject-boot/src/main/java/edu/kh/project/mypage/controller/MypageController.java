package edu.kh.project.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.mypage.model.service.MypageService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;


@Slf4j
@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MypageController {

	private final MypageService service;
	
	@GetMapping("profile")
	public String profilePage() {
		
		
		return "myPage/myPage-profile";
	}
	
	@GetMapping("info")
	 public String info(
             @SessionAttribute("loginMember")Member loginMember, 
             Model model) {      
 
		 // 주소만 꺼내옴
		 String memberAddress = loginMember.getMemberAddress();
		 
		 // 주소가 있을 경우에만 동작
		 if(memberAddress != null) {
		    // 구분자 "^^^"를 기준으로 
		    // memberAddress 값을 쪼개어 String[]로 반환 
		    String[] arr = memberAddress.split("\\^\\^\\^");
		    
		    // ex) "04540^^^서울 중구 남대문로 120^^^3층 E 강의장"
		    // -->["04540","서울 중구 남대문로 120", "3층 E 강의장"]
		    model.addAttribute("postcode",arr[0]);
		    model.addAttribute("address",arr[1]);
		    model.addAttribute("detailAddress",arr[2]);
		 }
		 
		 return "myPage/myPage-info";
	}
	
	@PostMapping("info")
	   public String updateInfo(Member inputMember, 
	                     @SessionAttribute("loginMember") Member loginMember,
	                     @RequestParam("memberAddress")String[] memberAddress,
	                     RedirectAttributes ra) {
	      
	      // inputMember에 로그인한 회원번호 추가 
	      int memberNo = loginMember.getMemberNo();
	      inputMember.setMemberNo(memberNo);
	      
	      // 회원 정보 수정 서비스 호출 
	      int result = service.updateInfo(inputMember,memberAddress);
	      
	      String message = null;
	      
	      if(result > 0) {
	         
	         message = "회원 정보가 수정 되었습니다.";
	         
	         // loginMember는 
	         // session에 저장된 로그인한 회원 정보가 저장된 객체를 참조하고 있다. 
	         
	         // -> loginMember를 수정하면 
	         //      세션에 저장된 로그인한 회원 정보가 수정된다! 
	         
	         // == 세션 데이터와 DB데이터를 맞춤 
	         
	         loginMember.setMemberNickname(inputMember.getMemberNickname() );
	         
	         loginMember.setMemberTel(inputMember.getMemberTel() );
	         
	         loginMember.setMemberAddress(inputMember.getMemberAddress() );
	         
	      } else {
	         message = "회원 정보 수정 실패";
	      }
	      
	      ra.addFlashAttribute("message",message);
	      
	      return "redirect:info";
	      
	   }
	
	@GetMapping("changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	
	@PostMapping("changePw")
	public String changePw (@RequestParam Map<String, Object> paramMap, @SessionAttribute("loginMember") Member loginMember, RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.changePw(paramMap, memberNo);
		
		String path = null;
		String message = null;
		
		if(result>0) {
			path = "/myPage/info";
			message = "Password Changed";
		} else {
			path = "/myPage/changePw";
			message = "Current Password is not a match";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	@PostMapping("secession")
	public String secession(@RequestParam("memberPw") String memberPw, 
			@SessionAttribute("loginMember") Member loginMember, 
			RedirectAttributes ra, SessionStatus status) {
		
//		log.debug("loginMember : " + loginMember);
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.secession(memberPw, memberNo);
		
		String message = null;
		String path = null;
		
		if (result>0) {
			message = "User Deleted";
			path = "/";
			status.setComplete();
		} else {
			message = "Password Incorrect";
			path = "secession";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	@GetMapping("fileTest")
	public String fileTest() {
		
		return "myPage/myPage-fileTest";
	}
	
	@GetMapping("fileList")
	public String fileList() {
		return "myPage/myPage-fileList";
	}
	
	@PostMapping("file/test1")
	public String fileUpload1(@RequestParam("uploadFile") MultipartFile uploadFile, RedirectAttributes ra) {
		
		try {
			
			String path = service.fileUpload1(uploadFile);
			
			if(!path.isBlank()) {
				ra.addFlashAttribute("path", path);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return "redirect:/myPage/fileTest";
	}
	
}
