package com.koreait.matzip.rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.model.RestaurantRecommendMenuVO;
import com.koreait.matzip.rest.model.RestDMI;
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
	//알아서 json형태롤 바꿔서 리턴해준다
	List<RestDMI> selRestList(RestPARAM param){
		return mapper.selRestList(param);
	}
	List<CodeVO> selCategoryList(){
		final int I_M = 1; //카테고리 코드
		CodeVO p = new CodeVO();
		p.setI_m(I_M); //음식점 카테고리 1번
		
		return cMapper.selCodeList(p);
	}
	int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
	RestDMI getRest(RestPARAM param) {
		return mapper.getRest(param);
	}
	List<RestRecMenuVO> getRecommendMenuList(RestPARAM param){
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
		return mapper.delRestMenu(param);
	}
	public int delRestRecMenu(RestPARAM param) {
		return mapper.delRestRecMenu(param);
	}
	public int ajaxDelRecMenu(RestPARAM param, String realPath) {
		//파일 삭제
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		if(list.size() == 1) {
			RestRecMenuVO item = list.get(0);
			
			if(item.getMenu_pic() !=null && !item.getMenu_pic().equals("")) {
				File file = new File(realPath + item.getMenu_pic());
				if(file.exists()) {
					if(file.delete()) {
						return mapper.delRestMenu(param);
					}else {
						return 0;
					}
				}
			}
		}
		return mapper.delRest(param);
	}
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		
		//서블릿 컨택스트의 객체가 넘어옴
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
			
			//각 파일 저장
			MultipartFile mf = fileList.get(i);
			
			if(mf.isEmpty()) {continue;}
			
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID()+ext;
			
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
	
	int addRecMenus(HttpServletRequest request) {
		String savePath = "/resources/img/restaurant";
		String tempPath = request.getServletContext().getRealPath(savePath + "/temp");	
		FileUtils.makeFolder(tempPath);
		
		int maxFileSize = 52_428_800; //1024 * 1024 * 10 (10mb) //최대 파일 사이즈 크기
		MultipartRequest multi = null;
		int i_rest = 0;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		List<RestRecMenuVO> list = null;
		try {
			multi = new MultipartRequest(request, tempPath, maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
			
			i_rest = CommonUtils.getIntParameter("i_rest", multi);
			System.out.println("i_rest : " + i_rest);
			menu_nmArr = multi.getParameterValues("menu_nm");
			menu_priceArr = multi.getParameterValues("menu_price");
			
			if(menu_nmArr == null || menu_priceArr == null) {
				return i_rest;
			}
			
			list = new ArrayList();
			
			for(int i=0; i<menu_nmArr.length; i++) {
				RestRecMenuVO vo = new RestRecMenuVO();
				vo.setI_rest(i_rest);
				vo.setMenu_nm(menu_nmArr[i]);
				vo.setMenu_price(CommonUtils.parseStringToInt(menu_priceArr[i]));
				list.add(vo);
			}	
			
			
			String targetPath = request.getServletContext().getRealPath(savePath + "/" + i_rest);
			FileUtils.makeFolder(targetPath);
			
			String fileNm = "";
			String saveFileNm = "";
			Enumeration files = multi.getFileNames();
			while(files.hasMoreElements()) {		
				String key = (String)files.nextElement();
				System.out.println("key : " + key);
				fileNm = multi.getFilesystemName(key);
				System.out.println("fileNm : " + fileNm);
				
				if(fileNm != null) {
					String ext = FileUtils.getExt(fileNm);
					//랜덤으로 파일명 이름을 바꾸는 것
					//1. 중복방지
					//2. 한글 이미지 파일 사용 X
					saveFileNm = UUID.randomUUID() + ext;
					
					System.out.println("saveFileNm : " + saveFileNm);				
					File oldFile = new File(tempPath + "/" + fileNm);
				    File newFile = new File(targetPath + "/" + saveFileNm);
				    oldFile.renameTo(newFile);	
				    
				    int idx = CommonUtils.parseStringToInt(key.substring(key.lastIndexOf("_") + 1));				    
				    RestRecMenuVO vo = list.get(idx);
				    vo.setMenu_pic(saveFileNm);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(list != null) {
			for(RestRecMenuVO vo : list) {
				mapper.insRestRecMenu(vo);
			}	
		}
		
		return i_rest;
	}
	
}
