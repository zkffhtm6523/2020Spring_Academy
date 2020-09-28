package com.koreait.matzip.rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.model.RestaurantRecommendMenuVO;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Service
public class RestService {
	@Autowired
	private RestMapper mapper;

	@Autowired
	private CommonMapper cMapper;

	
	public List<RestRecMenuVO> selRestMenus(RestPARAM param){
		return mapper.selRestMenus(param);
	}
	
	public void addHits(RestPARAM param, HttpServletRequest req) {
		String myIp = req.getRemoteAddr();
		ServletContext ctx = req.getServletContext();
		
		int i_user = SecurityUtils.getLoginUserPk(req);
		
		String currentReadUser = (String)ctx.getAttribute(Const.CURRENT_REST_READ_IP+param.getI_rest());
		//null이라면 처음 들어간 것
		if(currentReadUser == null || !currentReadUser.equals(myIp)) {
			System.out.println("조회수 등록 들어옴");
			param.setI_user(i_user); // 내가 쓴 글이면 조회수 안 올라가게 쿼리문으로 막는다.
			//조회수 올림 처리 할꺼임
			System.out.println(param.getI_user());
			System.out.println(param.getI_user());
			mapper.updAddHits(param);
			ctx.setAttribute(Const.CURRENT_REST_READ_IP+param.getI_rest(), myIp);
		}
	}
	
	// 알아서 json형태롤 바꿔서 리턴해준다
	List<RestDMI> selRestList(RestPARAM param) {
		return mapper.selRestList(param);
	}

	List<CodeVO> selCategoryList() {
		final int I_M = 1; // 카테고리 코드
		CodeVO p = new CodeVO();
		p.setI_m(I_M); // 음식점 카테고리 1번

		return cMapper.selCodeList(p);
	}

	int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}

	RestDMI getRest(RestPARAM param) {
		return mapper.selRest(param);
	}

	List<RestRecMenuVO> selRestRecMenu(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}

	@Transactional
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}

	public int delRest(RestPARAM param) {
		return mapper.delRest(param);
	}

	public int delRestMenu(RestPARAM param) {
		System.out.println("menu_pic : "+param.getMenu_pic());
		System.out.println("seq : "+param.getSeq());
		if(param.getMenu_pic() != null && !"".equals(param.getMenu_pic())) {
			String path = Const.realPath + "/resources/img/rest/"+param.getI_rest()+"/menu/";
			System.out.println("path"+path);
			if(FileUtils.delFile(path + param.getMenu_pic())) {
				return mapper.delRestMenu(param);
			} else {
				return Const.FAIL;
			}
		}
		//파일이 없더라도 DB 삭제가 되도록 해야함
		return mapper.delRestMenu(param);
	}

	public int delRestRecMenu(RestPARAM param) {
		return mapper.delRestRecMenu(param);
	}

	public int delRecMenu(RestPARAM param, String realPath) {
		// 파일 삭제
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		if (list.size() == 1) {
			RestRecMenuVO item = list.get(0);

			if (item.getMenu_pic() != null && !item.getMenu_pic().equals("")) { // 이미지 있음 > 삭제!!
				File file = new File(realPath + item.getMenu_pic());
				if (file.exists()) {
					if (file.delete()) {
						return mapper.delRestRecMenu(param);
					} else {
						return 0;
					}
				}
			}
		}

		return mapper.delRestRecMenu(param);
	}
	public int insMenus(RestFile param, MultipartHttpServletRequest mReq) {
		//권한 인증
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());
		System.out.println("i_user : "+i_user);
		int i_rest = param.getI_rest();
		System.out.println("i_rest : "+i_rest);
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
		List<MultipartFile> fileList = param.getMenu_pic();
		// 서블릿 컨택스트의 객체가 넘어옴
		String path = mReq.getServletContext().getRealPath("/resources/img/rest/" + i_rest+"/menu/");

		List<RestRecMenuVO> list = new ArrayList<RestRecMenuVO>();
		for (int i = 0; i < fileList.size(); i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);
			// 각 파일 저장
			MultipartFile mf = fileList.get(i);
			
			if (mf.isEmpty()) {continue;}
			vo.setI_rest(i_rest);
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			
			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (RestRecMenuVO vo : list) {
			mapper.insMenus(vo);
		}
		return i_rest;
	}
	
	
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		//권한 인증
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
		
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");

		// 서블릿 컨택스트의 객체가 넘어옴
		String path = mReq.getServletContext().getRealPath("/resources/img/rest/" + i_rest + "/rec_menu/");

		List<RestRecMenuVO> list = new ArrayList<RestRecMenuVO>();

		for (int i = 0; i < menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);

			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setI_rest(i_rest);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);

			// 각 파일 저장
			MultipartFile mf = fileList.get(i);

			if (mf.isEmpty()) {
				continue;
			}

			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;

			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		for (RestRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}
		return i_rest;
	}

	private boolean _authFail(int i_rest, int i_user) {
		RestPARAM param = new RestPARAM();
		param.setI_rest(i_rest);
		
		RestDMI dbResult = mapper.selRest(param);
		System.out.println("_authFail i_user : "+i_user);
		System.out.println("_authFail i_rest : "+i_rest);
		System.out.println("_authFail dbResut : "+dbResult);
		//인증 실패
		if(dbResult == null || dbResult.getI_user() != i_user) {
			System.out.println("인증 실패");
			return true;
		}
		//인증 완료
		System.out.println("인증 완료");
		return false;
	}
}
