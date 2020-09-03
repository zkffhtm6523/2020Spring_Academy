package com.koreait.pjt.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.koreait.pjt.vo.UserLoginHistoryVO;
import com.koreait.pjt.vo.UserVO;

//콜백함수 부르는 방법, AOP, 관점지향 프로그래밍
public class UserDAO {
	public static int insUserLoginHistory(UserLoginHistoryVO ulhVO) {
		String sql = " INSERT INTO t_user_loginhistory "
				+ " (i_history, i_user, ip_addr, os, browser, r_dt) "
				+ " VALUES "
				+ " (seq_userloginhistory.nextval, ?, ?, ?, ?, sysdate) ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, ulhVO.getI_user());
				ps.setNString(2, ulhVO.getIp_addr());
				ps.setNString(3, ulhVO.getOs());
				ps.setNString(4, ulhVO.getBrowser());
			}
		});
	}
	
	public static int insUser(UserVO param) {
		
		String sql = " INSERT INTO t_user (i_user, user_id, user_pw, nm, email) "
				+ " VALUES "
				+ " (seq_user.nextval, ?, ?, ?, ?) ";
		
				//리턴값을 jdbctemplate의 executeupdate 메소드를 사용한다.
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
											//객체화가 아니다.. 익명클래스다
			//executeUpdate메소드 안에 JdbcUpdateInterface 메소드
			@Override
			public void update(PreparedStatement ps) throws SQLException  {
				ps.setNString(1, param.getUser_id());
				ps.setNString(2, param.getUser_pw());
				ps.setNString(3, param.getNm());
				ps.setNString(4, param.getEmail());
			}
		});
	}
	//0 : 에러 발생, 1 : 로그인 성공, 2: 비밀번호 틀림, 3: 아이디 없음
	public static int selUser(UserVO param) {
		String sql = " select i_user, user_pw, nm"
				+ " from t_user "
				+ " where user_id = ? ";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getUser_id());
			}
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()){
					String dbPw = rs.getNString("user_pw");
					//입력한 비밀번호와 DB 비밀번호가 같다면
					if(dbPw.equals(param.getUser_pw())) {
						//로그인 성공될 때 개인 정보 가져오기
						int i_user = rs.getInt("i_user");
						String nm = rs.getNString("nm");
						param.setUser_pw(null);
						param.setI_user(i_user);
						param.setNm(nm);
						return 1;
					//비밀번호가 틀릴 때
					}else {
						return 2;
					}
					//아이디 없음
				}else {
					return 3;
				}
			}
			
		});
	}
	public static UserVO selUser(int i_user) {
		String sql = " select user_id, nm, profile_img, email, r_dt "
					+ " from t_user where i_user = ? ";
		UserVO result = new UserVO();
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_user);
			}
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					result.setUser_id(rs.getNString("user_id"));
					result.setNm(rs.getNString("nm"));
					result.setProfile_img(rs.getNString("profile_img"));
					result.setEmail(rs.getNString("email"));
					result.setR_dt(rs.getNString("r_dt"));
				}
				return 1;
			}
		});
		return result;
	}
	public static int updUser(UserVO param) {
		StringBuilder sb = new StringBuilder(" UPDATE t_user SET m_dt = sysdate");
		
		if(param.getUser_pw() != null) {
			sb.append(" , user_pw = '");
			sb.append(param.getUser_pw());
			sb.append("' ");
		}
		if(param.getNm() != null) {
			sb.append(" , nm = '");
			sb.append(param.getNm());
			sb.append("' ");
		}
		if(param.getEmail() != null) {
			sb.append(" , email = '");
			sb.append(param.getEmail());
			sb.append("' ");
		}
		if(param.getProfile_img() != null) {
			sb.append(" , profile_img = '");
			sb.append(param.getProfile_img());
			sb.append("' ");
		}
		sb.append(" where i_user = ");
		sb.append(param.getI_user());
		
		System.out.println("sb : " + sb.toString());
		
		return JdbcTemplate.executeUpdate(sb.toString(), new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
			}
		});
	}
	
}
