package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.koreait.matzip.user.model.UserVO;

@Mapper
public interface RestMapper {

	int insRest(RestPARAM param);
	int insMenus(RestRecMenuVO param);
	int insRestRecMenu(RestRecMenuVO param);
	
	int selRestChkUser(int param);
	RestDMI selRest(RestPARAM param);
	List<RestDMI> selRestList(RestPARAM param);
	List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	List<RestRecMenuVO> selRestMenus(RestPARAM param);
	
	int delRestRecMenu(RestPARAM param);
	int delRestMenu(RestPARAM param);
	int delRest(RestPARAM param);
}
