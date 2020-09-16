package com.koreait.matZip;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//"/"는 모두 얘가 잡고, res는 web.xml에서 staticcontainer로 "/"를 막아놨음
@WebServlet("/")
@MultipartConfig(
		fileSizeThreshold = 10_485_760,
		maxFileSize = 52_428_800,
		maxRequestSize = 104_857_600
)
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
		//로그인에 따른 접속 가능 여부 판단
		//(로그인이 안 되어 있으면 접속할 수 있는 주소만 여기서 체크, 나머지 전부 로그인되어 있어야함)
		//handler mapping에서 로그인이 되어있으면 들어갈 수 있도록 할 수 있지만
		//그것은 절차지향 방식이고, 아래의 방식이 객체지향 방식
		String routerCheckResult = LoginChkInterceptor.routerChk(request);
		if(routerCheckResult != null) {
			response.sendRedirect(routerCheckResult);
			return;
		}
		//위에서 선언한 변수(주소값).메소드
		
		String temp = mapper.nav(request);
		System.out.println("temp : "+temp);
		//보통 템플릿 파일명이 넘어온다. mapping에서는 uri에서 /상태로 자른 값들
		//기준으로 
		
		//handlermapping에서 ajax를 넘겨주면 여기서 처리할 로직
		//405,404가 안넘어오는게 temp.indexOf("/") >= 0, "redirect:".equals("/"))하고 같지 않으니까 다음 밑에 request로 이동, 
		if(temp.indexOf(":") >= 0){
			String prefix = temp.substring(0, temp.indexOf(":"));
			String value = temp.substring(temp.indexOf(":")+1);
			System.out.println("prefix : "+prefix);
			System.out.println("value : "+value);
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
