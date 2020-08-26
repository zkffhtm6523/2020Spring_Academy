package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardCmtVO;

public class BoardCmtDAO {
	public static  BoardCmtVO selCountCmt(BoardCmtVO param) {
		String sql = " select i_board, count(*) "
				+ " from t_board4_cmt "
				+ " group by i_board "
				+ " HAVING i_board = 32 ";
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				return 0;
			}
		});
		return param;
	}
	
	
	
	public static int insCmt(BoardCmtVO param) {
		String sql = " insert into t_board4_cmt "
					+ " (i_cmt, i_board, i_user, cmt) "
					+ " values"
					+ " (seq_board4_cmt.nextval, ?, ?, ?) ";
					
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
				ps.setInt(2, param.getI_user());
				ps.setNString(3, param.getCmt());
			}
		});
	}
	
	public static int updCmt(BoardCmtVO param) {
		String sql = " update t_board4_cmt "
				+ " set cmt = ?, m_dt = sysdate "
				+ " where i_board = ? and i_cmt = ? ";
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getCmt());
				ps.setInt(2, param.getI_board());
				ps.setInt(3, param.getI_cmt());
			}
		});
	}
	
	public static int delCmt(BoardCmtVO param) {
		String sql = " delete from t_board4_cmt "
					+ " where i_cmt = ? and i_board = ? ";
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_cmt());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	
	public static List<BoardCmtVO> selCmt(BoardCmtVO cmtVO) {
		List<BoardCmtVO> cmtList = new ArrayList<BoardCmtVO>();
		String sql = " select A.i_cmt, A.i_board ,A.i_user,B.nm, A.cmt, A.r_dt, A.m_dt "
				+ " from t_board4_cmt A "
				+ " inner join t_user B "
				+ " on A.i_user = b.i_user "
				+ " where A.i_board = ? "
				+ " order by i_cmt desc ";
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, cmtVO.getI_board());
				
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					BoardCmtVO cmtVO1 = new BoardCmtVO();
					int i_cmt = rs.getInt("i_cmt");
					int i_board = rs.getInt("i_board");
					int i_user = rs.getInt("i_user");
					String nm = rs.getNString("nm");
					String cmt = rs.getNString("cmt");
					String r_dt = rs.getNString("r_dt");
					String m_dt = rs.getNString("m_dt");
					
					cmtVO1.setI_cmt(i_cmt);
					cmtVO1.setI_board(i_board);
					cmtVO1.setI_user(i_user);
					cmtVO1.setNm(nm);
					cmtVO1.setCmt(cmt);
					cmtVO1.setR_dt(r_dt);
					cmtVO1.setM_dt(m_dt);
					
					cmtList.add(cmtVO1);
				}
				return 1;
			}
		});
		return cmtList;
	}
}
