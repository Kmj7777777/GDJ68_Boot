package com.winter.app.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class MemberService {
	@Autowired
	MemberDAO memberDAO;
	
	public MemberVO getLogin(MemberVO memberVO) {
		MemberVO loginVO = memberDAO.getMember(memberVO);
		
		if(loginVO == null) {
			return loginVO;
		}
		
		if(loginVO.getPassword().equals(memberVO.getPassword())) {
			return loginVO;
		}
		
		return null;
	}
	
	// 사용자 정의 검증 : 검증 애너테이션으로 검증하지 못하는 것들을 처리
	public boolean getMemberError(MemberVO memberVO, BindingResult bindingResult) {
		boolean check = false;
		
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			check = true;
			bindingResult.rejectValue("passwordCheck", "memberVO.password.equalCheck");
		}
		
		MemberVO checkVO = memberDAO.getMember(memberVO);
		
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("userName", "memberVO.userName.equalCheck");
		}
		
		return check;
	}
}