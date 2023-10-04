package com.winter.app.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.winter.app.board.PostVO;
import com.winter.app.member.MemberVO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class SecurityLogoutAdd implements LogoutHandler {
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String adminKey;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// this.useRestTemplate();
		// this.logoutForKakao(authentication);
		// this.logoutForKakao(response);
		this.useWebClient();
	}
	
	public void useRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		
		// 파라미터
		/*
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("postId", "1");
		*/
		
		// HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, null);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, null);
		
		/*
			(결과가 하나일 때)
			ResponseEntity<PostVO> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/1", PostVO.class, request); // 두 번째 매개변수 : 응답 타입
			PostVO postVO = response.getBody();
		*/
		
		/*
			(결과가 여러 개일 때)
			@SuppressWarnings("unchecked")
			List<PostVO>  response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", List.class, request);
		*/
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/comments?postId=1", String.class, request);
		
		log.info("==================== RestTemplate : {} ====================", response);
	}
	
	private void logoutForKakao(Authentication authentication) {
		RestTemplate restTemplate = new RestTemplate();
		MemberVO memberVO = (MemberVO)authentication.getPrincipal();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		// headers.add("Authorization", "Bearer " + memberVO.getAccessToken());
		headers.add("Authorization", "KakaoAK bb86067d806d9c89982919695f983c45"); // headers.add("Authorization", "KakaoAK " + adminKey);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
		params.add("target_id", memberVO.getName());
		
		// HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers); // (파라미터, 헤더)
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/logout", request, String.class); // GET 방식과는 두세 번째 매개변수 위치가 다름
		
		String result = response.getBody();
		log.info("●●●●●●●●●●●●●●●●●●●● 로그아웃 ID : {} ●●●●●●●●●●●●●●●●●●●●", result);
	}
	
	// 위 방법은 이유는 모르겠지만 카카오 계정 자체는 로그아웃이 되지 않는다.
	private void logoutForKakao(HttpServletResponse response) {
		/*
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity("https://kauth.kakao.com/oauth/logout?client_id=70b76695bf923d9118a8a3ad2aa085ed&logout_redirect_uri=http://localhost:82/member/kakaoLogout", String.class);
			String result = response.getBody();
			log.info("●●●●●●●●●●●●●●●●●●●● 카카오 계정과 함께 로그아웃 : {} ●●●●●●●●●●●●●●●●●●●●", result);
		*/
		
		// 위 방법은 잘 안 됨, Redirect 방식으로 요청해야 하는 것 같다.
		try {
			response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id=70b76695bf923d9118a8a3ad2aa085ed&logout_redirect_uri=http://localhost:82/member/kakaoLogout");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// RestTemplate은 추후에 Deprecated될 예정, 앞으로는 WebClient라는 것을 사용
	private void useWebClient() {
		WebClient webClient = WebClient.builder()
									  .baseUrl("https://jsonplaceholder.typicode.com/")
									  .build();
		// Mono : 결과가 1개인 경우
		// Flux : 결과가 여러 개인 경우
		Mono<ResponseEntity<PostVO>> response = webClient.get()
																	.uri("posts/1")
																	.retrieve()
																	.toEntity(PostVO.class);
		
		PostVO postVO = response.block().getBody();
		log.info("#################### WebClient : {} ####################", postVO);
	}
}