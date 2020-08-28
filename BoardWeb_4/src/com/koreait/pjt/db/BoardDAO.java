package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

public class BoardDAO {

	public static ArrayList<BoardVO> likeListDetailBoardList(BoardVO param) {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		String sql = " select b.nm, A.i_user "
				+ " from t_board4_like A "
				+ " inner join t_user B "
				+ " on A.i_user = B.i_user "
				+ " where a.i_board = ? ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while (rs.next()) {
					BoardVO vo = new BoardVO();
					String nm = rs.getNString("nm");
					vo.setNm(nm);
					list.add(vo);
				}
				return 1;
			}
		});
		return list;
	}
	public static void delLikeDetailBoardList(BoardVO param) {
		String sql = " delete from t_board4_like where i_user = ? and i_board = ? ";
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_user());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	
	//좋아요 없을 시 추가
	public static void insLikeDetailBoardList(BoardVO param) {
		String sql = " insert into t_board4_like (i_user, i_board) "
					+ " values "
					+ " (?, ?) ";
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_user());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	public static void hitDetailBoardList(BoardVO param) {
		String sql = " update t_board4 set hits = (select nvl(max(hits),0)+1 from t_board4 where i_board = ? ) where i_board = ? ";
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	
	public static int uptDetailBoardList(BoardVO param) {
		String sql = " update t_board4 set title = ?, ctnt = ?, m_dt = sysdate where i_board = ? ";
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getTitle());
				ps.setNString(2, param.getCtnt());
				ps.setInt(3, param.getI_board());
			}
		});
	}
	
	public static BoardVO likeDetailBoardList(BoardVO param) {
		String sql = " select DECODE(C.i_user, null, 0, 1) as yn_like, "
					+ " ( SELECT count(*) FROM t_board4_like WHERE i_board = ? ) as likeCount "
					+ " from t_board4 A "
					+ " inner join t_user B "
					+ " on A.i_user = B.i_user "
					+ " left join t_board4_like C "
					+ " on A.i_board = C.i_board "
					+ " AND C.i_user = ? "
					+ " where A.i_board = ? ";
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
				ps.setInt(2, param.getLoginUser());
				ps.setInt(3, param.getI_board());
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					int yn_like = rs.getInt("yn_like");
					int likeCount = rs.getInt("likecount");
					//내가 이 글에 좋아요를 했으면 1, 안 했으면 0
					param.setYn_like(yn_like);
					param.setLikeCount(likeCount);
					return 1;
				}else {
					return 2;
				}
			}
		});
		
		return param;
	}
	
	public static BoardVO selDetailBoardList(BoardVO param) {
	String sql = " select t_board4.title, TO_CHAR(t_board4.r_dt, 'YYYY/MM/DD HH24:MI') as r_dt , t_board4.hits, t_board4.ctnt, t_user.nm, t_user.i_user "
				+" from t_board4 "
				+" inner join t_user "
				+" on t_board4.i_user = t_user.i_user "
				+" where t_board4.i_board = "+param.getI_board();
	
	JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
		
		@Override
		public void prepared(PreparedStatement ps) throws SQLException {}
		
		@Override
		public int executeQuery(ResultSet rs) throws SQLException {
			if(rs.next()) {
				String title = rs.getNString("title");
				String nm = rs.getNString("nm");
				String r_dt = rs.getNString("r_dt");
				String ctnt = rs.getNString("ctnt");
				int hits = rs.getInt("hits");
				int i_user = rs.getInt("i_user");
				
				param.setTitle(title);
				param.setNm(nm);
				param.setR_dt(r_dt);
				param.setCtnt(ctnt);
				param.setHits(hits);
				param.setI_user(i_user);
			}
			return 1;
		}
	});
		return param;
	}
	public static int selPagingCnt(final BoardVO param) {
		String sql = " select ceil(count(i_board) / ?) from t_board4 "
					+ " where title like ? ";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getRecord_cnt());
				
				ps.setNString(2, param.getSearchText());
			}
			@Override
			//스칼라값 : 1행 1열만 있는 값
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		});
	}
	
	
	
	public static List<BoardVO> selBoardList(BoardVO vo) {
		List<BoardVO> list = new ArrayList<BoardVO>();
//		String sql = " select A.i_board, A.title, A.r_dt, A.i_user, A.hits, B.nm,  "
//				 + " (select count(*) as countall from t_board4_cmt group by i_board HAVING i_board = A.i_board) as countCmt "
//				+ " from t_board4 A "
//				+ " inner join t_user B "
//				+ " on A.i_user = B.i_user "
//				+ " order by a.i_board desc ";
		String sql = " select * from " 
					+ " ( "
					+ " select rownum as rnum, A.* from "
					+ " ( "
					+ " select A.i_board, A.title, A.r_dt, A.i_user, A.hits, B.nm, "
					+ " (select count(*) as countall from t_board4_cmt group by i_board HAVING i_board = A.i_board) as countCmt "
					+ " from t_board4 A "
					+ " inner join t_user B "
					+ " on A.i_user = B.i_user "
					+ " where A.title like ? "
					+ " order by a.i_board desc "
					+ " ) A "
					+ " where rownum <= ? "
					+ " )A "
					+ " where A.rnum > ? ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1, vo.getSearchText());
				ps.setInt(2, vo.getEldx());
				ps.setInt(3, vo.getSldx());
			}
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					int i_board = rs.getInt("i_board");
					String title = rs.getNString("title");
					String r_dt = rs.getNString("r_dt");
					int i_user = rs.getInt("i_user");
					int hits = rs.getInt("hits");
					String nm = rs.getNString("nm");
					int countCmt = rs.getInt("countCmt"); 
					
					BoardVO vo = new BoardVO();
					vo.setI_board(i_board);
					vo.setTitle(title);
					vo.setHits(hits);
					vo.setI_user(i_user);
					vo.setR_dt(r_dt);
					vo.setNm(nm);
					vo.setCountCmt(countCmt);
					list.add(vo);
				}
				return 1;
			}			
		});
		
		return list;
	}
	public static int insBoardList(BoardVO param) {
		String sql = " INSERT INTO t_board4 (i_board, title, ctnt, i_user) "
				+" select nvl(max(i_board),0)+1, ?, ?, ? "
				+" from t_board4 " ;
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getTitle());
				ps.setNString(2, param.getCtnt());
				ps.setInt(3, param.getI_user());
			}
		});
		
	}

	public static void delBoardList(BoardVO param) {
		String sql = " delete from t_board4 where i_board = "+param.getI_board();
		
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {}
		});
	}


}