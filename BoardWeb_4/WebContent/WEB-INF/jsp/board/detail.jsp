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
	 padding: 20px 50px 20px 50px; }
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
	#like:hover{cursor:pointer;}
	#like{
	    position: relative;
    	top: 5px;
    	color:red;
	}
	.text1{font-weight: bold;}
	.comment{clear: both; width: 600px;}
	.comment1{width: 600px;; text-align: center;}
	.commentInput{display: inline-block;}
	#commentId0{width: 440px; padding: 10px;}
	.commentList{width:600px; text-align: center;
	margin: 50px auto;}
	table, th, tr, td{border: 1px solid black; border-collapse: collapse;
	margin: 0 auto;}
	table{margin-top: 20px;}
	td{padding: 10px;}
	.btn{color: blue;}
	.btn:hover {cursor:pointer;}
	.containerPImg {display: inline-block;width: 30px;height: 30px;border-radius: 50%;overflow: hidden;}
	.pImg {object-fit: cover;max-width:100%;}
	.highlight{color: red;font-weight: bold;}
}
</style>
</head>
<body>
	<div class="container">
	<h2>자유 게시판</h2>
	<hr class="hr1">
		<div class="h3title"><h3>${data.title}</h3></div>
		<ul>
			<li>
			<div class="containerPImg">
				<c:choose>
					<c:when test="${data.profile_img != null}">
						<img class="pImg" src="/img/user/${data.i_user}/${data.profile_img}">
					</c:when>
					<c:otherwise>
						<img class="pImg" src="/img/default_profile.jpg">
					</c:otherwise>
				</c:choose>
			</div>
			</li>
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
				<span>
				<div class="containerPImg">
				<c:choose>
					<c:when test="${item.profile_img != null}">
						<img class="pImg" src="/img/user/${item.i_user}/${item.profile_img}">
					</c:when>
					<c:otherwise>
						<img class="pImg" src="/img/default_profile.jpg">
					</c:otherwise>
				</c:choose>
				</div>
				${item.nm} |</span>
			</c:forEach>
		</div>
		<hr>
		<div class="ctnt">${data.ctnt}</div>
		<hr>
		<div>param.record_cnt : ${param.record_cnt}</div>
		<div>page : ${page}</div>
		<div class="footer">
			<div class="list"><a href="/board/list?page=${param.page}&record_cnt=${param.record_cnt}&searchText=${param.searchText}
			">목록</a></div>
			<c:if test="${loginUser.i_user == data.i_user}">
				<div class="list1"><a href="/board/regmod?i_board=${data.i_board }&i_user=${data.i_user}">수정</a></div>
				<form action="/board/del" method="post" id="delFrm">
					<input type="hidden" name="i_board" value="${data.i_board}">
					<div class="list"><a href="#" onclick="submitDel()">삭제</a></div>
				</form>
			</c:if>
		</div>
		<div class="comment">
			<form id="cmtFrm" action="/board/cmt" method="post">
				<!-- i_cmt 값이 0이면 등록, 1이면 수정 -->
				<input type="hidden" name="i_cmt" value="0">
				<input type="hidden" name="i_board" value="${data.i_board}">
				<div class="comment1">
					<input type="text" name="cmt" placeholder="댓글 내용" class="commentInput" id="commentId0">
					<input type="submit" value="전송" id="commentId" class="commentInput">
					<input type="button" value="삭제" id="commentId1" class="commentInput" onclick="clkCmtCancle()">
				</div>
			</form>
		</div>
		<div class="commentList">
			<h2>댓글 리스트</h2>
				<c:forEach items="${cmtlist }" var="item" >
					<table>
					   <tr>
					      <td class="i_cmt">댓글 번호 | ${item.i_cmt}</td>
					      <td>작성자명 | ${item.nm}</td>
					   </tr>
					   <tr>
					      <td colspan="2">${item.cmt}</td>
					   </tr>
					   <tr>
					      <td>작성날짜 | ${item.r_dt}</td>
					      <td>수정날짜 | ${item.m_dt}</td>
					   </tr>
					</table>
					<c:if test="${loginUser.i_user == item.i_user}">
						<span class="btn" onclick="uptInput(${item.i_cmt},'${item.cmt}')">수정</span>
						<span class="btn" onclick="delComment(${item.i_cmt})">삭제</span>
					</c:if>
				</c:forEach>
		</div>
		<script>
			function clkCmtCancle() {
				cmtFrm.i_cmt.value = 0
				cmtFrm.cmt.value = ''
				commentId.value = '전송'
			}
		
			function submitDel() {
				var chk = confirm('삭제하시겠습니까?') 
				if(chk){
					delFrm.submit()
				}
			}
			function toggleLike(yn_like){
				location.href="/board/toggleLike?i_board=${data.i_board}&yn_like="+yn_like
			}
			function uptInput(i_cmt, cmt) {
				commentId0.setAttribute('value', cmt)
				cmtFrm.i_cmt.setAttribute('value',i_cmt)
				commentId.value = '수정'
			}
			
			function delComment(i_cmt, cmt){
				var chk = confirm('댓글 삭제하시겠습니까?')
				if(chk){
					location.href="/board/cmt?i_cmt="+i_cmt+"&i_board=${data.i_board}"
				}
			}
			function doHighlight() {
				var searchText = '${param.searchText}'
				var searchtype = '${param.searchType}'
				
			}
		</script>
	</div>
</body>
</html>