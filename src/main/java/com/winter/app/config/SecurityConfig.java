package com.winter.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private SecuritySuccessHandler handler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		// 비밀번호 해싱(암호화) 및 비교에 사용되는 객체
		return new BCryptPasswordEncoder();
	}
	
	/*
		webSecurityCustomizer 메서드는 WebSecurity 객체를 구성하는 초기 단계에서 실행된다.
		즉, securityFilterChain 메서드 이전에 실행된다.
		(전체적인 설정은 webSecurityCustomizer 메서드에서, 세부적인 설정은 securityFilterChain 메서드에서 해야 하는 것 같다.)
	*/
	// 람다식 사용
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
				// 보안 검사를 수행하지 않을 경로 설정
				.ignoring()
					.antMatchers("/css/**")
					.antMatchers("/img/**")
					.antMatchers("/js/**")
					.antMatchers("/vender/**");
	}
	
	/*
		// 람다식 사용 X
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
		SecurityFilterChain은 Servlet Filter 다음으로 실행된다.
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
				.loginPage("/member/login")//내장된 로그인폼을 사용하지 않고, 개발자가 만든 폼을 사용
				.defaultSuccessUrl("/")
				.failureUrl("/member/login")
				//.successHandler(handler)
				//.failureUrl("/member/login?message=LoginFail")
				.failureHandler(getFailHandler())
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/member/logout") // 해당 경로로 요청이 오면 로그아웃 처리
				// .logoutSuccessUrl("/") // 로그아웃 처리 후 Redirect URL
				.addLogoutHandler(getLogoutAdd())
				.logoutSuccessHandler(getLogoutHandler())
				.invalidateHttpSession(true) // HttpSession에 저장된 모든 데이터 제거 : Spring Security는 사용자 인증 및 권한과 관련된 데이터를 HttpSession에 저장하고 관리한다.
				.deleteCookies("JSESSIONID");
				// .and()
			// .sessionManagement();
		
		return httpSecurity.build();
	}
	
	private SecurityLogoutHandler getLogoutHandler() {
		return new SecurityLogoutHandler();
	}
	
	private SecurityLogoutAdd getLogoutAdd() {
		return new SecurityLogoutAdd();
	}
	
	private SecurityFailHandler getFailHandler() {
		return new SecurityFailHandler();
	}
}