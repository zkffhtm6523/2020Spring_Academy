package com.koreait.pjt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyUtils {
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
}
