package com.koreait.pjt.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardCmtVO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

@WebServlet("/boardDetail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session의 속성값 가져옴
		UserVO loginUser = MyUtils.getLoginUser(request);

		//로그인 안 되어 있을 시 로그인 페이지로 보냄
		
		//게시판 목록->상세화면으로 선택한 값의 i_boardf를 받아오고 vo에 값 저장
		String strI_board = request.getParameter("i_board");
		int i_board = Integer.parseInt(strI_board);
		BoardVO param = new BoardVO();
		param.setI_board(i_board);
		
		//게시글 찾기 로직
		String searchText = request.getParameter("searchText");
		searchText = (searchText == null ? "" : searchText);
		
		//제목, 제목+내용 찾기 기능
		String searchType = request.getParameter("searchType");
		searchType = (searchType == null) ? "a" : searchType;
		
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
		if(!"".equals(searchText) && ("a".equals(searchType) || "b".equals(searchType) || "c".equals(searchType))) {
			String title = param.getTitle();
			String ctnt = param.getCtnt();
			title = title.replace(searchText
					, "<span class=\"highlight\">" + searchText +"</span>");
			ctnt = ctnt.replace(searchText
					, "<span class=\"highlight\">" + searchText +"</span>");
			param.setTitle(title);
			param.setCtnt(ctnt);
		}
		param.setLoginUser(loginUser.getI_user());
		param = BoardDAO.likeDetailBoardList(param);
		
		
		
		ArrayList<BoardVO> list = BoardDAO.likeListDetailBoardList(param);
		
		//댓글 내용 불러오기
		BoardCmtVO cmtVO = new BoardCmtVO();
		cmtVO.setI_board(param.getI_board());
		
		List<BoardCmtVO> cmtList = BoardCmtDAO.selCmt(cmtVO); 
		
		//게시글 상세 속성 부여
		request.setAttribute("data", param);
		//댓글 속성 부여
		request.setAttribute("list", list);
		//댓글 목록 속성 부여
		request.setAttribute("cmtlist", cmtList);
		
//		ViewResolver.forwardLoginChk("board/detail", request, response);
		ViewResolver.forward("board/detail", request, response);
		}
	}

