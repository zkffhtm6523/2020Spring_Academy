<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%!
	public static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static String userName = "hr";
	public static String userPassword  = "koreait2020";
	/*이렇게 적는 것은 서블릿의 메소드 안에 들어가짐, 지역변수라서 private 못 붙임
	메소드 안에 메소드 구현 못함, 메소드 밖에다 내용을 적으려면 <%!로 해줘야함*/
	public Connection getCon() throws Exception{
		Connection con = DriverManager.getConnection(url,userName,userPassword);
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("접속성공");
		return con;
	}
%>
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
</head>
<body>
	<div>게시판 리스트</div>
	<script>
		var check = confirm('접속하시겠습니까?');
		if(check == false){
	<%
		try{
			getCon();
	%>
		alert('접속 성공');
	<%			
		}catch(Exception e){
			e.printStackTrace();
		}
	%>
	}
	</script>
</body>
</html>