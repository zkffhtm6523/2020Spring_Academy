package com.koreait.pjt.board;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.Const;
import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardDetailSer
 */
@WebServlet("/boardDetail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session의 속성값 가져옴
		UserVO loginUser = MyUtils.getLoginUser(request);

		//로그인 안 되어 있을 시 로그인 페이지로 보냄
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		
		//게시판 목록->상세화면으로 선택한 값의 i_boardf를 받아오고 vo에 값 저장
		String strI_board = request.getParameter("i_board");
		int i_board = Integer.parseInt(strI_board);
		BoardVO param = new BoardVO();
		param.setI_board(i_board);

		//application 받아오기 -> application에 속성값 받아줌(초기에는 null이 들어감)
		ServletContext application = getServletContext();
		Integer readI_user = (Integer)application.getAttribute("read_"+strI_board);
		//int를 썼으면 null이 넘어와서 에러터짐. integer는 null을 담을 수 있음
		//application에 null이거나, application과 loginUser와 같지 않다면 조회수 올려줌
		if(readI_user == null || readI_user != loginUser.getI_user()) {
			BoardDAO.hitDetailBoardList(param);
			//application에 속성값 넣어줌, 다음에는 조회수가 안 올라감
			application.setAttribute("read_"+strI_board, loginUser.getI_user());
		}
		
		
		param = BoardDAO.selDetailBoardList(param);
		param = BoardDAO.likeDetailBoardList(param);
		
		request.setAttribute("data", param);
//		ViewResolver.forwardLoginChk("board/detail", request, response);
		ViewResolver.forward("board/detail", request, response);
	}
}
