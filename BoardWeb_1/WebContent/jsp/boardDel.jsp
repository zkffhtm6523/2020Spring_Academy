<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.user.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.koreait.web.*"%>
<%@ page import="java.util.*"%>
<%	
	Conn conn = new Conn();
	String strI_board = request.getParameter("i_board");
	if(strI_board == null){
%>	
	<script>
		alert('잘못된 접근입니다.');
		location.href = '/jsp/boardList.jsp';
	</script>	
<% return;}%>
<%
	String sql = " DELETE FROM t_board WHERE i_board = ? ";
	//-----------------------------------------
	//이 부분을 예외 안 터지게 만들어줘야함.
	int i_board = Integer.parseInt(strI_board);
	int result = -1;//오류 분석 방법
	
	//-----------------------------------------
	PreparedStatement ps = null;
	try{
		ps = conn.getCon().prepareStatement(sql);
		ps.setInt(1,i_board); //setInt(물음표의 위치, 물음표에 들어갈 숫자)
		result = ps.executeUpdate();
		//-----------------------------------------
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		// 한 try-catch로 넣으면, 에러 터졌을 시 나머지는 안 닫고 넘어가짐. 이 방식이 FM
		if (ps != null) {try {ps.close();} catch (Exception e) {}}
		if (Conn.con != null) {try {conn.closeCon();} catch (Exception e) {}}
	}
	/*
	1번 방법
	switch(result){
		case -1 : System.out.println("삭제 실패"); response.sendRedirect("boardDetail.jsp?err=-1&i_board="+i_board); break;
		case 0 : System.out.println("삭제 실패"); response.sendRedirect("boardDetail.jsp?err=0&i_board="+i_board); break;
		case 1 : System.out.println("삭제 성공"); response.sendRedirect("boardList.jsp"); break; 
	}
	*/
	//자바스크립트로 이동 : 창 띄우고 이동하고 싶을 때
	
	//jsp로 이동 : 그냥 이동하고 싶을 때
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삭제 페이지</title>
<style type="text/css">
</style>
</head>
<body>
	<script>
	//2번 방법 : JS로 구현
	<%if(result == 1){%>
		alert('삭제 성공');
	//			location.href = 'boardList.jsp';
	<%}else if(result == 0){%>
		alert('삭제 실패');
		location.href = 'boardDetail.jsp';
	<%}else if(result == -1){%>
		alert('삭제 실패');
		location.href = 'boardDetail.jsp';
	<%}%>
	</script>
	
</body>
</html>