package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.SendResult;

import com.koreait.pjt.Const;
import com.koreait.pjt.MyUtils;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class TogglerLikeSer
 */
@WebServlet("/board/toggleLike")
public class TogglerLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session의 속성값 가져옴
		UserVO loginUser = MyUtils.getLoginUser(request);

		//로그인 안 되어 있을 시 로그인 페이지로 보냄
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		//파라미터 가져오기->형변환
		String strI_board = request.getParameter("i_board");
		String strYn_like = request.getParameter("yn_like");
		int yn_like = Integer.parseInt(strYn_like);
		int i_board = Integer.parseInt(strI_board);
		int i_user = loginUser.getI_user();
		
		BoardVO param = new BoardVO();
		
		param.setYn_like(yn_like);
		param.setI_board(i_board);
		param.setI_user(i_user);
		
		System.out.println("로그인 유저 정보 : "+param.getI_user());
		//좋아요가 있다면, 좋아요 제거
		if(param.getYn_like() == 1) {
			BoardDAO.delLikeDetailBoardList(param);
			System.out.println("삭제");
		//좋아요가 없다면, 좋아요 추가
		}else if(param.getYn_like() == 0) {
			BoardDAO.insLikeDetailBoardList(param);
			System.out.println("좋아요 추가");
		}
		//VO에 값 저장
		response.sendRedirect("/boardDetail?i_board="+param.getI_board());
	}
}
