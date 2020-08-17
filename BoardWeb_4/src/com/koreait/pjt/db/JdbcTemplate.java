package com.koreait.pjt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JdbcTemplate<T> {
	
	private static JdbcTemplate<?> instance = null;
	
	private JdbcTemplate(){}
	
	public static JdbcTemplate<?> getInstance(){
		if(instance == null) {
			instance = new JdbcTemplate();
		}
		return instance;
	}
	
	
	//insert, update, delete에 쓸 친구
	//콜백함수 사용하려고 인터페이스 받아옴
	public static int executeUpdate(String sql, JdbcUpdateInterface jdbc) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			//콜백함수 사용할 때 jdbcupdateInterface의 Update 리턴값이 매개변수로 저장된다
			//리턴된 주소값이 jdbc에 저장되기 때문에 주소값의 update도 리턴값이 저장되어 있다.
			result = jdbc.Update(ps);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeCon(con, ps);
		}
		
		return result;
	}

	public static void executeQuery(String sql, JdbcUpdateInterface jdbc) {
		
	}
	
}
