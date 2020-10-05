package kr.hkit.exam09.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.hkit.exam09.BoardVO;
import kr.hkit.exam09.Query;

class QueryTest {
	
	//시작 전에 테스트 @test가 실행되기 전 최초 1번
	@BeforeAll
	static void start() {
		Query.createTable();
	}
	@AfterAll
	static void end() {
		Query.dropTable();
	}
	//얘부터는 static을 안 붙여도 됨
	@BeforeEach
	void before() {
		//전부 지우고
		Query.boardDelete(0);
		
		//깨끗한 값을 넣는다
		BoardVO bv1 = new BoardVO();
		bv1.setBtitle("타이틀1");
		bv1.setBcontent("내용1");
		Query.boardInsert(bv1);
		
		BoardVO bv2 = new BoardVO();
		bv2.setBtitle("타이틀2");
		bv2.setBcontent("내용2");
		Query.boardInsert(bv2);
	}
	@Test
	void testA() {
		List<BoardVO> list = Query.getAllBoardList();
		//가장 많이 사용하는 것
		assertEquals(2, list.size());
		
		BoardVO vo1 = list.get(0);
		assertEquals("타이틀2", vo1.getBtitle());
		assertEquals("내용2", vo1.getBcontent());

		BoardVO vo2 = list.get(1);
		assertEquals("타이틀1", vo2.getBtitle());
		assertEquals("내용1", vo2.getBcontent());
	}
	@Test
	void testB() {
		List<BoardVO> list = Query.getAllBoardList();
		BoardVO vo1 = list.get(0);
		Query.boardDelete(vo1.getBid());
		BoardVO vo1Db = Query.getBoardDetail(vo1.getBid());
		assertEquals(0, vo1Db.getBid());
		assertNull(vo1Db.getBtitle());
		assertNull(vo1Db.getBcontent());
		
		assertEquals(1, Query.getAllBoardList().size());
		
		BoardVO vo2 = list.get(1);
		Query.boardDelete(vo2.getBid());
		BoardVO vo2Db = Query.getBoardDetail(vo2.getBid());
		assertEquals(0, vo2Db.getBid());
		assertNull(vo2Db.getBtitle());
		assertNull(vo2Db.getBcontent());
		
		assertEquals(0, Query.getAllBoardList().size());
	}
	@Test
	void testC() {
		List<BoardVO> firstList = Query.getAllBoardList();
		List<BoardVO> newList = new ArrayList<BoardVO>();
		for (int i = 0; i < firstList.size(); i++) {
			BoardVO temp = new BoardVO();
			temp.setBid(i);
			temp.setBcontent("업데이트 내용 "+(i+1));
			temp.setBtitle("업데이트 타이틀 "+(i+1));
			Query.boardUpdate(temp);
			newList.add(temp);
			
		}
		List<BoardVO> chkList = Query.getAllBoardList();
		for (int i = 0; i < chkList.size(); i++) {
			assertNotEquals(chkList.get(i).getBtitle(), newList.get(chkList.size()-(i+1)).getBtitle());
			assertNotEquals(chkList.get(i).getBcontent(), newList.get(chkList.size()-(i+1)).getBcontent());
		}
	}
}
