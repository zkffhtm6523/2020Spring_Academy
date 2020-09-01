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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
	.container{width: 1200px; margin: 0 auto; text-align: center;}
	table{margin: 0 auto; margin-bottom: 20px;}
	table, tr, th, td{border: 1px solid #ccc; border-collapse: collapse;}
	th{padding: 10px; background-color: lightgray;}
	th, td{width: 100px;}
	h1{width: 240px;display: inline-block;}
	.regmod{width:100px; display: inline-block; text-align: right;}
	.regmod a{text-align: right; color: white; text-decoration: none; padding: 10px;background: #1e90ff}
	.itemRow:hover{
		background-color: #ecf0f1;
		cursor:pointer;
	}
	.cmt{color: red; font-weight: bold;}
	#currentPage{color: black; font-weight: bold; font-size: 1.5em;}
	.elsePage{text-decoration: none; color : blue; font-weight: bold;  font-size: 1.5em;}
	#currentPage, .elsePage{padding: 10px; margin: 10px;}
	.material-icons{padding: 15px;  position: relative; top: 4px;}
	.material-icons:hover{
		cursor:pointer;
	}
		.containerPImg {
		display: inline-block;	
		width: 30px;
		height: 30px;
	    border-radius: 50%;
	    overflow: hidden;
	}
	
	.pImg {
	
		 object-fit: cover;
		  max-width:100%;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<h3>${loginUser.user_id}님 환영합니다.</h3>
			<a href="/profile">프로필</a>
			<h1>게시판 리스트</h1>
			<!-- /붙이고 안 붙이고 중요한 것은 /안붙이면 마지막 주소의 /만 바뀌고 붙인다 -->
			<!-- /붙이면 localhost:8089에 /를 붙이고 시작한다.-->
			<div class="regmod"><a href="regmod">글쓰기</a></div>
			<div class="regmod"><a href="/logout">로그아웃</a></div>
		</div>
		<!-- 게시글 목록 수 -->
		<div>
			<form action="/board/list" id="selFrm" method="get">
				<input type="hidden" name="page" value="${page}">
				<input type="hidden" name="searchText" value="${param.searchText}">
				레코드 수 : 
				<select name="record_cnt" onchange="changeRecordCnt()">
					<c:forEach begin="10" end="30" step="10" var="item">
						<c:choose>
							<c:when test="${recordCnt == item}">
								<option value="${item}" selected>${item}개</option>
							</c:when>
							<c:otherwise>
								<option value="${item}">${item}개</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</form>
		</div>
		<div>param : ${param}</div>
		<div>recordCnt : ${recordCnt}</div>
		<table>
			<colgroup>
				<col width="9%"/>
				<col width="8%"/>
				<col width="15%">
				<col width="35%"/>
				<col width="10%"/>
				<col width="25%"/>
			</colgroup>
			<tr>
				<th>게시판 번호</th>
				<th>유저 번호</th>
				<th>유저명</th>
				<th>제목</th>
				<th>조회수</th>
				<th>작성 날짜</th>
			</tr>
			<!-- 게시판 목록 -->
			<c:forEach items="${list }" var="item">
			   <tr class="itemRow" onclick="moveToDetail(${item.i_board},${recordCnt},${page},'${searchText}')">
			      <td>${item.i_board}</td>
			      <td>${item.i_user}</td>
		      	  <td>
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
						${item.nm}
					</td>
			      <td>${item.nm}</td>
			      <td>${item.title}&nbsp;&nbsp;<span class="cmt">${item.countCmt == 0 ? '' : [item.countCmt]}</span></td>
			      <td>${item.hits}</td>
			      <td>${item.r_dt}</td>
			   </tr>
			</c:forEach>
		</table>
		<div>
		<!-- 게시글 검색 -->
			<form action="/board/list">
				<input type="hidden" name="record_cnt" value="${recordCnt}">
				<input type="hidden" name="page" value="${page}">
				<input type="text" name="searchText" value="${searchText}">
				<input type="submit" value="검색">
			</form>
		</div>
		<!-- 게시판 페이지 수 -->
		<span class="material-icons" onclick="moveToBefore(${page},${recordCnt},'${searchText}')">navigate_before</span>
		<c:forEach var="item" begin="1" end="${pagingCnt}" step="1">
			<c:choose>
				<c:when test="${page == item}">
					<span id="currentPage">${item}</span>			
				</c:when>
				<c:otherwise>
					<span><a href="/board/list?page=${item}&&record_cnt=${param.record_cnt}&&searchText=${searchText}" class="elsePage">${item}</a></span>			
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<span class="material-icons" onclick="moveToAfter(${page},${pagingCnt},${param.record_cnt},'${searchText}')">navigate_next</span>
	</div>
	<script>
		function moveToDetail(i_board, param, page,searchText) {
			location.href = '/boardDetail?page='+page+'&record_cnt='+param+'&i_board='+i_board+'&searchText='+searchText
		}
		function moveToBefore(page, param,searchText) {
			if(page >= 2){
				param = param == undefined ? '' : param
				var before = page - 1;
				location.href = '/board/list?page='+before+'&record_cnt='+param+'&searchText='+searchText
			}
		}
		function moveToAfter(page,maxPage, param,searchText) {
			if(page < maxPage){
				param = param == undefined ? '' : param
				var after = page + 1;
				location.href = '/board/list?page='+after+'&record_cnt='+param+'&searchText='+searchText
			}
		}
		function changeRecordCnt() {
			selFrm.submit()
		}
	</script>
</body>
</html>