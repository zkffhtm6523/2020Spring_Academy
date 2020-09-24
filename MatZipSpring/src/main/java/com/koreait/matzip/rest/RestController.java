package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
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
		return service.selRestList(param);
	}
	@RequestMapping(value =  "/ajaxDelRecMenu",produces = "application/json; charset=utf8")
	@ResponseBody
	public int ajaxDelRecMenu(RestPARAM param, HttpSession hs) {
		String path = "/resources/img/rest/" + param.getI_rest() + "/rec_menu/";
		String realPath = hs.getServletContext().getRealPath(path);
		param.setI_user(SecurityUtils.getLoginUserPk(hs)); //로긴 유저pk담기
		System.out.println("i_user : "+param.getI_user());
		System.out.println("i_rest : "+param.getI_rest());
		System.out.println("seq : "+param.getSeq());
		return service.delRecMenu(param, realPath);
	}
	@RequestMapping(value =  "/ajaxDelMenu",produces = "application/json; charset=utf8")
	@ResponseBody
	public int ajaxDelMenu(RestPARAM param, HttpSession hs) {
		String path = "/resources/img/rest/" + param.getI_rest() + "/menu/";
		String realPath = hs.getServletContext().getRealPath(path);
		param.setI_user(SecurityUtils.getLoginUserPk(hs)); //로긴 유저pk담기
		System.out.println("i_user : "+param.getI_user());
		System.out.println("i_rest : "+param.getI_rest());
		System.out.println("seq : "+param.getSeq());
		return service.delRecMenu(param, realPath);
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String restReg(Model model) {
		model.addAttribute("categoryList", service.selCategoryList());
		model.addAttribute(Const.TITLE, "매장 등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String restReg(RestPARAM param, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		int result = service.insRest(param);
		if(result == 1) {
			return "redirect:/rest/reg";
		}
		return "redirect:/rest/map";
	}
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String restDetail(Model model, RestPARAM param) {
		model.addAttribute("menuList", service.selRestMenus(param));
		model.addAttribute("css", new String[] {"restaurant"});
		model.addAttribute("data", service.getRest(param));
		model.addAttribute("recommendMenuList", service.selRestRecMenu(param));
		model.addAttribute(Const.TITLE, "상세페이지");
		model.addAttribute(Const.VIEW, "rest/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/recMenus", method = RequestMethod.POST)
	public String recMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
		System.out.println("/recMenus");
		
		int i_rest = service.insRecMenus(mReq);
		
		ra.addAttribute("i_rest",i_rest);
		return  "redirect:/rest/detail";
	}
	@RequestMapping("/del")
	public String del(RestPARAM param, HttpSession hs) {
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		int result = 1;
		try {
			service.delRestTran(param);
		}catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		System.out.println("result : "+result);
		return "redirect:/";
	}
	@RequestMapping("/menus")
	public String menus(@ModelAttribute RestFile param, MultipartHttpServletRequest mReq)	{
		System.out.println();
		for (MultipartFile file : param.getMenu_pic()) {
			System.out.println("fileNm : "+file.getOriginalFilename());
		}
		int i_rest = service.insMenus(param, mReq);
		
		return "redirect:/rest/detail?i_rest="+i_rest;
	}
	
}
