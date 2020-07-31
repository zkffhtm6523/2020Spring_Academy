<%@page import="com.koreait.web.BoardVO"%>
<%@page import="java.sql.*"%>
<%@page import="board.user.Conn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	Connection getCon() throws Exception{
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String username = "hr";
	String password = "koreait2020";
	
	Class.forName("oracle.jdbc.driver.OracleDriver");
	
	Connection con = DriverManager.getConnection(url, username, password);
	System.out.println("접속성공!");
	return con;
	}
%>
<%

	String title = request.getParameter("title");
	System.out.println(title);
	String ctnt = request.getParameter("ctnt");
	System.out.println(ctnt);
	String strI_student = request.getParameter("i_student");
	System.out.println(strI_student);
	int i_student = Integer.parseInt(strI_student);
	//접속 객체화
	Connection con = null;
	PreparedStatement ps = null;
	
	String sql = " insert into t_board(i_board,title,ctnt,i_student)"
			+"select nvl(max(i_board),0)+1,?,?,?"
			+"from t_board" ;
	//BoardVO vo = new BoardVO();
	int result = 0;
	try{
		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setNString(1, title);
		ps.setNString(2, ctnt);
		ps.setInt(3, i_student);
		System.out.println("글쓰기 등록 완료");
		result = ps.executeUpdate();
		System.out.println("글쓰기 등록 완료2");
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("등록 실패");
	}finally {
		// 한 try-catch로 넣으면, 에러 터졌을 시 나머지는 안 닫고 넘어가짐. 이 방식이 FM
		if (ps != null) {try {ps.close();} catch (Exception e) {}}
		if (con != null) {try {con.close();} catch (Exception e) {}}
	}
%>

<div>title : <%=title %></div>
<div>ctnt : <%=ctnt %></div>
<div>strI_student : <%=strI_student %></div>
<script>
	//2번 방법 : JS로 구현
	<%if(result == 1){%>
		alert('등록 성공');
		location.href = 'boardList.jsp';
	<%}else if(result == 0){%>
		alert('등록 실패');
		location.href = 'boardWrite.jsp';
	<%}else if(result == -1){%>
		alert('등록 실패');
		location.href = 'boardWrite.jsp';
	<%}%>
</script>
