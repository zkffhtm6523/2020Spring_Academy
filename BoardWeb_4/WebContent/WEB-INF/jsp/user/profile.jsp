<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.container{margin: 0 auto;}
	img{width: 400px;}
</style>
</head>
<body>
	<div class="container">
		<h1>프로필</h1>
		<div><a href="/board/list"><button>목록 가기</button></a></div>
		<div><a href="/changePw"><button>비밀번호 변경</button></a></div>
		<div>User_ID : ${data.user_id}</div>
		<div>이름 : ${data.nm }</div>
		<div>이메일 : ${data.email}</div>
		<div>수정날짜 : ${data.r_dt }</div>
		<div>
			<form action="/profile" method="post" enctype="multipart/form-data">
				<div>
					<label>프로필 이미지 : 
						<input type="file" name="profile_img" accept="image/*">
					</label>
					<input type="submit" value="업로드">
				</div>
			</form>
		</div>
		<div>
			<c:choose>
				<c:when test="${data.profile_img != null}">
					<img src="/img/user/${loginUser.i_user}/${data.profile_img}">
				</c:when>
				<c:otherwise>
					<img src="/img/default_profile.jpg">
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
<script type="text/javascript">
	var proc = '${param.proc}'
	
	switch (proc) {
	case '1':
		alert('비밀번호를 변경하였습니다.')
		break;
	}
</script>
</html>