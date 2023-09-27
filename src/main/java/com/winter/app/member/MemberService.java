package com.winter.app.member;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService extends DefaultOAuth2UserService implements UserDetailsService {
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/*
		소셜 로그인(OAuth 라이브러리가 Access Token을 받아오고, Access Token을 활용해 사용자 정보를 받아오는 작업을 대신 해준다.)
		Access Token이 만료되면 Refresh Token을 활용해 Access Token을 재발급받는 과정도 OAuth 라이브러리가 대신 해준다고 한다.
		즉, 개발자는 OAuth 라이브러리가 넘겨준 사용자 정보를 처리하는 작업에 집중만 하면 된다. 
	*/
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
		log.info("==================== 소셜 로그인 테스트 ====================");
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("==================== {} ====================", oAuth2User);
		
		String social = clientRegistration.getRegistrationId(); // kakao, naver 등...
		if(social.equals("kakao")) {
			oAuth2User = this.forKakao(oAuth2User);
		}else if(social.equals("naver")) {
			// ...
		}else if(social.equals("google")) {
			// ...
		}
		
		return oAuth2User;
	}
	
	@SuppressWarnings("unchecked")
	private OAuth2User forKakao(OAuth2User oAuth2User) {
		MemberVO memberVO = new MemberVO();
		// 결과 : {nickname=김민진, profile_image=http://.../img_640x640.jpg, thumbnail_image=http://.../img_110x110.jpg}
		LinkedHashMap<String, String> properties = oAuth2User.getAttribute("properties");
		// 결과 : {profile_nickname_needs_agreement=false, profile_image_needs_agreement=false, profile={nickname=김민진, thumbnail_image_url=http://.../img_110x110.jpg, profile_image_url=http://.../img_640x640.jpg, is_default_image=true}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=dngu_icdi@naver.com, has_birthday=true, birthday_needs_agreement=false, birthday=0728, birthday_type=SOLAR}
		LinkedHashMap<String, Object> kakao_account = oAuth2User.getAttribute("kakao_account");
		// 결과 : {nickname=김민진, thumbnail_image_url=http://.../img_110x110.jpg, profile_image_url=http://.../img_640x640.jpg, is_default_image=true}
		LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>)kakao_account.get("profile");
		
		String birthday = kakao_account.get("birthday").toString(); // 0728
		String month = birthday.substring(0, 2);
		String day = birthday.substring(2);
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(year).append("-").append(month).append("-").append(day);
		
		memberVO.setUsername(properties.get("nickname"));
		memberVO.setName(properties.get("nickname"));
		memberVO.setEmail(kakao_account.get("email").toString());
		memberVO.setBirth(Date.valueOf(stringBuffer.toString())); // 년도 정보는 필요 없기 때문에 아무 년도나 입력
		
		// oAuth2User.getAttributes() : 모든 정보를 다 가지고 있음(이후 추가적인 정보가 필요하다면 이것을 참조하면 될듯)
		memberVO.setAttributes(oAuth2User.getAttributes());
		
		List<RoleVO> roles = new ArrayList<>();
		RoleVO roleVO = new RoleVO();
		roleVO.setRoleName("ROLE_MEMBER");
		
		roles.add(roleVO);
		memberVO.setRoles(roles);
		
		log.info("username : {}", memberVO.getUsername());
		log.info("name : {}", memberVO.getName());
		log.info("email : {}", memberVO.getEmail());
		log.info("birth : {}", memberVO.getBirth());
		
		log.info("★★★★★★★★★★★★★★★★★★★★ {} ★★★★★★★★★★★★★★★★★★★★", oAuth2User.getAttribute("properties").getClass());
		
		log.info("★★★★★★★★★★★★★★★★★★★★ {} ★★★★★★★★★★★★★★★★★★★★", properties);
		log.info("★★★★★★★★★★★★★★★★★★★★ {} ★★★★★★★★★★★★★★★★★★★★", kakao_account);
		log.info("★★★★★★★★★★★★★★★★★★★★ {} ★★★★★★★★★★★★★★★★★★★★", profile);
		
		return memberVO;
		// memberVO.setUsername();
	}
	
	// My Server 로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // username : 파라미터명(다를 경우 변경하는 설정을 해주어야 함)
		log.info("==================== 로그인 시도 중 ====================");
		
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		memberVO = memberDAO.getMember(memberVO);
		
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