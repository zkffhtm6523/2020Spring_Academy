package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

@Controller
@RequestMapping("/rest") //matzip의 핸들러 매핑에 1차로 넣어주는 것
public class RestController {
	
	@Autowired
	private RestService service;
	
	@RequestMapping("/map") //matzip의 핸들러 매핑에 2차로 넣어주는 것
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE,"지도보기");
		model.addAttribute(Const.VIEW,"rest/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/ajaxGetList" ,produces = "application/json; charset=utf8")
	@ResponseBody
	//객체를 리턴해줄 때 responseBody를 하면 알아서 jackson 적용됨
	public List<RestDMI> ajaxGetList(RestPARAM param) {
		System.out.println("sw_lat : "+param.getSw_lat());
		System.out.println("sw_lng : "+param.getSw_lng());
		System.out.println("ne_lng : "+param.getNe_lat());
		System.out.println("ne_lng : "+param.getNe_lng());
		return service.selRestList(param);
	}
	
	@RequestMapping(value = "/restReg", method = RequestMethod.GET)
	public String restReg(Model model) {
		model.addAttribute("categoryList", service.selCategoryList());
		
		model.addAttribute(Const.TITLE, "매장 등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/restReg", method = RequestMethod.POST)
	public String restReg(RestPARAM param, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		int result = service.insRest(param);
		if(result == 1) {
			return "redirect:/rest/restReg";
		}
		return "redirect:/rest/map";
	}
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String restDetail(Model model, RestPARAM param) {
		model.addAttribute("css", new String[] {"restaurant"});
		model.addAttribute("data", service.getRest(param));
		model.addAttribute("recommendMenuList", service.getRecommendMenuList(param.getI_rest()));
		model.addAttribute(Const.TITLE, "상세페이지");
		model.addAttribute(Const.VIEW, "rest/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/addRecMenusProc", method = RequestMethod.POST)
	public String addRecMenusProc(RestPARAM param, HttpServletRequest request, RedirectAttributes ra) {
		//쿼리스트링 보내줌
		ra.addAttribute("i_rest", service.addRecMenus(request));
		return "redirect:/rest/detail";
	}
}
