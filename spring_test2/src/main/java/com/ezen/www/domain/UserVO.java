package com.ezen.www.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class UserVO {

	private String email;
	private String pwd;
	private String nickName;
	private String regDate;
	private String lastLogin;
	private List<AuthVO> authList;
	
}
