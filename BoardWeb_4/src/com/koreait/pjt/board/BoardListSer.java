package com.koreait.pjt.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.Const;
import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardCmtVO;
import com.koreait.pjt.vo.BoardVO;

@WebServlet("/board/list")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session을 먼저 받아오고 getattribute를 해야함
		//매개변수에 request가 있고 넘어오고, 넘겨주니까 이것을 바로 씀.
		//session은 받아오는 것이 없으니까 request로 getSession으로 받아와야함
		//pageContext는 서블릿이 아닌 jsp파일에서 사용할 수 있음.
		HttpSession hs = request.getSession();
		if(hs.getAttribute(Const.LOGIN_USER) == null) {
			response.sendRedirect("/login");
			return;
		}
		
		//게시글 찾기 로직
		String searchText = request.getParameter("searchText");
		searchText = (searchText == null ? "" : searchText);
		
		//-----------------------------------------------------------
		//페이지 숫자, 레코드 카운트(게시글 표시갯수) 받아오기
		int page = MyUtils.getIntParameter(request, "page");
		page = (page == 0 ? 1 : page);
		
		int recordCnt = MyUtils.getIntParameter(request, "record_cnt");
		recordCnt = (recordCnt == 0 ? 10 : recordCnt);
		//-----------------------------------------------------------
		//댓글 카운트 구현
		BoardCmtVO cmt = new BoardCmtVO();
		cmt = BoardCmtDAO.selCountCmt(cmt);
		
		//페이지 표시할 갯수
		BoardVO param = new BoardVO();
		param.setRecord_cnt(recordCnt); //개시글 표시 갯수
		param.setSearchText("%"+searchText+"%");
		int pagingCnt = BoardDAO.selPagingCnt(param);
		//이전 레코드수 값이 있고, 이전 레코드수보다 변경한 레코드 수가 더 크면 마지막 페이지 수로 변경
		if(page > pagingCnt) {
			page = pagingCnt; // 마지막 페이지 값으로 변경
		}
		request.setAttribute("page", page); 
		
		param.setEldx(page*recordCnt);
		param.setSldx(param.getEldx()-recordCnt);
		
		//이전 다음 용
		request.setAttribute("recordCnt", recordCnt);
		//게시판 목록 최대 갯수
		request.setAttribute("searchText", searchText);
		request.setAttribute("pagingCnt", pagingCnt); // ok
		request.setAttribute("list", BoardDAO.selBoardList(param)); // ok
		
		ViewResolver.forward("/board/list", request, response);
	}
}
