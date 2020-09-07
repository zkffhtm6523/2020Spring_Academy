package com.koreait.matZip;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.matZip.db.DbManager;
//"/"는 모두 얘가 잡고, res는 web.xml에서 staticcontainer로 "/"를 막아놨음
@WebServlet("/")
public class Container extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HandlerMapper mapper;
	
	public Container() {
		mapper = new HandlerMapper();
	}
	//톰캣 컨테이너가 최초에 request를 준다.
	//doget은 호출당하니까 framework
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//기본 서블릿에 '/'를 매핑시켜놓으면 URI가 '/' 한 번 잡히고, '/res/js/test.ts'가 두 번 잡힌다.
		proc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		proc(request, response);
	}
	private void proc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//주소값.메소드
		String temp = mapper.nav(request);
		
		if(temp.indexOf("/") >= 0 && "redirect:".equals(temp.substring(0, temp.indexOf("/")))) {
				response.sendRedirect(temp.substring(temp.indexOf("/")));
				return;
		}
		
		switch (temp) {
		case "405":
			temp = "/WEB-INF/view/error.jsp";
			break;
		case "404":
			temp = "/WEB-INF/view/notFound.jsp";
			break;
		}
		request.getRequestDispatcher(temp).forward(request, response);
	}
}
