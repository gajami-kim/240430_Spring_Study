package com.ezen.www.service;

import java.util.List;

import com.ezen.www.domain.UserVO;

public interface UserService {

	int register(UserVO uvo);

	List<UserVO> getUserList();

	void modify(UserVO uvo);

	int delete(String email);

	UserVO checkEmail(String email);

	int checknick(String nickName);

}
