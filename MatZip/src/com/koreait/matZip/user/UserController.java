package com.koreait.matZip.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.koreait.matZip.Const;
import com.koreait.matZip.ViewRef;
import com.koreait.matZip.vo.UserVO;

public class UserController {
	
	private UserService service;
	
	public UserController() {
		service = new UserService();
	}
	//예를 들어 /user/login이면 여기로 오게 할 거임
	public String login(HttpServletRequest request)throws ServletException, IOException {
		String error = request.getParameter("error");
		
		if(error != null) {
			switch(error) {
			case "2":
				request.setAttribute("msg", "아이디를 확인해 주세요.");
				break;
			case "3":
				request.setAttribute("msg", "비밀번호를 확인해 주세요.");
				break;
			}
		}
		
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
		
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(user_pw);
		param.setNm(nm);
		
		int result = service.join(param);
		
		return "redirect:/user/login";
	}
	public String loginProc(HttpServletRequest request)throws ServletException, IOException {
		String user_id = request.getParameter("user_id");		
		String user_pw = request.getParameter("user_pw");		
		
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(user_pw);
		
		int result = service.login(param);
		
		if(result == 1) {
			return "redirect:/restaurant/restMap";
		}else {
			return "redirect:/user/login?user_id="+user_id+"&error="+result;
		}
	}
	
	public String ajaxIdChk(HttpServletRequest request)throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw("");
		
		int result = service.login(param);
		
		return String.format("ajax:{\"result\": %s}", result);
	}
}
