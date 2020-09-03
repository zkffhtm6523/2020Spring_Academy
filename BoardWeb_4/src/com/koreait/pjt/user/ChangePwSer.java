package com.koreait.pjt.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/changePw")
public class ChangePwSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.forward("user/changePw", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		
		String type = request.getParameter("type");
		String pw = request.getParameter("pw");
		String encrypt_pw = MyUtils.encryptString(pw);
		
		UserVO param = new UserVO();
		
		//암호화 필요
		param.setUser_pw(encrypt_pw);
		param.setUser_id(loginUser.getUser_id());
		param.setI_user(loginUser.getI_user());
		switch (type) {
		case "1"://현재 비밀번호 확인
			int result = UserDAO.selUser(param);
			
			if(result == 1) {
				request.setAttribute("isAuth", true);
			}else {
				request.setAttribute("msg", "비밀번호를 확인해주세요");
			}
			
			doGet(request, response);
			break;
		case "2"://비밀번호 변경
			int result2 = UserDAO.updUser(param);
			if(result2 == 1) {
				response.sendRedirect("/profile?proc=1");
				return;
			}
			doGet(request, response);//실패 시 여기로 감
			break;
		default:
			break;
		}
	}

}
