-- JOIN 1번 방법 : Inner Join(교집합)
--A..B는 별명주는 것, 1번은 모든 컬럼 불러오기
select * from t_student_hobby A
inner join t_student B
ON a.i_student = b.i_student;
-- 2번 : 그 다음 컬럼을 수정
select B.i_student, B.nm, A.i_hobby, C.* --불러올 컬럼
FROM t_student_hobby A -- 모든게 연결된 A 테이블
INNER JOIN t_student B -- 외래키 보유 B 테이블
ON A.i_student = b.i_student
INNER JOIN t_hobby C  -- 외래키 보유 C 테이블
ON A.i_hobby = c.i_hobby;

-- JOIN 2번 방법 : Left Join(합집합 : null도 같이 나옴)
select * from t_student_hobby A
inner join t_student B
ON a.i_student = b.i_student;
-- 2번 : 그 다음 컬럼을 수정
select B.i_student, B.nm, A.i_hobby, C.* --불러올 컬럼
FROM t_student_hobby A --본 테이블
left JOIN t_student B --가져와서 조인할 테이블 1
ON A.i_student = b.i_student  -- 결합 1
left JOIN t_hobby C -- 가져와서 조인할 테이블 2
ON A.i_hobby = c.i_hobby; -- 결합 2
-- on : t_student와 t_student_hobby의 테이블이 칼럼명이 같을 때

--게시판만 뿌릴때는 목록 전체를 칼럼으로 설정 후  다 나와서 문제 없음, 조회수도 게시판 테이블에 넣음.
--좋아요 누른 사람의 정보까지 하려면 좋아요 테이블을 만들어야함. 테이블을 따로 안 빼면 3정규화에 안 맞음

--select i_board, count(*) from 좋아요 group by i_board;

