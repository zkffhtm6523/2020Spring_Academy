package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

//서비스는 그냥 빈등록이라고 생각하면 됨
@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
	public int login(UserDTO param) {
		UserDMI dbUser = mapper.selUser(param);
		if(param.getUser_id() == dbUser.getUser_id() && param.getUser_pw() != dbUser.getUser_pw()) {
			return Const.NO_PW;
		}
		if(param.getUser_pw() == dbUser.getUser_pw() && param.getUser_id() != dbUser.getUser_id()) {
			return Const.NO_ID;
		}
		return Const.SUCCESS;
	}
	
	public int join(UserVO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.insUser(param);
	}
}
