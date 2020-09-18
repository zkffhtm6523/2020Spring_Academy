package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//이거 하나만 있으면 주소값 알아서 넣어준다
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model , @RequestParam(value="err", required = false, defaultValue = "0") int err) {
		if(err == Const.NO_ID) {
			model.addAttribute("msg", "아이디가 일치하지 않습니다.");
		}else if(err == Const.NO_PW) {
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
		}
		model.addAttribute(Const.TITLE,"로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//getparameter가 자동으로 된다. 대신 form의 name이 uservo와 같아야 한다
	public String login(UserDTO param, @RequestParam(value="err", required = false, defaultValue = "0") int err) {
									// 쿼리스트링에 있는 거 받아옴, 아니어도 받아옴, 타입 맞게 받아옴
									// required 필수인가?, 기본값은 true, true 하면 무조건 매개변수 받아와야함
		System.out.println("id : "+param.getUser_id());
		System.out.println("pw : "+param.getUser_pw());
		int result = service.login(param);
		System.out.println("result : "+result);
		if(result == 2) {
			return "redirect:/user/login?err="+result;
		}else if(result == 3 ) {
			return "redirect:/user/login?err="+result;
		}
		return "redirect:/rest/map"; 
		
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
	public String join(UserVO param) {
		int result = service.join(param);
		if(result == 1) {
			return "redirect:/user/login";
		}
		return "redirect:/user/join?err="+result;
	}
}
