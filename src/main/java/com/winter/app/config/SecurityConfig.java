package com.winter.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
	SecurityFilterChain은 일반 Filter의 뒤에서 작동함
	Filter → SecurityFilterChain → Dispatcher Servlet 
	
	※Spring Security는 일반 Session 객체를 사용하지 않고,
	  Session 객체에 Spring Security 전용 Session을 만들어놓고 그 Session을 사용한다.
*/

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Security를 거치지 않아야 하는 List
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
				.ignoring()
					.antMatchers("/css/**")
					.antMatchers("/img/**")
					.antMatchers("/js/**")
					.antMatchers("/vender/**");
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// 근데 MemberVO에서 해당 권한을 확인하는 건가? 그리고 그 권한이 있는지는 어떻게 확인하는 거지?
		httpSecurity
			.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
				// .antMatchers("/notice/add").authenticated() // authenticated : 로그인된 사람만 허용
				.antMatchers("/notice/add").hasRole("ADMIN") // ADMIN 권한은 허용
				.antMatchers("/manager/*").hasAnyRole("ADMIN", "MANAGER") // ADMIN 또는 MANAGER 권한을 가지고 있으면 허용
				.antMatchers("/").permitAll() // permitAll : 누구나 허용
				.and()
			// form 관련 설정
			.formLogin()
				.loginPage("/member/login") // login 로직을 처리하는(POST) path를 Spring Security에게 알려주기(Spring Security Filter가 해당 요청을 가로채서 MemberService(UserDetailsService)의 loadUserByUsername 메서드를 호출하여 처리함)
				.defaultSuccessUrl("/") // 성공했을 때 페이지
				.failureUrl("/member/login") // 실패 시 페이지
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/member/logout") // 해당 경로로 요청이 오면 로그아웃 처리가 시작됨
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true); // 세션 제거
				// .and()
			// .sessionManagement();
		
		return httpSecurity.build();
	}
}