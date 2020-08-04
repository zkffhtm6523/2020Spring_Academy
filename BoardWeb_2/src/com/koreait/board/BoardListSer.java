package com.koreait.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//바꿔도 됨../로 바꾸명 localhost만 해도 접속해짐
@WebServlet("/boardList")
//HttpServlet 상속받아서 HttpServlet의 기능을 사용할 수 있음.
public class BoardListSer extends HttpServlet {
	//멤버 필드
	private static final long serialVersionUID = 1L;
    
	//기본 생성자 : 변수를 아무것도 받지 않는 것(클래스명과 동일, 리턴타입이 없다)
    public BoardListSer() {
        super();
        //직속 부모의 기본생성자 호출
        //super. super() / this. this()
        //메모리는 Object부터 밑으로 호출해서 Heap 메모리에 쌓이고, 생성자는 밑에서부터 위로 실행
    }
    //@Override가 생략되어 있어서, 적어주는 것이 좋다.											이러한 예외처리가 발생할 수 있으니 넘긴다
    //get방식 : 주소 직접 넣는 것 ,post방식 : 언제 날릴 수 있는가.form태그
    //차이점 -> get방식은 보내는 값들이 주소값에 쿼리스트링으로 적히고 빠르다, 주소 길이의 한계가 있음/post방식은 캡슐화해서 보낼 수 있고 많은 양을 보낼 수 있다.속도는 상대적 느리다.
    //get : 화면 띄울 때 post : action(login) 페이지
    //jsp : 뷰 담당    servlet : 로직 담당
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    						//요청											응답

    	//		삭제하고 작성
    	//    	response.getWriter().append("Served at: ").append(request.getContextPath());
    	//				        		고객에게 응답으로 감.
    	//http://localhost:8089/BoardListSer로 접속하면 served at이 화면에 나온다

    	//response.sendRedirect(); : 이동
    	//주소 이동을 하고 주소값을 바꿈 , 새로고침 하는 효과가 있어서 살려서 보내지 못한다.새롭게 발행되는거 같음ㄴ
    	//-request.dispatcher : 이동
    	//주소 이동을 하고 주소값을 바꾸지 않음, 파라미터의 request, response를 jsp파일로 살려서 보낸다
    	String strI_board = request.getParameter("i_board");
    	System.out.println("Servlet i_board : "+strI_board);
    	RequestDispatcher rd =  request.getRequestDispatcher("/WEB-INF/view/boardList.jsp?i_board=");
    	//값을 jsp파일로 그대로 넘겨줄 수 있음
    	rd.forward(request, response);
    	//http://localhost:8089/boardList?i_board=3이렇게 주소값에 쳐넣으면 웹페이지에 나옴
	}
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	//		삭제하고 작성
    	//		doGet(request, response);
	}

}
