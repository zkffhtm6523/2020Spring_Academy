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
	String ctnt = request.getParameter("ctnt");
	String strI_student = request.getParameter("i_student");
	if("".equals(title) || "".equals(ctnt) || "".equals(strI_student)){
		response.sendRedirect("/jsp/boardWrite.jsp?err=10");
		return;
	}
	int i_student = Integer.parseInt(strI_student);
	//접속 객체화
	Connection con = null;
	PreparedStatement ps = null;
	// nvl(max(i_board))+1 = i_board 값 중 제일 큰 값의 +1을 가져온다 . 다음번 숫자에 글 쓰려고
	// insert into의 value를 select로 대체
	String sql = " insert into t_board(i_board,title,ctnt,i_student)"
			+"select nvl(max(i_board),0)+1,?,?,?"
			+"from t_board" ;
	//BoardVO vo = new BoardVO();
	int result = -1;
	try{
		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setNString(1, title);
		ps.setNString(2, ctnt);
		ps.setInt(3, i_student);
		System.out.println("글쓰기 등록 완료");
		//1이 넘어오면 쿼리 실행된거임.
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
	int err = 0;
	switch(result){
		case 1:
			response.sendRedirect("/jsp/boardList.jsp");
			return;
			//response.sendRedirect는 두번 실행되면 안됨.
			//return이 자동으로 들어가짐.
		case 0:
			err = 10;
			break;
		case -1:
			err = 20;
			break;
	}
	response.sendRedirect("/jsp/boardWrite.jsp?err="+err);
%>
<!-- 2번 방법 : JS로 구현 
<script>
 
</script>
-->
