package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface JdbcUpdateInterface {
	int Update(PreparedStatement ps) throws SQLException;
}
