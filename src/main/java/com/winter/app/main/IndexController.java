package com.winter.app.main;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	@GetMapping("/")
	public String getIndex(HttpSession session) {
		//Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication b = context.getAuthentication();
		
		log.info("============ GetName : {} ============ ", b.getName());
		
		log.info("============ Principa : {} ============ ", b.getPrincipal());
		
		log.info("============ Object : {} ============ ", b.getAuthorities());
		
		return "index";
	}
}