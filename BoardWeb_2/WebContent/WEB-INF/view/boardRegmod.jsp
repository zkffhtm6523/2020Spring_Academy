<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${data == null ? '글등록' : '글수정'}</title>
<style type="text/css">
.container {
	width: 500px;
	margin: 0 auto;
	text-align: center;
	
}
.before
table, th, td {
	border: 1px solid black;
	text-align : center;
}

table {
	border-collapse: collapse;
	margin: 0 auto;
	
}

th, td {
	padding: 10px
	
}

.col2 {
	width: 200px;
}

.title2 {
	background-color: lightgray;
}
</style>
</head>
<body>
	<div>${data == null ? '글등록' : '글수정'}</div>
	<div class="before"><a href="/boardList">이전으로 가기</a></div>
	<div class="err">${msg}</div>
	<form id="frm" action="/${data == null ? 'boardWrite' : 'boardMod' }" method="post" onsubmit="return chk()">
		<!-- EL식은 value 값에 아무 것도 없다면 ""로 찍힌다 -->
		<!-- hidden으로 숨겨놔서 안 보이지만, 값은 넘어간다. -->
		<input type="hidden" name="i_board" value="${data.i_board }">
		<div><label for = "title">제목 : <input type="text" name="title" id="title" value="${data.title }"></label></div>
		<div><label for = "ctnt">내용: <textarea name="ctnt" id="ctnt">${data.ctnt }</textarea></label></div>
		<div><label for = "i_student">작성자 : <input type="text" name="i_student" id="i_student" value="${data.i_student }" ${data == null ? '' : 'readonly' }></label></div>
		<div><input type="submit" value="${data == null ? '글쓰기' : '글수정' }"></div>
	</form>
	<script>
		function eleValid(ele,nm){
			if(ele.value.length == 0){
				alert(nm+'을(를) 입력해주세요.')
				ele.focus()
				return true
			}
		}
		function chk(){
			if(eleValid(frm.title,'제목')){
				return false
			}else if(eleValid(frm.ctnt,'내용')){
				return false
			}else if(eleValid(frm.i_student,'작성자')){
				return false
			}
		}
	</script>
</body>
</html>