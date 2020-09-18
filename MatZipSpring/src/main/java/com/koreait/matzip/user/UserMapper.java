package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

//마이바티스 것 ->servlet-context의 맨 하단 mybatis는 mapper를 찾는 것
@Mapper
public interface UserMapper {
	public int insUser(UserVO vo);
	public UserDMI selUser(UserDTO dto);
	
}
