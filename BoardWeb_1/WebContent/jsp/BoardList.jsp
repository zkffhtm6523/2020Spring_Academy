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
	List<BoardVO> boardList = new ArrayList<BoardVO>();

	//하이버네이트 jpa, 마이바티스, junit, python
	PreparedStatement ps = null; // 쿼리문 완성 및 실행 담당
	ResultSet rs = null; //결과를 담는 담당, select때만 필요
	String sql = " SELECT i_board, title FROM t_board ";//두 개만 가져오고 싶다.
	//세미콜론을 허용해주면 인젝션 공격이 가능해짐
	try {
	ps = Conn.getCon().prepareStatement(sql); // 접속 담당 및 쿼리문 완성 과정
	rs = ps.executeQuery(); //db데이터를 rs에 때려박음, sql의 결과물이 rs에 담겨있음
	
	while(rs.next()){
		int i_board = rs.getInt("i_board"); //적어주는 것은 컬럼명
		String title = rs.getNString("title");
		
		BoardVO vo = new BoardVO();
		vo.setI_board(i_board);
		vo.setTitle(title);
		
		boardList.add(vo);
	}
	
	} catch (Exception e) {
		e.printStackTrace();
	} finally{
		if(rs!=null){ try{rs.close();} catch(Exception e){}}
		if(ps!=null){ try{rs.close();} catch(Exception e){}}
		if(Conn.con != null) { try{Conn.closeCon();} catch(Exception e){}}
	}
%>
<style type="text/css">
	.container{width: 500px ; margin: 0 auto; text-align: center;}
	table, th, td{border: 1px solid black;}
	table{border-collapse: collapse; margin: 0 auto;}
	th, td{padding : 10px}
	.col2{width: 200px;}
	.title2{background-color: lightgray;}
</style>
</head>
<body>
	<div class="container">
	<div class="title"><h1>게시판 리스트</h1></div>
	<table>
		<tr class="title2">
			<th>No</th>
			<th class="col2">제목</th>
		</tr>
		<%for(BoardVO vo : boardList){%>
		<tr>
			<td><%=vo.getI_board() %></td>	
			<td class="col2"><%=vo.getTitle() %></td>
		</tr>	
		<% }%>
	</table>
	</div>
</body>
</html>