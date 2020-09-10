package com.koreait.matZip.restaurant;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.koreait.matZip.Const;
import com.koreait.matZip.ViewRef;

public class RestaurantController {
	public String restMap(HttpServletRequest request) throws ServletException, IOException {
		request.setAttribute(Const.TITLE, "맵");
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP; 
	}
}
