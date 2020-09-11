package com.koreait.matZip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonUtils {
	public static int parseStringToInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch (Exception e) {
			return 0;
		}
	}
	public static double parseStringToDouble(String str) {
		try {
			return Double.parseDouble(str);
		}catch (Exception e) {
			return 0;
		}
	}
	
	public static int getParameterInt(String key, HttpServletRequest request) {
		return parseStringToInt(request.getParameter(key));
	}
	
	public static double getParameterDouble(String key, HttpServletRequest request) {
		return parseStringToDouble(request.getParameter(key));
	}
}
