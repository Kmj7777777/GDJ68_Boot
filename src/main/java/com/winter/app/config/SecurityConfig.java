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
	Spring Security는 일반 Session 객체를 사용하지 않고,
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
		// 람다식 사용
		return web -> web
				.ignoring()
					.antMatchers("/css/**")
					.antMatchers("/img/**")
					.antMatchers("/js/**")
					.antMatchers("/vender/**");
	}
	
	/*
 		@Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return new WebSecurityCustomizer() {
	            @Override
	            public void customize(WebSecurity web) {
	                web.ignoring()
	                    .antMatchers("/css/**")
	                    .antMatchers("/img/**")
	                    .antMatchers("/js/**")
	                    .antMatchers("/vendor/**");
	            }
	        };
	    }
	*/
	
	/*
		SecurityFilterChain은 Servlet Filter 다음으로 실행된다고 한다.
		Request → Servlet Filter → SecurityFilterChain → Dispatcher Servlet → ...
	*/
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
				// .antMatchers("/notice/add").authenticated() // authenticated() : 로그인한 사용자는 허용
				.antMatchers("/notice/add").hasRole("ADMIN") // hasRole("ADMIN") : ADMIN 권한만 허용
				.antMatchers("/manager/*").hasAnyRole("ADMIN", "MANAGER") // hasAnyRole("ADMIN", "MANAGER") : ADMIN 또는 MANAGER 권한만 허용
				.antMatchers("/").permitAll() // permitAll() : 모두 허용
				.and()
			.formLogin()
				/*
					쉽게 설명하자면 Spring Security에게 "내 웹 애플리케이션의 로그인 페이지 경로는 /member/login이야~"라고 알려주는(지정) 작업이다.
					이제 지정한 로그인 페이지에서 자격 증명(사용자 아이디 및 비밀번호)을 제출하면 UserDetailsService 인터페이스를 구현한 Service 클래스에
					정의된 loadUserByUsername 메서드를 사용하여 사용자의 자격 증명을 처리(로그인 인증)한다. 
				*/
				.loginPage("/member/login")
				.defaultSuccessUrl("/") // 로그인 인증 성공 시 Redirect URL
				.failureUrl("/member/login") // 로그인 인증 실패 시 Redirect URL
				.permitAll() // ???
				.and()
			.logout()
				.logoutUrl("/member/logout") // 해당 경로로 요청이 오면 로그아웃 처리
				.logoutSuccessUrl("/") // 로그아웃 처리 후 Redirect URL
				.invalidateHttpSession(true); // 표준 세션과 Spring Security 세션 모두 무효화
				// .and()
			// .sessionManagement();
		
		return httpSecurity.build();
	}
}