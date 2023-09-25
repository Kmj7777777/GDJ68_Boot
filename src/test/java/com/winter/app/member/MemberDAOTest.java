package com.winter.app.member;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberDAOTest {
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	void test() throws Exception {
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername("admin");
		
		memberDAO.getMember(memberVO);
		log.info("Member : {}", memberVO);
		assertNotNull(memberVO);
	}
	
	@Test
	void test_passwordEncoder() throws Exception {
		String password = passwordEncoder.encode("user");
		log.info("password : {}", password);
	}
}