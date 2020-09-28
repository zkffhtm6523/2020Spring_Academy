package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//이거 하나만 있으면 주소값 알아서 넣어준다
	@Autowired
	private UserService service;
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model , @RequestParam(value="err", required = false, defaultValue = "0") int err) {
		
		model.addAttribute(Const.TITLE,"로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//getparameter가 자동으로 된다. 대신 form의 name이 uservo와 같아야 한다
	public String login(UserPARAM param, HttpSession hs, RedirectAttributes ra) {
									// 쿼리스트링에 있는 거 받아옴, 아니어도 받아옴, 타입 맞게 받아옴
									// required 필수인가?, 기본값은 true, true 하면 무조건 매개변수 받아와야함
		int result = service.login(param);
		
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/"; 
		}
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해주세요.";
		}else if(result == Const.NO_PW) {
			msg = "비밀번호를 확인해주세요";
		}
		param.setMsg(msg);

		ra.addFlashAttribute("data", param);
		return "redirect:/user/login";
		//디스패쳐의 특징, post로 보내면 post로 날라감
		//세션에 박히고, 쓰면 세션에서 지운다
	}
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(value="err", required = false, defaultValue = "0") int err) {
		
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생했습니다.");
		}
		model.addAttribute(Const.TITLE,"회원가입");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVO param, RedirectAttributes ra) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		//쿼리스트링으로 날려준다
		ra.addAttribute("err", result);
		return "redirect:/user/join?err="+result;
	}
	@RequestMapping(value = "/ajaxIdchk", method = RequestMethod.POST)
	@ResponseBody //responseBody를 빼 면  jsp를 찾을 것
	public String ajaxIdChk(@RequestBody UserPARAM param) {
		int result = service.login(param);
		return String.valueOf(result);
	}
	@RequestMapping(value="/ajaxToggleFavorite", method=RequestMethod.GET)
	@ResponseBody
	public int ajaxToggleFavorite(UserPARAM param, HttpSession hs) {
		System.out.println("==> ajaxToggleFavorite");
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		return service.ajaxToggleFavorite(param);
	}
	
}
