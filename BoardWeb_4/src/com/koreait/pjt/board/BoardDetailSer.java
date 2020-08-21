package com.koreait.pjt.board;

import java.io.IOException;
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

/**
 * Servlet implementation class BoardDetailSer
 */
@WebServlet("/boardDetail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(MyUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		String strI_board = request.getParameter("i_board");
		int i_board = Integer.parseInt(strI_board);
		BoardVO param = new BoardVO();
		param.setI_board(i_board);
		//조회수를 올려주세요!!
		BoardDAO.likeDetailBoardList(param);
		
		param = BoardDAO.selDetailBoardList(param);
		request.setAttribute("data", param);
//		ViewResolver.forwardLoginChk("board/detail", request, response);
		ViewResolver.forward("board/detail", request, response);
	}
}
