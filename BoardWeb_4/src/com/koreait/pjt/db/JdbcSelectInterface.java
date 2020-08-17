package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface JdbcSelectInterface {
	int select(PreparedStatement ps) throws SQLException;
}
