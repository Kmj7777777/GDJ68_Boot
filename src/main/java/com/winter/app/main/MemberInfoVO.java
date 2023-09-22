package com.winter.app.main;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MemberInfoVO {
	@NotBlank
	private String name;
	
	@Email // == 이메일 형식
	private String email;
	
	@Past // == 과거 날짜 여부
	private Date birth;
	
	private Date joinDate;
}