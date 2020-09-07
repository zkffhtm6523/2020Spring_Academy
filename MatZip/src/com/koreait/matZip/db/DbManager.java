package com.koreait.matZip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbManager {
	public static Connection getCon() throws Exception {
		String url = "jdbc:mysql://localhost:3306/matzip?serverTimezone=UTC";
		String username = "root";
		String userpassword = "koreait2020";
		String className = "com.mysql.cj.jdbc.Driver";
		
		Class.forName(className);
		Connection con = DriverManager.getConnection(url, username, userpassword);
		System.out.println("DB 접속 성공!");
		return con;
	}

	public static void closeCon(Connection con, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {try {rs.close();} catch (Exception e) {}}
		if (ps != null) {try {ps.close();} catch (Exception e) {}}
		if (con != null) {try {con.close();} catch (Exception e) {}}
	}

	public static void closeCon(Connection con, PreparedStatement ps) {
		closeCon(con, ps, null);
	}
}
