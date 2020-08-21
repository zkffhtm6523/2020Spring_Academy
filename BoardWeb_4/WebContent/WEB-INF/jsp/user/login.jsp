<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
	*{outline: none; }
	.container{margin: 0 auto; text-align: center; width: 500px; }
	.err{color: red; font-weight: bold; font-size: 1.2em; }
	.form{background-color: #2C54A3;}
	input{margin: 10px; padding: 10px}
	.btn{padding: 10px;}
</style>
</head>
<body>
	<div class="container">
		<h1>로그인</h1>
		<div class="err">${msg }</div>
		<div class="form">
			<form id="frm" action="/login" method="post">
				<div><input type="text" name="user_id" placeholder="아이디" value="${data.user_id}"></div>
				<div><input type="password" name="user_pw" placeholder="비밀번호"></div>
				<div><input type="submit" value="로그인"></div>
			</form>
			<div class="btn"><a href="/join">회원가입</a></div>
		</div>
	</div>
</body>
</html>