package com.winter.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityPasswordEncoder {
	@Bean
	PasswordEncoder passwordEncoder() {
		// 비밀번호 해싱(암호화) 및 비교에 사용되는 객체
		return new BCryptPasswordEncoder();
	}
}