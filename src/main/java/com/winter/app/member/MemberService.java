package com.winter.app.member;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService implements UserDetailsService {
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// /member/login POST 대신 처리
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // username : 파라미터명(다를 경우 변경하는 설정을 해주어야 함)
		log.info("==================== 로그인 시도 중 ====================");
		
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		try {
			memberVO = memberDAO.getMember(memberVO);
		}catch (Exception e) {
			memberVO = null;
			e.printStackTrace();
		}
		
		return memberVO; // 반환 시점에 Spring Security가 입력된 패스워드와 memberVO의 패스워드를 비교 후 Spring Security Session에 저장해준다고 한다.
	}
	
	/*
		public MemberVO getLogin(MemberVO memberVO) throws Exception {
			MemberVO loginVO = memberDAO.getMember(memberVO);
			
			if(loginVO == null) {
				return loginVO;
			}
			
			if(loginVO.getPassword().equals(memberVO.getPassword())) {
				return loginVO;
			}
			
			return null;
		}
	*/
	
	// 사용자 정의 검증 : 검증 애너테이션으로 검증하지 못하는 것들을 처리
	public boolean getMemberError(MemberVO memberVO, BindingResult bindingResult) throws Exception {
		boolean check = false;
		
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			check = true;
			bindingResult.rejectValue("passwordCheck", "memberVO.password.equalCheck");
		}
		
		MemberVO checkVO = memberDAO.getMember(memberVO);
		
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("username", "memberVO.username.equalCheck");
		}
		
		return check;
	}
	
	/*
		public void testValid(@Valid MemberVO memberVO, BindingResult bindingResult) {
			log.info("Test Valid");
		}
	*/
	
	@Transactional(rollbackFor = Exception.class)
	public int setJoin(MemberVO memberVO) {
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
		int result = memberDAO.setJoin(memberVO);
		Map<String, Object> map = new HashMap<>();
		map.put("roleNum", 3);
		map.put("username", memberVO.getUsername());
		result = memberDAO.setMemberRole(map);
		
		return result;
	}
}