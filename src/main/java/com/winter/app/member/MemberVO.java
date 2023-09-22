package com.winter.app.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.winter.app.main.MemberInfoVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO extends MemberInfoVO {
	@NotBlank // == NOT NULL
	@Size(min = 2, max = 12, message = "2자 이상 12자 이하이어야 합니다.") 
	private String userName;
	
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\\\W)(?=\\\\S+$).{6,12}", message = "비밀번호를 입력해주세요.")
	private String password;
	
	private String passwordCheck;
}