package com.koreait.matZip;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//위에서 선언한 변수(주소값).메소드
		String temp = mapper.nav(request);//보통 템플릿 파일명이 넘어온다
		//handlermapping에서 ajax를 넘겨주면 여기서 처리할 로직
		//405,404가 안넘어오는게 temp.indexOf("/") >= 0, "redirect:".equals("/"))하고 같지 않으니까 다음 밑에 request로 이동, 
		if(temp.indexOf(":") >= 0){
			String prefix = temp.substring(0, temp.indexOf(":"));
			String value = temp.substring(temp.indexOf(":")+1);
			
			if("redirect".equals(prefix)) {
				System.out.println("temp sub : "+temp.substring(0, temp.indexOf("/")));
				//redirect : 이것이 붙은게 넘어올 때 여기로 가기 위한 것
				response.sendRedirect(value);
				return;
			//ajax 처리 로직
			}else if("ajax".equals(prefix)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				//join.jsp의 result에 값이 들어간다
				PrintWriter out = response.getWriter();
				out.print(value);
				return;
			}
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
