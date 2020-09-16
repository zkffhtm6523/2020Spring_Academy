package com.koreait.matZip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.koreait.matZip.CommonUtils;
import com.koreait.matZip.FileUtils;
import com.koreait.matZip.vo.RestaurantDomain;
import com.koreait.matZip.vo.RestaurantRecommendMenuVO;
import com.koreait.matZip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantService {
	
	private RestaurantDAO dao;
	
	public RestaurantService() {
		dao = new RestaurantDAO();
	}
	
	public int restReg(RestaurantVO param) {
		return dao.insRestaurant(param);
	}
	//레스토랑 리스트 가져오기
	public String getRestList(){
		List<RestaurantDomain> list = dao.selRestList(); 
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	public RestaurantDomain getRest(RestaurantVO param) {
		return dao.selRest(param);
	}
	//추천메뉴 추가
	public int addRecMenus(HttpServletRequest request) {
		String savePath = "/res/img/restaurant";
		String tempPath = request.getServletContext().getRealPath(savePath + "/temp");		
		FileUtils.makeFolder(tempPath);
		
		int maxFileSize = 52_428_800; //1024 * 1024 * 10 (10mb) //최대 파일 사이즈 크기
		MultipartRequest multi = null;
		int i_rest = 0;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		List<RestaurantRecommendMenuVO> list = null;
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
				RestaurantRecommendMenuVO vo = new RestaurantRecommendMenuVO();
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
				    RestaurantRecommendMenuVO vo = list.get(idx);
				    vo.setMenu_pic(saveFileNm);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(list != null) {
			for(RestaurantRecommendMenuVO vo : list) {
				dao.insRecommendMenu(vo);
			}	
		}
		
		return i_rest;
		}
	//추천메뉴 가져오기
	public List<RestaurantRecommendMenuVO> getRecommendMenuList(int i_rest) {
		return dao.selRecommendMenuList(i_rest);
	}
	//추천 메뉴 삭제
	public int delRecMenu(RestaurantRecommendMenuVO param) {
		return dao.delRecommendMenu(param);
	}
	//메뉴 여러개 한번에 추가
	public int addMenus(HttpServletRequest request) { //메뉴 
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		System.out.println("addMenus->i_rest : "+i_rest);
		
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant");
		String tempPath = savePath + "/"+i_rest+"/menu";//임시	
		FileUtils.makeFolder(tempPath);
		List<RestaurantRecommendMenuVO> list = new ArrayList<RestaurantRecommendMenuVO>();
        try {
        	for (Part part : request.getParts()) {
                String fileName = FileUtils.getFileName(part);
//                					part.getSubmittedFileName();
                if(fileName != null) {
                	RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
                	String ext = FileUtils.getExt(fileName);
                	String saveFileNm = UUID.randomUUID() + ext;
                	param.setI_rest(i_rest);
                	param.setMenu_pic(saveFileNm);
                	//파일 저장 부분. request에 있는 file을 getPart에 리스트로 넘어올 것이고, 그것을 저장
                	part.write(tempPath + "/" + saveFileNm);
                	list.add(param);
                }
            }      
        } catch(Exception e) {
        	e.printStackTrace();
        }
        if(list != null) {
			for(RestaurantRecommendMenuVO vo : list) {
				dao.insMenu(vo);
			}	
		}
        


		return i_rest;
	}
	
	public List<RestaurantRecommendMenuVO> getMenuList(int i_rest){
		return dao.selMenuList(i_rest);
	}

	
}
