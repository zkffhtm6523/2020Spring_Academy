package com.koreait.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board.db.BoardDAO;
import com.koreait.board.vo.BoardVO;

@WebServlet("/boardWrite")
public class BoardWriteSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BoardWriteSer() {
        super();
    }
    //삭제는 화면이 없어서 get으로 많이 한다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsp = "/WEB-INF/view/boardRegmod.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String jsp = "/WEB-INF/view/boardList.jsp";
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		String strI_student = request.getParameter("i_student");
		
		int i_student = Integer.parseInt(strI_student);
		if("".equals(title) || "".equals(ctnt) || "".equals(strI_student)){
			response.sendRedirect("/WEB-INF/view/boardWrite.jsp?err=10");
			return;
		}
		
		BoardVO param = new BoardVO();
		param.setCtnt(ctnt);
		param.setTitle(title);
		param.setI_student(i_student);
		
		BoardDAO.selBoardWrite(param);
		
		response.sendRedirect("/boardList");
		
	}

}
