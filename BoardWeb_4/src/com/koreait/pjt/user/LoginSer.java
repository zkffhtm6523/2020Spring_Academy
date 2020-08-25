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
import com.koreait.pjt.vo.UserLoginHistoryVO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		System.out.println("IP : "+request.getRemoteAddr());
		if(loginUser != null) {
			response.sendRedirect("/board/list");
			return;
		}
//		if(request.getRemoteAddr().equals("192.168.2.1")) {
//			String ipben = request.getRemoteAddr();
//			response.sendRedirect("/ipben?ipben="+ipben);
//			return;
//		}else {
			String fileNm = "user/login";
			ViewResolver.forward(fileNm, request, response);
			return;
//		}
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
			return;
		}
		//브라우저, OS, IP 정보 가져오기
		String agent = request.getHeader("User-Agent");
		System.out.println("agent : "+agent);
		String os = getOs(agent);
		String browser = getBrowser(agent);
		
		String ip_addr = request.getRemoteAddr();
		
		UserLoginHistoryVO ulhVO = new UserLoginHistoryVO();
		ulhVO.setI_user(param.getI_user());
		ulhVO.setOs(os);
		ulhVO.setIp_addr(ip_addr);
		ulhVO.setBrowser(browser);
		
		UserDAO.insUserLoginHistory(ulhVO);
		
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,param);
		response.sendRedirect("/board/list");
	}
	//메소드 : 브라우저 정보 가져오기
	private String getBrowser(String agent) {
		if(agent.toLowerCase().contains("msie")) {
			return "ie";
		}else if(agent.toLowerCase().contains("chrome")) {
			return "chrome";
		}else if(agent.toLowerCase().contains("safari")) {
			return "safari";
		}
		return "";
	}
	//메소드 : OS정보 가져오기
	private String getOs(String agent) {
		if(agent.contains("mac")) {
			return "mac";
		}else if(agent.toLowerCase().contains("windows")) {
			return "Windows";
		}else if(agent.toLowerCase().contains("x11")) {
			return "linux";
		}else if(agent.toLowerCase().contains("android")) {
			return "android"; 
		}else if(agent.toLowerCase().contains("iphone")) {
			return "ios";
		}else if(agent.toLowerCase().contains("linux")) {
			return "linux";
		}
		return "";
	}

}
