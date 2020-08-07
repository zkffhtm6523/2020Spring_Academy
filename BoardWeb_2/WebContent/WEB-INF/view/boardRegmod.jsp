<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<style type="text/css">
.container {
	width: 500px;
	margin: 0 auto;
	text-align: center;
	
}

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
	<div><a href="/jsp/boardList.jsp">이전으로 가기</a></div>
	<div>
	</div>
	<div>
		<form id="frm" action="/boardWrite" method="post" onsubmit="return chk()">
			<div><label for = "title">제목 : <input type="text" name="title" id="title"></label></div>
			<div><label for = "ctnt">내용: <textarea name="ctnt" id="ctnt"></textarea></label></div>
			<div><label for = "i_student">작성자 : <input type="text" name="i_student" id="i_student"></label></div>
			<div><input type="submit" value="글쓰기"></div>
		</form>
	</div>
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