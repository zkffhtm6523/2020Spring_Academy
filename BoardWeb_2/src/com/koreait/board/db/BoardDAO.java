package com.koreait.board.db;

import java.sql.*;
import java.util.*;
import com.koreait.board.vo.BoardVO;

public class BoardDAO {
	public static List<BoardVO> selBoardList(){
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select i_board,i_student,title,ctnt from t_board order by i_board";
		try {
			ps = DbCon.getCon().prepareStatement(sql); 
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int i_board = rs.getInt("i_board");
				int i_student = rs.getInt("i_student");
				String title = rs.getNString("title");
				String ctnt = rs.getNString("ctnt");
				
				BoardVO vo = new BoardVO();
				vo.setI_board(i_board);
				vo.setI_student(i_student);
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeCon(con, ps, rs);
		}
		return list;
	}

}
