package com.ezen.www.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.UserVO;
import com.ezen.www.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/**")
@Controller
public class UserController {

	private final UserService usv;
	private final BCryptPasswordEncoder bcEncoder;
	
	//controller mapping과 jsp 경로가 같으면 void 가능
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(UserVO uvo) {
		log.info("uvo >>> {}",uvo);
		uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
		int isOk = usv.register(uvo);
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	//로그인 행위X
	@PostMapping("/login")
	public String loginPost(HttpServletRequest request, RedirectAttributes re) {
		//로그인 실패 시 다시 로그인 페이지로 돌아와서 오류메시지를 전송 역할
		//다시 로그인 유도
//		log.info(request.getAttribute("errMsg"));
		re.addAttribute("email", request.getAttribute("email"));
		re.addAttribute("errMsg", request.getAttribute("errMsg"));
		return "redirect:/user/login";
	}
	
	@GetMapping("/list")
	public String userlist(Model m) {
		List<UserVO> userList = usv.getUserList();
		m.addAttribute("userList", userList);
		return "/user/list";
	}
	
	@GetMapping("/modify")
	public void detail(String email) {}
	
	@PostMapping("/modify")
	public String modify(UserVO uvo, HttpServletRequest request, HttpServletResponse response) {
		usv.modify(uvo);
		log.info("modify uvo >>{}",uvo);
		logout(request, response);
		return "redirect:/";
	}
	
//	@PostMapping("/modify")
//	public String modify(UserVO uvo, HttpServletRequest request, HttpServletResponse response) {
//		if(uvo.getPwd().isEmpty() || uvo.getPwd().length()==0) {
//			//비밀번호 다시 입력받지 않는 경우
//			int isOk = usv.modifyPwdEmpty(uvo)
//		} else {
//			//비밀번호 다시 입력받는 경우
//			usv.modify(uvo);
//		}			
//		logout(request, response);
//		return "redirect:/";
//	}
	
	
	@GetMapping("/delete")
	public String delete(String email, RedirectAttributes re, HttpServletRequest request, HttpServletResponse response) {
		int isOk = usv.delete(email);
		if(isOk>0) { re.addAttribute("msg_delete", "1"); }
		logout(request, response);
		return "redirect:/";
	}
	
//	이런식으로 principal을 매개변수로 받아 값을 가져오는 방법도 있다
//	public void delete(Principal principal){
//		String email = principal.getName(); //email값을 가져옴
//		int isOk = usv.delete(email);
//	}
	
	@ResponseBody
	@PostMapping("/{email}")
	public String checkEmail(@PathVariable("email")String email) {
		UserVO uvo = usv.checkEmail(email);
		return uvo == null? "1":"0";
	}

	@ResponseBody
	@PostMapping("/nick/{nickName}")
	public String checkNickname(@PathVariable("nickName")String nickName) {
		int isOk = usv.checknick(nickName);
		log.info("isOk{}",isOk);
		return isOk>0? "1":"0";
	}
	
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);
	}
	
	
}
