create table t_board_like(
    i_board number,
    i_student number,
    primary key(i_board, i_student),
    foreign key(i_board) references t_board(i_board),
    foreign key(i_student) references t_student(i_student)
);

commit;
insert into t_board_like(i_board,i_student) values (1,1);
insert into t_board_like(i_board,i_student) values (1,2);
insert into t_board_like(i_board,i_student) values (1,3);
insert into t_board_like(i_board,i_student) values (2,1);

select * from t_board_like;

select i_board, count(i_board) as like_cnt
from t_board_like
GROUP by i_board;
-- select의 결과값+테이블로 join을 할 수도 있음.

select A.*,c.nm, B.*, B.i_board,nvl(B.like_cnt,0)  as like_cnt
from t_board A
left join(
    select i_board, count(i_board) as like_cnt
    from t_board_like
    group by i_board
    --서브쿼리임..
)B
on a.i_board = b.i_board
left join t_student C --t_student가 우측에 있음
on a.i_student = c.i_student
order by nvl(B.like_cnt,0) desc;
commit;
-- 언제 left 조인인지, 언제 inner 조인인지

-- 반정규화 : like_cnt 테이블이 있는데도, t_board에 like_cnt 칼럼을 새로 만듬. 그리고 좋아요 할 때 마다 t_board의 like_cnt에 값 추가
-- 정규화도 되지만, 값도 중복됨. 하지만 select 사용 빈도가 훨씬 많으므로 데이터 처리가 빠르다.