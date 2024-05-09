package com.ezen.www.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.www.domain.UserVO;
import com.ezen.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService{

	private final UserDAO udao;
	final BCryptPasswordEncoder passwordEncoder;
	
	

	@Transactional
	@Override
	public int register(UserVO uvo) {
		//권한 추가
		int isOk = udao.register(uvo);
		return udao.insertAuthInit(uvo.getEmail());
	}

	@Override
	public List<UserVO> getUserList() {
		List<UserVO> userList = udao.getList();
		for(UserVO uvo : userList) {
			uvo.setAuthList(udao.selectAuths(uvo.getEmail()));
		}
		return userList;
	}

	@Override
	public void modify(UserVO uvo) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		
		//principal이 UserVO의 값이 아니기때문에 값을 찾을 수 없음
		//UserVO oriUvo = (UserVO) authentication.getPrincipal();
		log.info("pw 수정 oriUvo>>{}", authentication.getPrincipal());
		if(uvo.getPwd()==null||uvo.getPwd().length()==0) {
			//uvo.setPwd(oriUvo.getPwd());
			udao.modifynick(uvo);
		} else {
			String setPw = passwordEncoder.encode(uvo.getPwd());
			uvo.setPwd(setPw);
			udao.modify(uvo);
		}
		log.info("pw 수정 uvo>>{}", uvo);
	}

	@Transactional
	@Override
	public int delete(String email) {
		int isOk = udao.deleteAuth(email);
		if(isOk>0) { udao.delete(email); }
		return isOk;
	}

	@Override
	public UserVO checkEmail(String email) {
		return udao.selectEmail(email);
	}

	@Override
	public int checknick(String nickName) {
		return udao.checknick(nickName);
	}
	
}
