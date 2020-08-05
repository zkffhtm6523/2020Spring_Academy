<%@page import="com.koreait.board.vo.BoardVO"%>
<%@page import="com.koreait.board.db.BoardDAO"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//String strI_board = request.getParameter("i_board");
	//Type safety: Unchecked cast from Object to 없애주기
	@SuppressWarnings("unchecked")
	List<BoardVO> list = (List<BoardVO>)(request.getAttribute("data"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
</head>
<body>
	<div>
	게시판 리스트
	<a href="/boardWrite"><button>글쓰기</button></a>
	</div>
	<%for(int i = 0;i < BoardDAO.selBoardList().size();i++){ %>
		<div>
			<%=BoardDAO.selBoardList().get(i).getI_board() %>	
			<%=BoardDAO.selBoardList().get(i).getI_student() %>	
			<%=BoardDAO.selBoardList().get(i).getTitle() %>	
			<%=BoardDAO.selBoardList().get(i).getCtnt() %>
		</div>	
	<%} %>
	<hr>
	<%for(BoardVO vo: BoardDAO.selBoardList()){ %>
		<div>
			<%=vo.getI_board() %>	
			<%=vo.getI_student() %>	
			<%=vo.getTitle() %>	
			<%=vo.getCtnt() %>
		</div>	
	<%} %>
	<hr>
	<%for(BoardVO vo1: list){ %>
		<div>
			<%=vo1.getI_board() %>	
			<%=vo1.getI_student() %>	
			<%=vo1.getTitle() %>	
			<%=vo1.getCtnt() %>
		</div>	
	<%} %>
	
</body>
</html>