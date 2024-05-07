package com.ezen.www.service;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.UserVO;
import com.ezen.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService{

	private final UserDAO udao;

	@Override
	public int register(UserVO uvo) {
		return udao.register(uvo);
	}
	
}
