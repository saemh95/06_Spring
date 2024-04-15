package edu.kh.project.mypage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MypageMapper {

	int updateInfo(Member inputMember);

	String selectPw(int memberNo);

	int changePw(Map<String, Object> paramMap);

	int secession(int memberNo);

}
