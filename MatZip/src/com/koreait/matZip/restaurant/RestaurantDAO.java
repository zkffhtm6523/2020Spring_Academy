package com.koreait.matZip.restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.matZip.db.JdbcSelectInterface;
import com.koreait.matZip.db.JdbcTemplate;
import com.koreait.matZip.db.JdbcUpdateInterface;
import com.koreait.matZip.vo.RestaurantDomain;
import com.koreait.matZip.vo.RestaurantVO;

public class RestaurantDAO {
	public int insRestaurant(RestaurantVO param) {
		int result = 1;
		
		String sql = " INSERT INTO t_restaurant "
					+" (nm, addr, lat, lng, cd_category, i_user, r_dt, m_dt) "
					+" VALUES "
					+" (?, ?, ?, ?, ?, ?, now(), now()) ";
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setString(1, param.getNm());
				ps.setString(2, param.getAddr());
				ps.setDouble(3, param.getLat());
				ps.setDouble(4, param.getLng());
				ps.setInt(5, param.getCd_category());
				ps.setInt(6, param.getI_user());
			}
		});
		return result;
	}
	public List<RestaurantDomain> selRestList(){
		List<RestaurantDomain> list = new ArrayList<RestaurantDomain>();
		
		String sql = " SELECT i_rest, nm, lat, lng FROM t_restaurant ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			// ?가 없어서 비워둔다
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {}
			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				while (rs.next()) {
					RestaurantDomain vo = new RestaurantDomain();
					vo.setI_rest(rs.getInt("i_rest"));
					vo.setNm(rs.getNString("nm"));
					vo.setLat(rs.getDouble("lat"));
					vo.setLng(rs.getDouble("lng"));
					list.add(vo);
				}
			}
		});
		return list;
	}
}
