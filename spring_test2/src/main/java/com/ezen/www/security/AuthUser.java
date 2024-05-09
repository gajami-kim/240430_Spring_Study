package com.ezen.www.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ezen.www.domain.UserVO;

import lombok.Getter;

@Getter
//다른 곳(header)에서 호출하기 위해 getter 추가
public class AuthUser extends User{
	
	private static final long serialVersionUID = 1L;
	private UserVO uvo;

	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	//권한은 Collection 객체로 리턴, AuthList를 Collection으로 값이 나오도록 변경해줘야함
	//map의 authVO의 실제 AuthVO가 아니라 매개변수 이름을 나타냄
	public AuthUser(UserVO uvo) {
		super(uvo.getEmail(), uvo.getPwd(), 
				uvo.getAuthList().stream()
				.map(authVO -> 
				new SimpleGrantedAuthority(authVO.getAuth()))
				.collect(Collectors.toList()));
		
		//닉네임이나 추가적으로 들어갈 그 외 정보(UserVO에 있는)를 받기 위해 추가
		this.uvo = uvo;
	}

}
