package com.koreait.matzip.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
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
	@RequestMapping("/ajaxGetList")
	@ResponseBody
	public String ajaxGetList(RestPARAM param) {
		System.out.println("sw_lat : "+param.getSw_lat());
		System.out.println("sw_lng : "+param.getSw_lng());
		System.out.println("ne_lng : "+param.getNe_lat());
		System.out.println("ne_lng : "+param.getNe_lng());
		return service.selRestList(param);
	}
	
	@RequestMapping(value = "/restReg", method = RequestMethod.GET)
	public String restReg(Model model) {
		final int I_M = 1;//카테고리 코드
		model.addAttribute(Const.TITLE, "매장 등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/restReg", method = RequestMethod.POST)
	public String restReg(RestPARAM param) {
		int result = service.insRest(param);
		if(result == 1) {
			return "redirect:/rest/restReg";
		}
		return "redirect:/rest/map";
	}
}
