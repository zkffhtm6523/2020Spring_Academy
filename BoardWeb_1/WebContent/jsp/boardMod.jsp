<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.user.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.koreait.web.*"%>
<%@ page import="java.util.*"%>
<%	
	Conn conn = new Conn();
	//쿼리스트링 방식을 쓸 때는 getParameter로 받아온다.
	//http 통신은 요청이 오고 응답이 왔으면 바로 끊음. 그래서 con,rs,ps가 유지될 필요가 없다.
	//게임서버와 streaming서버는 계속 유지 상태
	//자바의 패키지명은 소문자로 시작, 클래스명은 대문자로 시작
	String strI_board = request.getParameter("i_board");
	String strTitle = request.getParameter("title");
	String sql = "SELECT title, ctnt, i_student FROM t_board WHERE i_board = ?";
	//-----------------------------------------
	int i_board = Integer.parseInt(strI_board);
	//-----------------------------------------
	PreparedStatement ps = null;
	ResultSet rs = null;
	BoardVO vo1 = new BoardVO();
	try{
		
		ps = conn.getCon().prepareStatement(sql);
		//-----------------------------------------
		//insert문에 쓰임
		ps.setInt(1,i_board); //setInt(물음표의 위치, 물음표에 들어갈 숫자)
		//-----------------------------------------
		rs = ps.executeQuery();
		if(rs.next()){ //while문이나 if문 둘 중 하나 써야함
			
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
		if (Conn.con != null) {try {conn.closeCon();} catch (Exception e) {}}
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
	<div><a href="/jsp/boardList.jsp">이전으로 가기</a></div>
	<div>
		<a href="#" onclick="procDel(<%=i_board %>)">삭제</a>
		<!-- 무엇을 수정할 것인지 쿼리스트링 보내줘야됨 -->
	</div>
	<div>
		<form id="frm" action="/jsp/boardModProc.jsp?i_board=<%=i_board %>" method="post" onsubmit="return chk()">
			<div><label for = "title">제목 : <input type="text" name="title" id="title" value="<%=vo1.getTitle()%>"></label></div>
			<div><label for = "ctnt">내용: <input type="text" name="ctnt" id="ctnt" value="<%=vo1.getCtnt() %>" ></input></label></div>
			<div><label for = "i_student">작성자 : <input type="text" name="i_student" id="i_student" value="<%=vo1.getI_student()%>"></label></div>
			<div><input type="submit" value="수정"></div>
		</form>
	</div>
		<script>
			function procDel(i_board){
				alert('i_board : '+i_board);
				if(confirm('삭제하시겠습니까?')){
					location.href = '/jsp/boardDel.jsp?i_board='+i_board;
				}
			}
		</script>
</body>
</html>