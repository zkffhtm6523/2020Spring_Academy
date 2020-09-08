package com.koreait.matZip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.koreait.matZip.user.UserController;

public class HandlerMapper {
	private UserController userCon;
	
	public HandlerMapper() {
		userCon = new UserController();
	}
	public String nav(HttpServletRequest request) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		
		System.out.println("URI : "+request.getRequestURI());
		for (int i = 0; i < uriArr.length; i++) {
			System.out.println("uriArr["+i+"] : "+uriArr[i]);
		}
		System.out.println("uriArr.length : "+uriArr.length );
		
		if(uriArr.length < 3) {return "405";}
		//1번방 기준이 localhost:8089/~/의 ~부분이 1번방
		
		switch (uriArr[1]) {
			//case "user"
		case ViewRef.URI_USER :
			//2번방 접근. login이 적혀있었다면
			switch (uriArr[2]) {
			case "login": 
				return userCon.login(request); 
			case "join": 
				return userCon.join(request); 
			case "joinProc": 
				return userCon.joinProc(request); 
			case "loginProc": 
				return userCon.loginProc(request); 
			case "ajaxIdchk":
				return userCon.ajaxIdChk(request);
			}
		}
		return "404";
	}
}
