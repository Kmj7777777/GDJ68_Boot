package com.winter.app.member;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/*")
@Slf4j
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("info")
	public void getInfo() {
		
	}
	
	@GetMapping("logout")
	public String getLogout(HttpSession session) {
		session.invalidate();
		return "redirect:../";
	}
	
	@GetMapping("login")
	public String getLogin(@ModelAttribute MemberVO memberVO)throws Exception{
		SecurityContext context = SecurityContextHolder.getContext();
		String check = context.getAuthentication().getPrincipal().toString();
		
		log.info("========== Name : {} ==========", context.getAuthentication().getPrincipal().toString());
		
		if(!check.equals("anonymousUser")) {
			return "redirect:/";
		}
		
		return "member/login";
	}
	
	/*	
		- Spring Security로 대체 -
		
		@PostMapping("login")
		public String getLogin_post(@ModelAttribute MemberVO memberVO, HttpSession session) {
			memberVO = memberService.getLogin(memberVO);
			if(memberVO != null) {
				session.setAttribute("member", memberVO);
				return "redirect:../";
			}
			
			return "redirect:./login";
		}
	*/
	
	/*
		@GetMapping("join")
		public void setJoin(Model model) {
			MemberVO memberVO = new MemberVO();
			model.addAttribute("member", memberVO);
		}
	*/
	
	@GetMapping("join")
	public void setJoin(@ModelAttribute MemberVO memberVO) {
		
	}
	
	@PostMapping("join")
	public String setJoin(@Valid MemberVO memberVO, BindingResult bindingResult, MultipartFile photo) throws Exception {
		// @Valid 애너테이션은 Controller에서만 사용 가능하다.
		// memberService.testValid(memberVO, bindingResult);
		
		boolean check = memberService.getMemberError(memberVO, bindingResult);
		
		// memberVO 검증 결과가 bindingResult에 담김
		if(bindingResult.hasErrors() || check) {
			return "member/join";
		}
		
		memberService.setJoin(memberVO);
		
		return "redirect:../";
	}
	
	@GetMapping("update")
	public void setUpdate(@AuthenticationPrincipal MemberVO memberVO, Model model) {
		/*
			MemberVO memberVO = (MemberVO)session.getAttribute("member");
			memberVO = memberService.getLogin(memberVO);
		*/
		
		MemberInfoVO memberInfoVO = new MemberInfoVO();
		memberInfoVO.setName(memberVO.getName());
		memberInfoVO.setBirth(memberVO.getBirth());
		memberInfoVO.setEmail(memberVO.getEmail());
		
		model.addAttribute("memberInfoVO", memberInfoVO);
	}
	
	@PostMapping("update")
	public String setUpdate(@Valid MemberInfoVO memberInfoVO, BindingResult bindingResult) {
		MemberVO memberVO = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		memberVO.setEmail("UpdateEmail@naver.com");
		
		/*
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError error : errors) {
				log.info(error.getField());
			}
		*/
		
		return "redirect:/";
	}
}