<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<style>
	body{text-align: center;}
	div {margin-top: 5px;}
	.err{color: red; font-weight: bold; font-size: 1.2em; margin: 20px;}
</style>
</head>
<body>
	<!-- 시맨틱의 h1은 매우 중요. -->
	<h1>회원가입</h1>
	<div class="err">${msg }</div>
	<a href="/login">로그인 하기</a>
	<div id="container">
		<div>
			<!-- id를 form에 준다 -->
			<form action="/join" id="frm" method="post" onsubmit="return chk()">
				<div><label><input type="text" name="user_id" placeholder="아이디" required value="${data.user_id }"></label></div>			
				<div><label><input type="password" name="user_pw" placeholder="비밀번호" required></label></div>			
				<div><label><input type="password" name="user_pwre" placeholder="비밀번호 확인" required></label></div>
				<div><label><input type="text" name="nm" placeholder="이름" required value="${data.nm }"></label></div>
				<div><label><input type="email" name="email" placeholder="이메일" required value="${data.email }"></label></div>
				<div><input type="submit" value="회원가입"></div>			
			</form>
		</div>
	</div>
</body>
<script>
	function chk() {
		//form 자체를 id로 받아온다.
		//아이디를 받아와서 하위의 name으로 접근 가능
		if(frm.user_id.value.length < 5){
			alert('아이디는 5글자 이상이 되어야합니다.')
			frm.user_id.focus()
			return false
		} 
		if(frm.user_pw.value.length < 8){
			alert('비밀번호는 8글자 이상이 되어야합니다.')
			frm.user_pw.focus()
			return false
		}
		if(frm.user_pw.value != frm.user_pwre.value){
			alert('비밀번호가 일치하지 않습니다.')
			frm.user_pwre.focus()
			return false
		//정규 표현식 : 이름을 한글만 들어갈 수 있도록
		}
		if(frm.nm.value.length > 0){
			const korean = /[^가-힣]/;//^부정 의미
			//korean 한글이 아닌게 맞다면
			if(korean.test(frm.nm.value)){
				alert('이름은 한글만 입력해주세요')
				frm.nm.focus()
				return false
			}
		}
		if(frm.email.value.length > 0) {
			console.log("ddd")
			const email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
			//email 형식이 아닌게 맞다면
			if(!email.test(frm.email.value)){
				alert('이메일을 확인해주세요')
				frm.email.focus()
				return false
			}
		}
		//if만 여러번 적어도 되는데 1줄이라도 줄이는 것, 가독성
	}
</script>
</html>