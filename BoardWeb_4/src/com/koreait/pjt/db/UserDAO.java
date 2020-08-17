package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.koreait.pjt.vo.UserVO;

//콜백함수 부르는 방법, AOP, 관점지향 프로그래밍
public class UserDAO {
	public static int insUser(UserVO param) {
		
		String sql = " INSERT INTO t_user (i_user, user_id, user_pw, nm, email) "
				+ " VALUES "
				+ " (seq_user.nextval, ?, ?, ?, ?) ";
		
				//리턴값을 jdbctemplate의 executeupdate 메소드를 사용한다.
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			//executeUpdate메소드 안에 JdbcUpdateInterface 메소드
			@Override
			public int Update(PreparedStatement ps) throws SQLException  {
				ps.setNString(1, param.getUser_id());
				ps.setNString(2, param.getUser_pw());
				ps.setNString(3, param.getNm());
				ps.setNString(4, param.getEmail());
				return ps.executeUpdate();
			}
		});
	}
//	public List<UserVO> selUserList(){
//		List<UserVO> list = new ArrayList();
//		String sql = "";
//		
//		JdbcTemplate.executeQuery(sql, new JdbcUpdateInterface() {
//			
//			@Override
//			public int Update(PreparedStatement ps) throws SQLException {
//				return 0;
//			}
//		});
//		
//		return list;
//	}
}
