package com.koreait.pjt.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;

@WebServlet("/board/like")
public class BoardLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//리스트 가져오기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");
		int i_board = Integer.parseInt(strI_board);
		System.out.println("i_board : "+strI_board);
		BoardVO vo = new BoardVO();
		vo.setI_board(i_board);
		
		ArrayList<BoardVO> likeList = BoardDAO.likeListDetailBoardList(vo);
		
		Gson gson = new Gson();
		
		//gson라이브러리로 json 방식으로 바꿔줌
		String json = gson.toJson(likeList);
		
		System.out.println(json);
		
		response.setCharacterEncoding("UTF-8");
		//보내는 방식이 어떻다 알려주는 것
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		//html 문서로 받는거, json이 아닌 걸 알음
		out.print(json);
	}
	//좋아요 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
