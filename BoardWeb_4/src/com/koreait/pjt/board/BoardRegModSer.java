package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.Const;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/board/regmod")
public class BoardRegModSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//화면 띄우는 용도(등록창/수정창)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		if(hs.getAttribute(Const.LOGIN_USER) == null) {
			response.sendRedirect("/login");
			return;
		}
		if(request.getParameter("i_user") == null) {
			ViewResolver.forward("/board/regmod", request, response);
		}else if(((UserVO)hs.getAttribute(Const.LOGIN_USER)).getI_user() == Integer.parseInt(request.getParameter("i_user"))) {
			BoardVO param = new BoardVO();
			int i_board = Integer.parseInt(request.getParameter("i_board"));
			param.setI_board(i_board);
			param = BoardDAO.selDetailBoardList(param);
			request.setAttribute("data", param);
			ViewResolver.forward("/board/regmod", request, response);
		}
	}
	
	//처리 용도(DB에 등록/수정) 실시
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		
		BoardVO param = new BoardVO();
		
		param.setTitle(swearWordFilter(imgFilter(scriptFilter(title))));
		param.setCtnt(swearWordFilter(imgFilter(scriptFilter(ctnt))));
		
		//글쓰기
		if(request.getParameter("i_user") == "") {
			UserVO u = (UserVO)hs.getAttribute(Const.LOGIN_USER);
			param.setI_user(u.getI_user());
			BoardDAO.insBoardList(param);
			response.sendRedirect("/board/list");
		//글수정
		}else {
			int i_board = Integer.parseInt(request.getParameter("i_board"));
			param.setI_board(i_board);
			BoardDAO.uptDetailBoardList(param);
			response.sendRedirect("/boardDetail?i_board="+i_board);
		}
	}
	//스크립트 필터(게시글 테러)
	private String scriptFilter(final String ctnt) {
		//필터링 필요한 스크립트 필터 매개변수로 받아옴
		String[] filters = {"<script>","</script>"};
		
		//스크립트 태그 들어왔을 때 이렇게 바꿔줌
		String[] filterResults = {"&lt;script&gt;","&lt;/script&gt;"};
		
		String result = ctnt;
		for (int i = 0; i < filterResults.length; i++) {
			result = result.replace(filters[i], filterResults[i]);
		}
		return result;
	}
	//이미지 필터(게시글 테러)
	private String imgFilter(final String ctnt) {
		//필터링 필요한 스크립트 필터 매개변수로 받아옴
		String[] filters = {"<img",">"};
		
		//스크립트 태그 들어왔을 때 이렇게 바꿔줌
		String[] filterResults = {"&lt;<img&gt","&lt;>&gt;"};
		
		String result = ctnt;
		for (int i = 0; i < filterResults.length; i++) {
			result = result.replace(filters[i], filterResults[i]);
		}
		return result;
	}
	private String swearWordFilter(final String ctnt) {
		String [] filters = {"개새끼","미친년","ㄱ ㅐ ㅅ ㅐ ㄲ ㅣ"};
		String result = ctnt;
		for (int i = 0; i < filters.length; i++) {
			result = result.replace(filters[i], "***");
		}
		return ctnt;
	}

}
