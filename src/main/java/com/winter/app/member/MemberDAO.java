package com.winter.app.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	public MemberVO getMember(MemberVO memberVO);
	public int setJoin(MemberVO memberVO);
	public int setMemberRole(Map<String, Object> map);
}