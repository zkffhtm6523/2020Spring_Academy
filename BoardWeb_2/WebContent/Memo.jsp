<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

0. Tomcat 서버 UTF-8 설정 필요(Get방식)
1. XML 설정(Post방식) : 전에꺼 가져오기
2. (web-inf) 폴더에 view 만듬
3. src->package폴더 생성->똑같은 파일명을 만들 수 있으니 패키지명으로 구분, .이 2개 이상 있어야됨
 : 회사 도메인 기준으로, com.naver.프로젝트명
 : 앱 등록할 때 패키지명이 매우 중요하다./패키지 중복 검사->플레이스토어의 한 곳 들어가서 쿼리스트링에 패키지명을 바꿔보고 안나와야됨
 : 패키지명에 대문자 삽입 금지
4. Servlet 페이지 만듬(패키지 안에)
5. tomcat에 add and remove로 작업할 프로젝트 추가
6. Server->Module에 Path Edit->Path 수정
7. 서버 스타트 : (정보: 서버가 [486] 밀리초 내에 시작되었습니다.)가 나오면 정상적



B

</body>
</html>