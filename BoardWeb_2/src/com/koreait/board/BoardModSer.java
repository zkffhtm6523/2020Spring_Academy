package com.koreait.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board.common.Utils;
import com.koreait.board.db.BoardDAO;
import com.koreait.board.vo.BoardVO;

@WebServlet("/boardMod")
public class BoardModSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BoardModSer() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVO param = new BoardVO();
		String strI_board = request.getParameter("i_board");
		
		int i_board = Utils.parseStrToInt(strI_board,0);
		
		if(i_board == 0) {
			response.sendRedirect("/boardList");
			return;
		}
		
		param.setI_board(i_board);
		BoardVO data = BoardDAO.selBoardDetail(param);

		request.setAttribute("data", data);
		
		String jsp = "/WEB-INF/view/boardRegmod.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");
		String strI_student = request.getParameter("i_student");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		if("".equals(title) || "".equals(ctnt) || "".equals(strI_student) || "".equals(strI_board)){
			response.sendRedirect("/WEB-INF/view/boardWrite.jsp?err=10");
			return;
		}
		int i_board = Integer.parseInt(strI_board);
		int i_student = Integer.parseInt(strI_student);
		
		BoardVO param = new BoardVO();
		param.setCtnt(ctnt);
		param.setTitle(title);
		param.setI_student(i_student);
		param.setI_board(i_board);
		
		int result = BoardDAO.upBoard(param);
		
		if(result == 1) {
			response.sendRedirect("/boardDetail?i_board="+i_board);
		}else {
			request.setAttribute("msg", "에러가 발생하였습니다.");
			doGet(request, response);
		}
	}

}
