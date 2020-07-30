package board.user;

import java.sql.*;

public class Conn {
	public static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static String userName = "hr";
	public static String userPassword  = "koreait2020";
	public static Connection con = null;
	/*이렇게 적는 것은 서블릿의 메소드 안에 들어가짐, 지역변수라서 private 못 붙임
	메소드 안에 메소드 구현 못함, 메소드 밖에다 내용을 적으려면 <%!로 해줘야함*/
	public Connection getCon() throws Exception{
		con = DriverManager.getConnection(url,userName,userPassword);
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("접속성공");
		return con;
}
	public void closeCon() throws Exception{
		System.out.println("접속종료");
		con.close();
	}
}
