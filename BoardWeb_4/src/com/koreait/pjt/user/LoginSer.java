package com.koreait.pjt.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.Const;
import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser != null) {
			response.sendRedirect("/board/list");
			return;
		}
		if(request.getRemoteAddr().equals("192.168.2.15")) {
			String ipben = request.getRemoteAddr();
			response.sendRedirect("/ipben");
			return;
		}
		String fileNm = "user/login";
		ViewResolver.forward(fileNm, request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//login의 form태그 name을 받아옴
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String encrypt_pw = MyUtils.encryptString(user_pw);
		
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(encrypt_pw);
		
		//result가 0인 경우는 jdbcTemplate에 구현되어 있다.
		int result = UserDAO.selUser(param);
		if(result != 1) {
			String msg = null;
			switch (result) {
			case 2: msg = "비밀번호가 맞지 않습니다."; break;
			case 3: msg = "아이디가 일치하지 않습니다."; break;
			default: msg = "에러가 발생하였습니다.."; break;
			}
			request.setAttribute("msg", msg);
			request.setAttribute("data", param);
			doGet(request,response);
		}
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,param);
		//이거하면 에러남
		//String fileNm = "/board/list";
//		ViewResolver.forward(fileNm, request, response);
		response.sendRedirect("/board/list");
	}

}
