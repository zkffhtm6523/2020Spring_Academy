<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="board.user.*"%>
<%@ page import="java.sql.*"%>
<%
	/*내장객체 사용할 수 있음*/
	/*request.getParameter(arg0);*/
	request.getParameter("");
	/*
	pageContext : 화면 뷰단에만 살아있음
	Request : 다음 장까지
	Session : 브라우저 끄기 전 까지
	application : 서버 끄기 전 까지
	*/
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
	<script>
		var check = confirm('접속하시겠습니까?');
		if(check == true){
	<%
	try{
		Conn.getCon();
	%>
		alert('접속 성공');
	<%			
		}catch(Exception e){
			e.printStackTrace();
		}
	%>
	}else{
		System.out.println("다시 시도");
	}
	</script>
</head>
<body>
	<div>게시판 리스트</div>
</body>
</html>