<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.container{margin: 0 auto;}
	img{width: 700px;}
</style>
</head>
<body>
	<div class="container">
		<h1>프로필</h1>
		<div>User_ID : ${data.user_id}</div>
		<div>이름 : ${data.nm }</div>
		<div>이메일 : ${data.email}</div>
		<div>수정날짜 : ${data.r_dt }</div>
		<img alt="프로필 이미지" src="${data.profile_img == null ? '/img/default_profile.jpg' : ''}">
	</div>
</body>
</html>