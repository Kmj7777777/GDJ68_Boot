package com.winter.app.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
	Member Update 시 Password Valid 제외하기
*/
@Component
public class MemberValidInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {		
		/**
 			Parameter를 생성해서 추가하는 메서드가 존재하지 않아 불가능하다.
		*/
		return true;
	}
}