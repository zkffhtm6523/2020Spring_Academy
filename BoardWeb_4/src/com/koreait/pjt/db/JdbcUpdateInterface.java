package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface JdbcUpdateInterface {
	//메소드에 선언만 되어 있음. 객체화 할 수 없다.
	void update(PreparedStatement ps) throws SQLException;
	//public abstract 두 키워드가 생략되어 있다
}
