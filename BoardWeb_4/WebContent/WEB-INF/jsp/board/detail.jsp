<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import = "java.util.List" %>
<%@ page import = "com.koreait.pjt.vo.BoardVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기 페이지</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
	body{background: lightgray;}
	.container{width: 600px; margin:20px auto; background-color: white;
	height: 800px; padding: 20px 50px 20px 50px; }
	.hr1{height: 3px; background: black; border: 0px; margin-top: 0px;}
	h2{margin-bottom: 10px;}
	h3{margin-top: 10px; margin-bottom: 10px;}
	ul{margin-top: 10px;}
	ul li{display: inline-block;}
	li{margin-right: 10px;}
	.ctnt{margin: 20px auto;}
	.footer{width:500px; margin: 0 auto; background-color: black;}
	.list{ width: 100px; margin:20px auto; ;text-align: center; float: left; margin-left: 25%;}
	.list1{ width: 100px; margin:20px auto; ;text-align: center; float: left; width: 75px;}
	.list a, .list1 a{text-align: center; color: white; text-decoration: none; padding: 10px;background: #1e90ff;
		float: left;}
	form{width: 100px;float: left;}
	#like:hover{
	cursor:pointer;
	}
	#like{
	    position: relative;
    	top: 5px;
    	color:red;
	}
	.text1{font-weight: bold;}
</style>
</head>
<body>
	<div class="container">
	<h2>자유 게시판</h2>
	<hr class="hr1">
		<div class="h3title"><h3>${data.title}</h3></div>
		<ul>
			<li>${data.nm}&nbsp;&nbsp;&nbsp;|</li>
			<li>${data.r_dt}&nbsp;&nbsp;&nbsp;|</li>
			<li>조회수&nbsp;[${data.hits}] |</li>
			<li id="like" onclick="toggleLike(${data.yn_like})">
				<c:if test="${data.yn_like == 0 }">
					<span class="material-icons">favorite_border</span>
				</c:if>
				<c:if test="${data.yn_like == 1 }">
					<span class="material-icons">favorite</span>
				</c:if>
			</li>
			<li>[${data.likeCount}] |</li>
		</ul>
		<div class="likeList">
			<span class="text1">좋아요 리스트 : </span>
			<c:forEach items="${list }" var="item">
				<span>${item.nm} |</span>
			</c:forEach>
		</div>
		<hr>
		<div class="ctnt">${data.ctnt}</div>
		<hr>
		<div class="footer">
			<div class="list"><a href="/board/list">목록</a></div>
			<c:if test="${loginUser.i_user == data.i_user}">
				<div class="list1"><a href="/board/regmod?i_board=${data.i_board }&i_user=${data.i_user}">수정</a></div>
				<form action="/board/del" method="post" id="delFrm">
					<input type="hidden" name="i_board" value="${data.i_board}">
					<div class="list"><a href="#" onclick="submitDel()">삭제</a></div>
				</form>
			</c:if>
		</div>
		<script type="text/javascript">
			function submitDel() {
				var chk = confirm('삭제하시겠습니까?') 
				if(chk){
					delFrm.submit()
				}
			}
			function toggleLike(yn_like){
				location.href="/board/toggleLike?i_board=${data.i_board}&yn_like="+yn_like
			}
		</script>
	</div>
</body>
</html>