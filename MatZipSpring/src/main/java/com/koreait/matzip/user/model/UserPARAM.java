package com.koreait.matzip.user.model;

//원래는 view단하고 db 이름이 다를 때 변환시켜주는 친구
public class UserPARAM extends UserVO{
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
