package com.winter.app.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO extends MemberInfoVO implements UserDetails, OAuth2User { // UserDetails 인터페이스 구현
	@NotBlank // == NOT NULL
	@Size(min = 2, max = 12, message = "2자 이상 12자 이하이어야 합니다.") 
	private String username;
	
	// @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\\\W)(?=\\\\S+$).{6,12}", message = "비밀번호를 입력해주세요.")
	private String password;
	
	private String passwordCheck;
	
	private Boolean enabled;
	
	private List<RoleVO> roles;
	
	private Map<String, Object> attributes;
	
	/*
		Spring Security는 hasRole과 같은 메서드를 처리할 때
		사용자가 해당 역할 및 권한을 가지고 있는지 아래 메서드를 통해 확인한다.
	*/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(RoleVO roleVO : roles) {
			authorities.add(new SimpleGrantedAuthority(roleVO.getRoleName()));
		}
		
		return authorities;
	}
	
	// ↓ 아래 메서드들의 반환값은 전부 true여야 한다.
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	
	// OAuth2User
	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}
}