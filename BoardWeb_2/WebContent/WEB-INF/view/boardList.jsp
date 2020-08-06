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
<style>
 .itemRow:hover{
 	background-color: #ecf0f1;
 	cursor:pointer;
 }
</style>
</head>
<body>
	<div>
	게시판 리스트
		<a href="/boardWrite"><button>글쓰기</button></a>
	</div>
	<table>
		<tr>
			<th>No</th>
			<th>학생번호</th>
			<th>제목</th>
			<th>내용</th>
		</tr>
		<%for(BoardVO vo1: list){ %>
		<tr class="itemRow" onclick="moveToDetail(<%=vo1.getI_board()%>)">
			<td><%=vo1.getI_board() %></td>	
			<td><%=vo1.getI_student() %></td>	
			<td><%=vo1.getTitle() %></td>	
			<td><%=vo1.getCtnt() %></td>
		</tr>	
		<%} %>
	</table>
	<script>
		function moveToDetail(i_board) {
			console.log("moveToDetail - i_board : "+i_board)
			location.href = 'boardDetail?i_board='+i_board
		}
	</script>
</body>
</html>