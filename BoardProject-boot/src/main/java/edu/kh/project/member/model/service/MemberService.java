package edu.kh.project.member.model.service;

import java.util.List;

import edu.kh.project.member.model.dto.Member;

public interface MemberService {

	Member login(Member inputMember);

	int checkEmail(String memberEmail);

	int checkNickname(String memberNickname);

	int signup(Member inputMember, String[] memberAddress);

	Member quickLogin(Member inputMember);

	List<Member> selectMemberList();

	int resetPw(int resetMemberNo);

	int restorationMember(int memberNo);
	
}
