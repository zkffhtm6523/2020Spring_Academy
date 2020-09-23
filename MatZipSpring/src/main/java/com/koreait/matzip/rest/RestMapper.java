package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.koreait.matzip.user.model.UserVO;

@Mapper
public interface RestMapper {
	public List<RestDMI> selRestList(RestPARAM p);
	public int insRest(RestPARAM p);
	public RestDMI getRest(RestPARAM p);
	public int insRestRecMenu(RestRecMenuVO p);
	public List<RestRecMenuVO> selRestRecMenus(RestPARAM p);
	public int delRest(RestPARAM p);
	public int delRestRecMenu(RestPARAM p);
	public int delRestMenu(RestPARAM p);
}
