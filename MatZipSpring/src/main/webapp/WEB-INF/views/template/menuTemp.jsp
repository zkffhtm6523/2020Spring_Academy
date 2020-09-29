<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" type="text/css" href="/res/css/common.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<c:forEach items="${css}" var="item">
	<link rel="stylesheet" type="text/css" href="/res/css/${item}.css?dfdfdf=10">
</c:forEach>
</head>
<body>
	<div id="container">
		<header>
			<div id="headerLeft">
				<div class="logo"><a href="/rest/map"><img alt="맛집" src="/res/img/matzip.logo.png"></a></div> 
				<div class="containerPImg">
						<c:choose>
							<c:when test="${loginUser.profile_img != null}">
								<img class="pImg" src="/res/img/user/${loginUser.i_user}/${loginUser.profile_img}">
							</c:when>
							<c:otherwise>
								<img class="pImg" src="/res/img/default_profile.jpg">
							</c:otherwise>
						</c:choose>
					</div>
				<div class="ml15">${loginUser == null ? '손' : loginUser.nm}님 안녕하세요.</div>
				<c:if test="${loginUser == null}">
					<div class="ml15" id="headerLogout"><a href="/user/login">로그인</a></div>
				</c:if>
				<c:if test="${loginUser != null}">
					<div class="ml15"><a href="/user/logout">로그아웃</a></div>
				</c:if>
		 	</div>
		 	<div id="headerRight">
		 		<c:if test="${loginUser != null }">
		 			<a class="ml15" href="/rest/reg">등록</a>
		 		</c:if>
		 		<c:if test="${loginUser == null}">
			 		<a class="ml15" href="#" onclick="moveToReg()">등록</a>
		 		</c:if>
		 		<div class="ml35"><a href="/user/favorite">북마크</a></div>
			</div>
		 </header>
		<section>
			<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
		</section>
		<footer>
			<span>회사 정보</span>
		</footer>
	</div>
	<script type="text/javascript">
		function moveToReg() {
			alert('로그인이 필요합니다')
			location.href = '/user/login'
		}
	</script>
</body>
</html>