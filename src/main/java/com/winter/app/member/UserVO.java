package com.winter.app.member;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Spring Security는 Security Session 객체에 Member 정보를 저장할 때 DTO(또는 VO)를 저장하지 않고 UserDetails라는 타입을 저장한다.
// 아래와 같이 해도 되고 포함 관계로 해도 되고, 아니면 MemberVO 클래스에 바로 implements UserDetails를 해도 된다.
public class UserVO implements UserDetails {
	private MemberVO memberVO;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getPassword() {
		
		return null;
	}

	@Override
	public String getUsername() {
		
		return null;
	}

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
		
		return true;
	}
}