<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<div>
		<!--값 입력받는 곳, 처리하는 곳으로 나눈다.  -->
		<!-- 
		form은 안에 있는 내용들을 보따리로 다음 페이지로 보내줌. get으로 보내줄 때 ?쿼리 스트링은 name값으로 자동 완성
		get방식은 주소창에 보낼 수 있는 것이 한정적이다.
		label : input에 포커스 가도록
		name : key로 쓰인다. / jsp,php,asp,nodejs에서 중요, form태그에서 값 날릴 때, name만 됨
		id : 말 그대로 유일해야 함
		class : 너네는 그룹이야라고 같은 의미를 줄 때/같은 css를 주고 싶을 때, 여러 개  클래스를 줄 때 
				class = "bb jj oooo" -> 클래스 이름 3개임
		
		선택자 3개 : 태그 선택자, 아이디선택자, 클래스 선택자				
		flatuicolors.com
		속성도 만들 수 있다.
		 -->
		<form action="/jsp/boardWriteProc.jsp" method="post">
			<div><label for = "title">제목 : <input type="text" name="title" id="title"></label></div>
			<div><label for = "ctnt">내용: <textarea name="ctnt" id="ctnt"></textarea></label></div>
			<div><label for = "i_student">작성자 : <input type="text" name="i_student" id="i_student"></label></div>
			<div><input type="submit" value="글등록"></div>
		</form>
	</div>
</body>
</html>