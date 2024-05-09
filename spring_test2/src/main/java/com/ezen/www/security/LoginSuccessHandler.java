package com.ezen.www.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.ezen.www.repository.UserDAO;
import com.ezen.www.service.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	//멤버변수 각자에게 getter,setter 부여 가능
	@Getter
	@Setter
	private String authEmail;

	@Getter
	@Setter
	private String authUrl;

	//redirect시 데이터를 가지고 redirect 하는 역할
	private RedirectStrategy redstg = new DefaultRedirectStrategy();
	//로그인 정보, 경로
	private RequestCache reqCache = new HttpSessionRequestCache();

	//로그인 성공시 로그인 일자 기록 lastLogin
	@Inject
	private UserDAO udao;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// authentication 인증된 AuthMember 객체
		log.info("authentication >>>>>>> {}",authentication.getPrincipal());
		setAuthEmail(authentication.getName());
		setAuthUrl("/board/list");
		
		//로그인 일자 기록 lastLogin
		int isOk = udao.updateLastLogin(getAuthEmail());
		
		//세션 가져오기
		HttpSession ses = request.getSession();
		if(isOk==0 || ses==null) {
			return;
		} else {
			//security에서 로그인에 시도하여 실패하면 로그인기록이 남게됨
			//이전에 시도한 시큐리티의 인증 실패기록 삭제
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		//로그인 전 방문한 기록을 저장
		SavedRequest saveReq = reqCache.getRequest(request, response);
		redstg.sendRedirect(request, response, (saveReq!=null? saveReq.getRedirectUrl() : getAuthUrl()));
	}

}
