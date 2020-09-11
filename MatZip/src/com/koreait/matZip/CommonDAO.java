package com.koreait.matZip;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.matZip.db.JdbcSelectInterface;
import com.koreait.matZip.db.JdbcTemplate;
import com.koreait.matZip.vo.CodeDomain;

public class CommonDAO {
	public static List<CodeDomain> selCodeList(final int i_m){
		List<CodeDomain> list = new ArrayList<CodeDomain>();
		
		String sql = " SELECT i_m, cd, val FROM c_code_d "
					+" WHERE i_m = ? ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_m);
			}
			
			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				while (rs.next()) {
					CodeDomain cd = new CodeDomain();
					cd.setCd(rs.getInt("cd"));
					cd.setVal(rs.getString("val"));
					cd.setI_m(rs.getInt("i_m"));
					list.add(cd);
				}
			}
		});
		
		return list;
	}
}
