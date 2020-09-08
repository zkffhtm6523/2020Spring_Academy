package com.koreait.matZip.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.koreait.matZip.db.JdbcTemplate;
import com.koreait.matZip.db.JdbcUpdateInterface;
import com.koreait.matZip.vo.UserVO;

public class UserDAO {
	public int join(UserVO param) {
		int result = 0;
		
		String sql = " INSERT INTO t_user "
					+" (user_id, user_pw, salt, nm) "
					+" VALUES "
					+" (?, ?, ?, ?) ";
		
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getUser_id());
				ps.setNString(2, param.getUser_pw());
				ps.setNString(3, param.getSalt());
				ps.setNString(4, param.getNm());
			}
		});
				
		return result;
	}
}
