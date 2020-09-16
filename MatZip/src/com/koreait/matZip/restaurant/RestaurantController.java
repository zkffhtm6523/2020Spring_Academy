package com.koreait.matZip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.matZip.CommonDAO;
import com.koreait.matZip.CommonUtils;
import com.koreait.matZip.Const;
import com.koreait.matZip.FileUtils;
import com.koreait.matZip.SecurityUtils;
import com.koreait.matZip.ViewRef;
import com.koreait.matZip.vo.CodeDomain;
import com.koreait.matZip.vo.RestaurantRecommendMenuVO;
import com.koreait.matZip.vo.RestaurantVO;
import com.koreait.matZip.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.glass.ui.View;

import sun.print.resources.serviceui;

public class RestaurantController {
	private RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) throws ServletException, IOException {
		request.setAttribute(Const.TITLE, "맵");
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP; 
	}
	public String restReg(HttpServletRequest request) {
		final int I_M = 1;//카테고리 코드
		request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));
		
		request.setAttribute(Const.TITLE, "가게 등록");
		//템플릿에 참조할 주소,el식으로 주소값 불러오면 템플릿의 섹션만 박힘
		request.setAttribute(Const.VIEW, "restaurant/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	public String restRegProc(HttpServletRequest request) {
		String nm = request.getParameter("nm");
		String addr = request.getParameter("addr");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lng = Double.parseDouble(request.getParameter("lng"));
		int cd_category = Integer.parseInt(request.getParameter("cd_category"));
		UserVO vo = SecurityUtils.getLoginUser(request);
		int i_user = vo.getI_user();
		
		RestaurantVO param = new RestaurantVO();
		param.setNm(nm);
		param.setAddr(addr);
		param.setLat(lat);
		param.setLng(lng);
		param.setCd_category(cd_category);
		param.setI_user(i_user);
		
		int result = service.restReg(param);
		
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		
		return "redirect:/restaurant/restMap";
	}
	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:"+service.getRestList();
	}
	public String restDetail(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);

		RestaurantVO param = new RestaurantVO();
		param.setI_rest(i_rest);

		request.setAttribute("css", new String[]{"restaurant"});
		
		request.setAttribute("menuList", service.getMenuList(i_rest));
		request.setAttribute("recommendMenuList", service.getRecommendMenuList(i_rest));
		request.setAttribute("data", service.getRest(param));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	public String addMenusProc(HttpServletRequest request) { //메뉴
		int i_rest = service.addMenus(request);
		return "redirect:/restaurant/detail?i_rest=" + i_rest;
	}
	public String addRecMenusProc(HttpServletRequest request) {
		int i_rest = service.addRecMenus(request);
		return "redirect:/restaurant/detail?i_rest=" + i_rest;
	}
	
	public String ajaxDelRecMenu(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		int seq = CommonUtils.getIntParameter("seq", request);
		
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		param.setI_rest(i_rest);
		param.setSeq(seq);
		param.setI_user(SecurityUtils.getLoginUserPk(request));
		
		return "ajax:" + service.delRecMenu(param);
	}
}
