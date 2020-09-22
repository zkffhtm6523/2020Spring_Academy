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

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.model.RestaurantRecommendMenuVO;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecommendMenuVO;
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
	List<RestRecommendMenuVO> getRecommendMenuList(int i_rest){
		return mapper.selRecommendMenuList(i_rest);
	}
	
	int addRecMenus(HttpServletRequest request) {
		String savePath = "/res/img/restaurant";
		String tempPath = request.getServletContext().getRealPath(savePath + "/temp");	
		FileUtils.makeFolder(tempPath);
		
		int maxFileSize = 52_428_800; //1024 * 1024 * 10 (10mb) //최대 파일 사이즈 크기
		MultipartRequest multi = null;
		int i_rest = 0;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		List<RestRecommendMenuVO> list = null;
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
				RestRecommendMenuVO vo = new RestRecommendMenuVO();
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
				    RestRecommendMenuVO vo = list.get(idx);
				    vo.setMenu_pic(saveFileNm);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(list != null) {
			for(RestRecommendMenuVO vo : list) {
				mapper.insRecommendMenu(vo);
			}	
		}
		
		return i_rest;
	}
	
}
