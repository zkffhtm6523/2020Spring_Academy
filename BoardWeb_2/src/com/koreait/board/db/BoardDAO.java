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
	public static BoardVO selBoardDetail(BoardVO param){
		//param에 값을 담으면 되지 않을까?.. 담아도 되는데 대규모 프로젝트에서는 일관성이 있어야 됨.
		String sql = "SELECT title, ctnt, i_student FROM t_board WHERE i_board = ?";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BoardVO vo = null;
		try {
			ps = DbCon.getCon().prepareStatement(sql); 
			ps.setInt(1,param.getI_board());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int i_student = rs.getInt("i_student");
				String title = rs.getNString("title");
				String ctnt = rs.getNString("ctnt");
				
				vo = new BoardVO();
				vo.setI_board(param.getI_board());
				vo.setI_student(i_student);
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.closeCon(con, ps, rs);
		}
		return vo;
	}
	public static int selBoardWrite(BoardVO param){
		String sql = " insert into t_board(i_board,title,ctnt,i_student) "
				+" select nvl(max(i_board),0)+1,?,?,? "
				+" from t_board " ;
//		시퀀스(시퀸스) 사용 시
//		String sql2 = " INSERT INTO t_board "
//				+" (i_board, title, ctnt, i_student) "
//				+" VALUES "
//				+" (seq_board.nextval, ?, ?, ?) ";
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = DbCon.getCon().prepareStatement(sql);
			System.out.println("접속 성공");
			ps.setNString(1, param.getTitle());
			System.out.println("타이틀 삽입");
			ps.setNString(2, param.getCtnt());
			System.out.println("내용 삽입");
			ps.setInt(3, param.getI_student());
			System.out.println("학생번호 삽입");
			result = ps.executeUpdate();
//			나머지는 이것 사용			
//			ps.executeQuery(); : select만 이거 사용
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.closeCon(con, ps);
		}
		return result;
	}
	public static int delBoard(BoardVO param) {
		String sql = " delete from t_board where i_board = ? ";
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		try {
			ps = DbCon.getCon().prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeCon(con, ps);
		}
		return result;
	}
	public static int upBoard(BoardVO param) {
		String sql = " update t_board set title = ?, ctnt = ?, i_student = ? where i_board = ?";
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		try {
			ps = DbCon.getCon().prepareStatement(sql);
			ps.setNString(1, param.getTitle());
			ps.setNString(2, param.getCtnt());
			ps.setInt(3, param.getI_student());
			ps.setInt(4, param.getI_board());
			System.out.println("수정 등록 완료");
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.closeCon(con, ps);
		}
		
		return result;
	}
}
