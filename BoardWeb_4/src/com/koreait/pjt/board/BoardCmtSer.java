package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.vo.BoardCmtVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardCmtSer
 */
@WebServlet("/board/cmt")
public class BoardCmtSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//댓글(삭제)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		
		String strI_cmt = request.getParameter("i_cmt");
		String strI_board = request.getParameter("i_board");
		int i_cmt = Integer.parseInt(strI_cmt);
		int i_board = Integer.parseInt(strI_board);
		
		BoardCmtVO param = new BoardCmtVO();
		param.setI_cmt(i_cmt);
		param.setI_board(i_board);
		BoardCmtDAO.delCmt(param);
		
		response.sendRedirect("/boardDetail?i_board="+param.getI_board());
	}
	//댓글(등록/수정)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		
		String strI_cmt = request.getParameter("i_cmt");
		String strI_board = request.getParameter("i_board");
		int i_board = Integer.parseInt(strI_board);
		int i_cmt = Integer.parseInt(strI_cmt);
		System.out.println("i_cmt : "+i_cmt);
		String cmt = request.getParameter("cmt");
		
		BoardCmtVO param = new BoardCmtVO();
		
		param.setI_user(loginUser.getI_user());
		param.setI_board(i_board);
		param.setCmt(cmt);
		param.setI_cmt(i_cmt);
		
		if(i_cmt == 0) {
			BoardCmtDAO.insCmt(param);
			response.sendRedirect("/boardDetail?i_board="+param.getI_board());
			return;
		} else {
			System.out.println("업데이트 테스트");
			BoardCmtDAO.updCmt(param);
			response.sendRedirect("/boardDetail?i_board="+param.getI_board());
			return;
		}
	}

}
