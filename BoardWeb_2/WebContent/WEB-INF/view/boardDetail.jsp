<%@page import="com.koreait.board.vo.BoardVO"%>
<%@page import="com.koreait.board.db.BoardDAO"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
<style>
	.container{width: 500px; margin: 0 auto;}
	h1{width: 190px; display: inline-block;}
	table, th, tr, td{border: 1px solid black; border-collapse: collapse;}
	th, td{padding: 10px;}
	.itemRow:hover{
		background-color: #ecf0f1;
		cursor:pointer;
	}
</style>
</head>
<body>
	<div class="container">
	<h1>상세 페이지</h1>
	<a href="/boardWrite"><button>글쓰기</button></a>
	<button onclick="doDel(${data.i_board})">삭제</button>
	<a href="/boardMod?i_board=${data.i_board }"><button>수정</button></a>
	<a href="/boardList"><button>이전</button></a>
	<div class="err">${msg}</div>
	<table>
		<tr>
			<th>No</th>
			<th>학생번호</th>
			<th>제목</th>
			<th>내용</th>
		</tr>
		<tr class="itemRow">
		<!-- EL식,, 위에 getAttribute 안 해도 됨 알아서 getter를 가져옴-->
		<!-- setattribute 한 것만 쓸 수 있음. -->
		<!-- pagecontext, request, session, application 4개에 있는 것만 사용할 수 있음 -->
		<!-- pageContext.setAttribute("b","ddd");
			${b.title}
		 -->
			<td>${data.i_board}</td>	
			<td>${data.i_student }</td>	
			<td>${data.title }</td>	
			<td>${data.ctnt }</td>
		</tr>	
	</table>
	</div>
	<script>
	
		function doDel(i_board) {
			if(confirm('삭제하시겠습니까?')){
				location.href='/boardDel?i_board='+i_board
			}
		}
	</script>
</body>
</html>