package com.ezen.www.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ezen.www.domain.UserVO;
import com.ezen.www.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthUserService implements UserDetailsService {

	@Inject
	private UserDAO udao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username : 로그인을 시도한 email(SecurityConfig - usernameParameter 값을 가져온것)
		//UserDetails : 인증용 객체
		//return UserDetails => 성공/실패 로 나뉨
		//비밀번호는 알아서 비교 및 결과를 출력
		//username이 DB에 있는지 확인하여 인증용 객체를 리턴
		log.info("username >> {}",username);
		UserVO uvo = udao.selectEmail(username);
		log.info("userDetails >> {}",uvo);
		if(uvo==null) {
			throw new UsernameNotFoundException(username);
		}
		uvo.setAuthList(udao.selectAuths(username));
		log.info("userDetails >> {}",uvo);
		return new AuthUser(uvo);
	}

}
