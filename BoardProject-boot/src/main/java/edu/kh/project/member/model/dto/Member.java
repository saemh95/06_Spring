package edu.kh.project.member.model.dto;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Member {

	private int memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberNickname;
	private String memberTel;
	private String memberAddress;
	private String profileImg;
	private String enrollDate;
	private String memberDelFl;
	
//	회원가입시 -> 일반 : D / 카카오 : K / 네이버 : N
	private String socialLoginType;
	
//	권환 -> 1: 일반회원 / 2: 관리자
	private int authority;
	
	
}
