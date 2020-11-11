package com.koreait.apart;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.koreait.apart.model.ApartInfoVO;
import com.koreait.apart.model.SearchPARAM;

@Controller
public class HomeController {
	@Autowired
	private HomeMapper mapper;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("locationList", mapper.selLocationCodeList());
		return "index";
	}
	@RequestMapping(value = "/result", method=RequestMethod.POST)
	public String result(SearchPARAM param, Model model) {
		List<ApartInfoVO> data = mapper.selApartmentInfoList(param);
		if(data.size() > 0) {
			model.addAttribute("data", data);
			return "result";
		}
		String lawd_cd = param.getLocationCd();
		//02는 무조건 2자리 차지한다는 의미,
		//primative 변수, 레퍼런스 변수는 null 가질 수 있음
		//mon에 null이 안 넘어옴, int형이라서 0이 넘어옴
		String deal_ym = String.format("%s%02d", param.getYear(), param.getMon());
		
		String serviceKey = "Y2UOCkD8Ilv2gViPGV33ddNTTQfRi92i8mRzUeQX%2BNgSiNTO3gp9hJZX4J6u8uXucMM";
		String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
		
		String decodeServiceKey = null;
		try {
			decodeServiceKey = URLDecoder.decode(serviceKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("LAWD_CD", lawd_cd)
				
				.queryParam("DEAL_YMD", deal_ym).queryParam("serviceKey", decodeServiceKey);
		
		return "result";
	}
}
