package com.koreait.apart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.apart.model.ApartInfoVO;
import com.koreait.apart.model.ItemDTO;
import com.koreait.apart.model.LocationCodeVO;
import com.koreait.apart.model.SearchPARAM;

@Mapper
public interface HomeMapper {
	List<LocationCodeVO> selLocationCodeList();
	List<ApartInfoVO> selApartmentInfoList(SearchPARAM param);
	
	//오픈 API 소스
	int insApartmentInfo(ItemDTO param);
}
