package com.koreait.pjt;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.vo.UserVO;

public class MyUtils {
	
//	public static UserVO getLoginUser(HttpServletRequest request) {
//		
//	}
	
	//return true : 로그인이 안됨. false : 로그인 된 상태
	public static boolean isLogout(HttpServletRequest request) throws IOException {		
		HttpSession hs = request.getSession();
		if(null == hs.getAttribute(Const.LOGIN_USER)) {
			return true;			
		}
		return false;
	}
	public static UserVO getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserVO)hs.getAttribute(Const.LOGIN_USER);
	}
	public static String encryptString(String str) {
		String sha = "";

		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			//보낸 문자열을 바이트 단위로 바꿈
			sh.update(str.getBytes());
			//byteData 배열
			//복호화가 안되는 기법
			byte byteData[] = sh.digest();
			//StringBuffer 문자열 합치기에 좋음
			//원래 "111"+"222"+"333"이렇게 하면 주소값이 5개~6개가 필요함
			//요즘 더하기를 하면 StringBuffer로 전환되서 알아서 자동으로 구현
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				//문자열이 계속 합쳐짐
				//비트 연산자 사용됨								16진수 + 16진수/16진법으로 하는것
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			System.out.println("Encrypt Error - NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
	}
	public static int getIntParameter(HttpServletRequest request, String keyNm) {
	      return parseStrToInt(request.getParameter(keyNm));
	   }
	public static int parseStrToInt(String str, int n) {
	      int num = n;
	      try {
	         num = Integer.parseInt(str);
	      } catch (Exception e) {
	         
	      }
	      
	      return num;
	   }
	   
	   public static int parseStrToInt(String str) {
	      int num = 0;
	      try {
	         num = Integer.parseInt(str);
	      } catch (Exception e) {
	         
	      }
	      
	      return num;
	   }
}
