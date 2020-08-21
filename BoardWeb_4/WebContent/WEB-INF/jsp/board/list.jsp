<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "com.koreait.pjt.vo.BoardVO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
<style type="text/css">
	.container{width: 1200px; margin: 0 auto; text-align: center;}
	table{margin: 0 auto;}
	table, tr, th, td{border: 1px solid black; border-collapse: collapse;}
	th{padding: 10px; background-color: lightgray;}
	th, td{width: 100px;}
	h1{width: 240px;display: inline-block;}
	.regmod{width:100px; display: inline-block; text-align: right;}
	.regmod a{text-align: right; color: white; text-decoration: none; padding: 10px;background: #1e90ff}
	.itemRow:hover{
		background-color: #ecf0f1;
		cursor:pointer;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<h3>${loginUser.user_id}님 환영합니다.</h3>
			<h1>게시판 리스트</h1>
			<!-- /붙이고 안 붙이고 중요한 것은 /안붙이면 마지막 주소의 /만 바뀌고 붙인다 -->
			<!-- /붙이면 localhost:8089에 /를 붙이고 시작한다.-->
			<div class="regmod"><a href="regmod">글쓰기</a></div>
			<div class="regmod"><a href="/logout">로그아웃</a></div>
		</div>
		<table>
			<colgroup>
				<col width="10%"/>
				<col width="8%"/>
				<col width="13%">
				<col width="26%"/>
				<col width="30%"/>
				<col width="13%"/>
			</colgroup>
			<tr>
				<th>게시판 번호</th>
				<th>유저 번호</th>
				<th>유저명</th>
				<th>제목</th>
				<th>작성 날짜</th>
				<th>조회수</th>
			</tr>
			<!-- items의 값을 el식으로 list 받아옴  var = 'item'은 item이라는 이름으로 pagecontext에 계속 박아줌 -->
			<c:forEach items="${list }" var="item" >
			   <tr class="itemRow" onclick="moveToDetail(${item.i_board})">
			      <td>${item.i_board}</td>
			      <td>${item.i_user}</td>
			      <td>${item.nm}</td>
			      <td>${item.title}</td>
			      <td>${item.r_dt}</td>
			      <td>${item.hits}</td>
			   </tr>
			</c:forEach>
		</table>
	</div>
	<script>
		function moveToDetail(i_board) {
			location.href = '/boardDetail?i_board='+i_board
		}
	</script>
</body>
</html>