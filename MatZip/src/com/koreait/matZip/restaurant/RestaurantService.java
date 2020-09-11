package com.koreait.matZip.restaurant;

import java.util.List;

import com.google.gson.Gson;
import com.koreait.matZip.vo.RestaurantDomain;
import com.koreait.matZip.vo.RestaurantVO;

public class RestaurantService {
	
	private RestaurantDAO dao;
	
	public RestaurantService() {
		dao = new RestaurantDAO();
	}
	
	public int restReg(RestaurantVO param) {
		return dao.insRestaurant(param);
	}
	
	public String getRestList(){
		List<RestaurantDomain> list = dao.selRestList(); 
		Gson gson = new Gson();
		return gson.toJson(list);
	}
}
