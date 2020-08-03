<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%	
	String msg = "";
	//boardWriteProc에서 에러 터졌을 때 받아옴, 맨 처음 화면에서 아무것도 없을 때 null이 담겨있음
	String err = request.getParameter("err");
	if(err != null){
		switch(err){
		case "10": msg="등록할 수 없습니다."; break;
		case "20": msg="DB 에러 발생"; break;
		}
	}
%>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<style>
	#msg{color: red;}
</style>
<body>
	<div id="msg"> <%=msg %></div>
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
		 <div><a href="/jsp/boardList.jsp">이전으로 가기</a></div>
		<form id="frm" action="/jsp/boardWriteProc.jsp" method="post" onsubmit="return chk()">
			<div><label for = "title">제목 : <input type="text" name="title" id="title"></label></div>
			<div><label for = "ctnt">내용: <textarea name="ctnt" id="ctnt"></textarea></label></div>
			<div><label for = "i_student">작성자 : <input type="text" name="i_student" id="i_student"></label></div>
			<div><input type="submit" value="글등록"></div>
		</form>
	</div>
	<script>
	//on으로 시작하는 것은 이벤트
	//onsubmit도 콜백함수. return false하면 실행이 안됨. false를 하면 날아감
	//늦게 실행하려고 아래쪽에 함수, 위에 적으면 추가 함수 적어줘야 한다. 함수만 적으려면 위아래 상관없음.
	//css를 맨 밑에 적어도 되는데 번쩍임 현상이 일어난다.
		function eleValid(ele,nm){
			if(ele.value.length == 0){
				alert(nm+'을(를) 입력해주세요.')
				ele.focus()
				return true
			}	
		}	
		
	
		function chk() {
		//frm의 값을 적을 수 있는 자식들
			console.log(`title : \${frm.title.value}`) //jsp에 쓰는 $하고 값이 비슷해서 \를 써야됨
			console.log("title"+frm.title.value)
			if(eleValid(frm.title,'제목')){
				return false
			}else if(eleValid(frm.ctnt,'내용')){
				return false
			}else if(eleValid(frm.i_student,'작성자')){
				return false
			}
			
			
			/*
			if(frm.title.value == ''){
				alert("제목을 입력해주세요.")
				frm.title.focus()
				return false
			}else if(frm.ctnt.value.length == 0){
				alert("내용을 입력해주세요.")
				frm.title.focus()
				return false
			}
			*/
		}
	</script>
</body>
</html>