package com.koreait.matZip.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.koreait.matZip.Const;
import com.koreait.matZip.ViewRef;

public class UserController {
	//예를 들어 /user/login이면 여기로 오게 할 거임
	public String login(HttpServletRequest request)throws ServletException, IOException {
		//헤더하고 사이드 등 고정, 안의 컨텐츠만 바뀐다. 로그인 페이지는 템플릿 안 쓰겠다
		request.setAttribute(Const.VIEW, "user/login");
		request.setAttribute(Const.TITLE, "로그인");
		return ViewRef.TEMP_DEFAULT;
	}
	public String join(HttpServletRequest request)throws ServletException, IOException {
		request.setAttribute(Const.VIEW, "user/join");
		request.setAttribute(Const.TITLE, "회원가입");
		return ViewRef.TEMP_DEFAULT;
	}
	
	public String joinProc(HttpServletRequest request)throws ServletException, IOException {
		String user_id = request.getParameter("user_id");		
		String user_pw = request.getParameter("user_pw");		
		String nm = request.getParameter("nm");
		
		
		return "redirect:/user/login";
	}
}
