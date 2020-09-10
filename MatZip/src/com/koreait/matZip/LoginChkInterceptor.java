package com.koreait.matZip;

import javax.servlet.http.HttpServletRequest;
//잘못된 것을 잡아주는 역할
public class LoginChkInterceptor {
	
	public static String routerChk(HttpServletRequest request) {
		
		String[] chkUriArr = {"login","loginProc","join","joinProc","ajaxIdchk"};
		
		boolean isLogout = SecurityUtils.isLogout(request);
		String[] targetUri = request.getRequestURI().split("/");
		
		if(targetUri.length < 3) {return null;}

		//로그아웃 상태
		if(isLogout) {
			if(targetUri[1].equals(ViewRef.URI_USER)) {
				for(String uri : chkUriArr) {
					if(uri.equals(targetUri[2])) {
						//아무런 문제가 없을 때 null
						return null;
					}
				}
			}
			System.out.println("로그아웃 상황에서 이상한 곳 접근함");
			return "/user/login";
		//로그인 상태
		//로그인이 되어야 레스토랑 페이지 갈 수 있음.. 무조건
		//로그인이 되어있다면, 로그인, poc, ajax 막는 기능
		}else {
			if(targetUri[1].equals(ViewRef.URI_USER)) {
				for (String uri : chkUriArr) {
					if(uri.equals(targetUri[2])) {
						return "/restaurant/restMap";
					}
				}
		}
		return null;
	}
}
}
