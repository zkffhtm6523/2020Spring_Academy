<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.user.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.koreait.web.*"%>
<%@ page import="java.util.*"%>
<%	
	Conn.getCon();
	//쿼리스트링 방식을 쓸 때는 getParameter로 받아온다.
	//http 통신은 요청이 오고 응답이 왔으면 바로 끊음. 그래서 con,rs,ps가 유지될 필요가 없다.
	//게임서버와 streaming서버는 계속 유지 상태
	//자바의 패키지명은 소문자로 시작, 클래스명은 대문자로 시작
	String strI_board = request.getParameter("i_board");
	String strTitle = request.getParameter("title");
	String sql = "SELECT title, ctnt, i_student FROM t_board WHERE i_board = "+strI_board;
	PreparedStatement ps = null;
	ResultSet rs = null;
	BoardVO vo1 = new BoardVO();
	try{
		ps = Conn.getCon().prepareStatement(sql);
		rs = ps.executeQuery();
		if(rs.next()){
			
		String title = rs.getNString("title");
		String ctnt = rs.getNString("ctnt");
		int i_student = rs.getInt("i_student");
			
		vo1.setTitle(title);
		vo1.setCtnt(ctnt);
		vo1.setI_student(i_student);
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		// 한 try-catch로 넣으면, 에러 터졌을 시 나머지는 안 닫고 넘어가짐. 이 방식이 FM
		if (rs != null) {try {rs.close();} catch (Exception e) {}}
		if (ps != null) {try {ps.close();} catch (Exception e) {}}
		if (Conn.con != null) {try {Conn.closeCon();} catch (Exception e) {}}
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 페이지</title>
<style type="text/css">
.container {
	width: 500px;
	margin: 0 auto;
	text-align: center;
	
}

table, th, td {
	border: 1px solid black;
	text-align : center;
}

table {
	border-collapse: collapse;
	margin: 0 auto;
	
}

th, td {
	padding: 10px
	
}

.col2 {
	width: 200px;
}

.title2 {
	background-color: lightgray;
}
</style>
</head>
<body>
	<!-- 단위 테스트 : 과정 별로 테스트해야함 -->
	<!-- 그래픽카드의 코어는 200개정도, 단순한 작업을 효율적으로 -->
	<!-- 특별한 경우 아니면 parameter 앞에 final을 붙이면 속도가 빨라진다. -->
	<!-- 내장객체들이 있다. -->
	<table>
			<tr class="title2">
				<th class="col2">게시판 번호</th>
				<th class="col2">학생 번호</th>
				<th class="col2">제목<br>(DB)</th>
				<th class="col2">제목<br>(Get)</th>
				<th class="col2">내용</th>
			</tr>
			<tr>
				<td><%=strI_board %> </td>
				<td><%=vo1.getI_student() %></td>
				<td><%=vo1.getTitle()%></td>
				<td><%=strTitle%></td>
				<td><%=vo1.getCtnt()%></td>
			</tr>
		</table>
		
	
</body>
</html>