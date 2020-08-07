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

@WebServlet("/boardDetail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardDetailSer() {
        super();
    }
    //doGet방식 주로 하는 일, 화면 띄우기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVO param = new BoardVO();
		
		String strI_board = request.getParameter("i_board");
//		int i_board = Integer.parseInt(strI_board);
		int i_board = Utils.parseStrToInt(strI_board,0);
		//파라미터 string과 int값을 받을거임. "1111로 변환하는 것, 문자가 섞여있다면 0을 리턴" 
		
		//i_board가 0이면 게시판 목록으로 다시 돌려보냄.
		if(i_board == 0) {
			//alert로 띄우고 싶으면 jsp에서 자바스크립트로 보낸 다음에 거기서 보내줘야됨
			response.sendRedirect("/boardList");
			return;
		}
		
		param.setI_board(i_board);
		//i_board를 바로 안 보내고 
		BoardVO data = BoardDAO.selBoardDetail(param);

		request.setAttribute("data", data);
		request.getRequestDispatcher("/WEB-INF/view/boardDetail.jsp").forward(request, response);
		
//		String jsp = "/WEB-INF/view/boardDetail.jsp";
//		request.getRequestDispatcher(jsp).forward(request, response);
	}
}
