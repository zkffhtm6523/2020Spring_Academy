package com.koreait.board.common;

public class Utils {
	public static int parseStrToInt(String param, int returnValue) {
		try {
			int temp = Integer.parseInt(param);
			return temp;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return returnValue;
		}
	}
	public static int parseStrToInt(String param) {
		return parseStrToInt(param, 0);
	}
}
