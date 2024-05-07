package com.ezen.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.www.security.CustomAuthUserService;
import com.ezen.www.security.LoginFailHandler;
import com.ezen.www.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//비밀번호 암호화 객체 PasswordEncoder 빈 생성
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//SuccessHandler 객체 빈 생성 => 사용자 지정 custom 객체
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	//FailHandler 객체 빈 생성 => 사용자 지정 custom 객체
	@Bean
	public AuthenticationFailureHandler authFailHandler() {
		return new LoginFailHandler();
	}
	
	//UserDetail 객체 빈 생성 => 사용자 지정 custom 객체
	@Bean
	public UserDetailsService customUserService() {
		return new CustomAuthUserService();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//인증되는 객체로 설정
		auth.userDetailsService(customUserService()).passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//화면에서 설정되는 권한에 따른 주소 맵핑 설정
		//csrf() : 공격에 대한 설정 풀기(연습용이기 때문에)
		http.csrf().disable();
		
		//권한 승인 요청
		//antMatchers : 접근을 허용하는 값(경로)
		//permitAll : 누구나 접근 가능한 경로
		//authenticated() : 인증된 사용자만 접근 가능한 경로
		//auth(DB 필드) => hasRole : 권한 확인(DB를 읽어올 수 있음)
		//USER, ADMIN <보통 연습시 , MANAGER 
		http.authorizeRequests()
			.antMatchers("/user/list").hasRole("ADMIN")
			.antMatchers("/","/board/list","/board/detail","/comment/**","/up/**","/re/**","/user/register","/user/login").permitAll()
			.anyRequest().authenticated();
		
		//커스텀 로그인 페이지 구성
		//Controller에 주소요청 맵핑이 같이 있어야함(필수)
		//usernameParameter => VO의 멤버변수 중 아이디로 사용할 값을 설정(멤버변수와 같은 이름을 써야함)
		//passwordParameter => VO의 멤버변수 중 비밀번호로 사용할 값을 설정
		//successHandler / failHandler => 로그인 성공 / 실패시 탈 핸들러 연결
		http.formLogin()
		.usernameParameter("email")
		.passwordParameter("pwd")
		.loginPage("/user/login")
		.successHandler(authSuccessHandler())
		.failureHandler(authFailHandler());
		//.successForwardUrl() / .failureUrl() => 별도 핸들러 없이 원하는 페이지로 이동 가능
		
		//logout도 핸들러를 타기 위해서 반드시 form 태그, method=post 로 전달해야함
		http.logout()
		.logoutUrl("/user/logout")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutSuccessUrl("/");
		
	}

	
	
}
