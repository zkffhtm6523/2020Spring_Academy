package com.koreait.pjt.user;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/profile")
public class ProfileSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//나의 프로필 화면(나의 프로필 이미지, 이미지 변경 가능한 화면)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		System.out.println(loginUser.getI_user());
		request.setAttribute("data", UserDAO.selUser(loginUser.getI_user()));
		ViewResolver.forward("user/profile", request, response);
	}
	//이미지 변경 처리(무조건 무조건 post) 방식
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		//어플리케이션 호출. 절대 경로
		String savePath = getServletContext().getRealPath("img") + "/user/" + loginUser.getI_user(); //저장경로
		System.out.println("savePath : "+savePath);
		
		//만약 폴더(디렉토리)가 없다면 폴더 생성
		File directory = new File(savePath);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		int maxFileSize = 10_485_760;
		//1024 * 1024 * 10(10mb) 매번 곱하는 것보다 곱한 값이 빠르다.
		String fileNm = "";
		String originFileNm = "";
		
		String saveFileNm = null;
		
		try {
			//이름 중복되면 자동으로 이름 바꿔서 저장해줌. 저장 후 파일이름 변경해야됨
			MultipartRequest mr = new MultipartRequest(request, savePath, 
					maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
			System.out.println("mr : " + mr);
			Enumeration files = mr.getFileNames();
			
			//파일의 다음 엘리멘트가 더 있냐
			while(files.hasMoreElements()) {
				String key = (String)files.nextElement();
				fileNm = mr.getFilesystemName(key);
				originFileNm = mr.getOriginalFileName(key);
				System.out.println("originFileNm : "+originFileNm);
				//확장자 추출
				int pos = fileNm.lastIndexOf( "." );
				String ext = fileNm.substring(pos);
				System.out.println(pos);
				System.out.println(ext);
				
				//form 태그의 name(key값)
				System.out.println("str : "+key);
				System.out.println("fileNm : "+fileNm);
				
				//UUID : 범용 고유 식별자

				//예전 파일
				File oldFile = new File(savePath+"/"+fileNm);
				//공파일 만들기
//				File newFile = new File(savePath+"/"+"dgdgdgdg.jpg");
				saveFileNm = UUID.randomUUID()+ext;
				File newFile = new File(savePath+"/"+saveFileNm);
				System.out.println("newFile: "+newFile);
				oldFile.renameTo(newFile);
				System.out.println("newFile: "+newFile);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//DB에 프로필 파일명 저장
		if(saveFileNm != null) {
			UserVO param = new UserVO();
			param.setProfile_img(saveFileNm);
			param.setI_user(loginUser.getI_user());
			UserDAO.updUser(param);
		}
		response.sendRedirect("/profile");
	}

}
