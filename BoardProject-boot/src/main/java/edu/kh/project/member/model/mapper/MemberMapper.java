package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(String memberEmail);

	int checkEmail(String memberEmail);

	int checkNickname(String memberNickname);

	int signup(Member inputMember);
	
}
