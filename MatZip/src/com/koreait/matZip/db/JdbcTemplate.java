package com.koreait.matZip.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTemplate {
	
	//객체 생성 막는 방법, private를 붙이거나 abstract를 붙인다.
	// 추상클래스 : 생각보다 안 씀, 추상메소드를 가지고 있으면 추상클래스, 없어도 abstract 작성하면 추상클래서, 객체화가 안되서 막는 방법으로 사용하기도 한다.
	// 인터페이스 : 자주 사용, 인터페이스는 무조건 추상메소드만 가지고 있음, public abstract 두 키워드가 생략되어 있다
	//insert, update, delete에 쓸 친구
	//콜백함수 사용하려고 인터페이스 받아옴				//이 부분은 레퍼런스 타입의 jdbc 주소값을 받아온다.
	public static int executeUpdate(String sql, JdbcUpdateInterface jdbc) {
												//선언부만 있지 구현이 안 되어 있어서 객체화 못함. 
		
		//자바스크립트는 함수만 보낼 수 있는데, 자바는 안 되서 객체를 보내야함
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DbManager.getCon();
			ps = con.prepareStatement(sql);
			//콜백함수 사용할 때 jdbcupdateInterface의 Update 리턴값이 매개변수로 저장된다
			//리턴된 주소값이 jdbc에 저장되기 때문에 주소값의 update도 리턴값이 저장되어 있다.
			jdbc.update(ps);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbManager.closeCon(con, ps);
		}
		
		return result;
	}

	public static int executeQuery(String sql, JdbcSelectInterface jdbc) {
		int result  = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DbManager.getCon();
			ps = con.prepareStatement(sql);
			
			jdbc.prepared(ps);
			
			rs = ps.executeQuery();
			jdbc.executeQuery(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbManager.closeCon(con, ps, rs);
		}
		return result;
	}
	
}
