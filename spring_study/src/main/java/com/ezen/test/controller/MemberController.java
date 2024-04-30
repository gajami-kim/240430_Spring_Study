package com.ezen.test.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.test.domain.MemberVO;
import com.ezen.test.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Controller
public class MemberController {
		
	private final MemberService msv;

	@GetMapping("/register")
	public void register() {}

	@PostMapping("/register")
	public String register(MemberVO mvo, Model m) {
		log.info(">>>> mvo >>>> {}", mvo);
		int isOk = msv.insert(mvo);
		log.info(">>>> isOk >>>> {}", isOk);
		if(isOk==0) {
			m.addAttribute("msg_join", "1");
		}
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(MemberVO mvo, HttpServletRequest request, Model m) {
		log.info(">>>> login >>>> {}", mvo);
		
		//mvo 객체가 DB의 값과 일치하면 객체를 가져옴(세션에 저장(로그인행위))
		MemberVO loginmvo = msv.isUser(mvo);
		log.info(">>>> login mvo >>>> {}", loginmvo);
		
		if(loginmvo!=null) {
			//로그인 성공시
			HttpSession ses = request.getSession();
			ses.setAttribute("ses", loginmvo); //세션에 로그인객체 저장
		} else {
			m.addAttribute("msg_login", "1");
		}
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model m) {
		//라스트로그인 업데이트 => 세션 객체 삭제 => 세션끊기
		MemberVO mvo = (MemberVO) request.getSession().getAttribute("ses");
		msv.last_loginUpdate(mvo.getId());
		
		request.getSession().removeAttribute("ses"); //ses 세션 삭제
		request.getSession().invalidate(); //세션 끊기
		
		m.addAttribute("msg_logout", "1");
		return "index";
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo, RedirectAttributes re) {
		log.info(">>>> mvo >>>> {}", mvo);
		msv.modify(mvo);
		re.addFlashAttribute("msg_modify", "1");
		return "redirect:/member/logout";
	}
	
	@GetMapping("/remove")
	public String remove(HttpServletRequest request, Model m) {
		MemberVO mvo = (MemberVO) request.getSession().getAttribute("ses");
		msv.remove(mvo.getId());
		
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate();
		
		m.addAttribute("msg_delete", "1");
		return "index";
	}
	
//	@GetMapping("/remove")(반복되는 세션끊기 작업 줄이기)
//	public String remove(HttpServletRequest request, RedirectAttributes re) {
//		MemberVO mvo = (MemberVO) request.getSession().getAttribute("ses");
//		msv.remove(mvo.getId());
//	
		//return을 jsp로 하지않는다면(jsp로 이동하지않는다면) Model m 객체를 사용할 수 X
		//내부에서 경로를 다시 탈 땐 RedirectAttributes 사용가능
		//내부 logout을 타서 최종적으로 index로 이동하게 됨(remove->logout->index)
		//로그아웃에서 세션 삭제/끊기 작업까지 모두 완료
		//Flash는 일시적으로 사용가능
//		re.addFlashAttribute("msg_delete", "1");
//		return "redirect:/member/logout";
//	}
	
	//ID 가져오는 것을 메서드로 처리할 때(반복되는 코드 줄이기)
//	private String getId(HttpServletRequest request) {
//		MemberVO mvo = (MemberVO) request.getSession().getAttribute("ses");
//		return mvo.getId();
//		
//		//다른 메서드에서 사용할 때
//		msv.delete(getId(request));
//	}
}
