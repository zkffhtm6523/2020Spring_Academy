<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록/수정</title>
<style type="text/css">
	.container{width: 600px; margin: 0 auto; }
	.title{padding: 10px; width: 500px; margin-bottom: 20px; margin-top: 20px;}
	textarea{padding: 10px; width: 500px; height: 300px;}
	.form {width:560px; margin: 0 auto; text-align: center;}
	h1{margin-left: 35px;}
	.btn1, .btn2{
	width: 100px; padding: 10px; display: inline-block; 
	}
	.btn1 input, .btn2 input{
	width: 100px; padding: 10px; display: inline-block; 
	background: #1e90ff; color: white; border: none;
	}
</style>
</head>
<body>
	<div class="container">
		<h1>글쓰기</h1>
		<hr>
		<div class="form">
			<form id="frm" action="regmod" method="post">
				<div><input type="text" name="title" placeholder="제목을 입력해주세요" class="title" value="${data.title }"></div>
				<div><textarea name="ctnt">${data == null ? '본문 내용과 함께 #태그를 입력하면 더 많은 사람들이 볼 수 있어요!' : '' }${data.ctnt}</textarea></div>
				<div><input type="text" name="i_user" value="${data.i_user}"></div>
				<div><input type="text" name="i_board" value="${data.i_board}"></div>
				<div class="btn1"><input type="submit" value="등록" onclick="return chk()"></div>
				<div class="btn2"><input type="reset" value="취소"></div>
			</form>
		</div>
		<script type="text/javascript">
			function chk() {
				if(frm.title.value.length == 0){
					alert("제목을 입력하세요");
					location.reload();
					return false
				}
				if(frm.ctnt.value.length == 0){
					alert("본문을 입력하세요");
					location.reload();
					return false
				}
			}
		</script>
	</div>
</body>
</html>