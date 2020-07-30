<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="board.user.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.koreait.web.*"%>
<%
	/*내장객체 사용할 수 있음*/
/*request.getParameter("");/*
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
<%
	Conn conn = new Conn();	//jsp service라는 메소드 안에 들어감
	List<BoardVO> boardList = new ArrayList<BoardVO>();
	//여러줄 가져올 때는 list를 사용해야 함.
	//하이버네이트 jpa, 마이바티스, junit, python
	PreparedStatement ps = null; // 쿼리문 완성 및 실행 담당
	ResultSet rs = null; //결과를 담는 담당, select때만 필요
	String sql = " SELECT i_board, title FROM t_board order by i_board asc ";//두 개만 가져오고 싶다.
	//세미콜론을 허용해주면 인젝션 공격이 가능해짐
	try {
		ps = conn.getCon().prepareStatement(sql); // 접속 담당 및 쿼리문 완성 과정
		rs = ps.executeQuery(); //db데이터를 rs에 때려박음, sql의 결과물이 rs에 담겨있음
	
		while (rs.next()) {
			//rs.next()의 리턴 타입은 true, false
			int i_board = rs.getInt("i_board"); //적어주는 것은 컬럼명
			String title = rs.getNString("title");
			
			//많이 중요함. while 반복문 안에서 만들어야함
			BoardVO vo = new BoardVO();
			vo.setI_board(i_board);
			vo.setTitle(title);
	
			boardList.add(vo);
		}
	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		// 한 try-catch로 넣으면, 에러 터졌을 시 나머지는 안 닫고 넘어가짐. 이 방식이 FM
		if (rs != null) {try {rs.close();} catch (Exception e) {}}
		if (ps != null) {try {ps.close();} catch (Exception e) {}}
		if (Conn.con != null) {try {conn.closeCon();} catch (Exception e) {}}
	}
%>
<style type="text/css">
.container {
	width: 500px;
	margin: 0 auto;
	text-align: center;
}

table, th, td {
	border: 1px solid black;
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
	<div class="container">
		<div class="title">
			<h1>게시판 리스트</h1>
		</div>
		<table>
			<tr class="title2">
				<th>No</th>
				<th class="col2">제목</th>
			</tr>
			<%
				for (BoardVO vo : boardList) {
			%>
			<tr>
				<td><%=vo.getI_board()%></td>
				<!-- 이렇게 다음 페이지에 보내는 방식이 GET방식 -->
				<!-- form의 submit을 했을 때 넘어가는 방식이 POST방식 -->
				<!-- Post와 Get방식이 둘 다 갔을 때 Get방식이 우선한다. -->
				<td class="col2">
														<!--쿼리 스트링  : 상세페이지, 글의 pk값을 보내준다, 그리고 클릭한 걸 DB에 알려준다-->
														<!--해쉬맵 : 앞쪽에 있는 것은 키값, value값은 뒤쪽, 매치되는 것을 알려준다-->
														<!-- 물음표 뒤부터 쿼리 스트링 -->
														<!-- 네이버 서치화면 살펴보기. sm=top_hty, 이렇게 나오는 게 GET방식 -->
														<!-- 아무것도 안나오는 것 POST방식 -->
					<!-- 여기서 보내야 다음 페이지에서 getParameter로 받을 수 있다 -->			
					<a href="/jsp/boardDetail.jsp?i_board=<%=vo.getI_board() %>&title=<%=vo.getTitle()%>">
						<%=vo.getTitle()%>
					<!-- 타이틀까지는 괜찮을 지 몰라도 게시판 내용을 가져오면 글이 많아서 traffic이 발생한다. -->
					</a>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>