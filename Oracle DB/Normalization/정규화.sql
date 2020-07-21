/*                                                              1정규화 수행, 취미가 없는 사람이 있을 수도 있음.
                                                                컬럼이 아닌 튜플로 관리.. 그렇다고 같은 튜플에 취미만 다르게 하면 안됨.
pk(번호)  이름   나이  전화번호               주소            취미    취미2   취미3
1       수기     20   010-2222-3333       대구시 어쩌고     수영     게임   음악감상
2       혜선     21   010-333-5555        서울시 어쩌고     독서

제 1정규화 : 한 컬럼에 원소값만 들어가야됨. 취미 : 수영,독서... 이렇게 안됨

사람      취미
병진      영화감상
          산책
    1대 다
    
다대다 관계(3테이블 이상)
테이블1 : 사람
테이블2 : 취미
테이블3 : 사람-취미

사람 취미(중복이 되긴 하는데, 문자열이 중복되지 않음)
사람번호    취미번호
1           2
1           4
2           1
3           5
3           3

외래키는 제약사항 : 취미가 3번까지 있는데, 사람취미 테이블의 취미 컬럼에 4번을 못 만든다.

보기 불편해서 PK를 기준으로 키맵핑 및 조인을 해서 select문으로 보기 편하게 만들어줘야함
무결성(잘못된값 X, 중복되지 않고, 관리하기 편하고)..... \
정규화를 많이 한다고 좋은 게 아님 : 테이블 간 조인 시 소스 많이 잡아먹음

반정규화 : 한 테이블에 왠만하면 때려박음. 정규화와 반정규화 조율이 중요

수정시 정규화가 편함..


구조를 짤 때 ERD라는 툴이 있음.. 자바는 인스턴스(객체), DB는 entity

휴대폰번호는 중복되지 않으니 PK할 수 있지만 문자열을 하는게 비효율적
*/

create table t_hobby(
    i_hobby number primary key,
    nm varchar2(30) not null
);

insert into t_hobby(i_hobby,nm) values(1,'독서');
insert into t_hobby values(2,'음악감상');
insert into t_hobby values(3,'영화감상');
insert into t_hobby values(4,'게임');
insert into t_hobby values(5,'산책');
insert into t_hobby values(6,'프로그래밍');
select * from t_hobby;
commit;
drop table t_student_hobby;
create table t_student_hobby(
    i_student number,
    i_hobby number,
    r_dt date default sysdate,
    -- sysdate는 현재 날짜,
    primary key(i_student,i_hobby),
    --2개 이상일 때는 primary key를 이렇게 줘야 한다.
    foreign key(i_student) references t_student(i_student) on delete cascade,
    foreign key(i_hobby) references t_hobby(i_hobby)
    -- 제약조건을 걸어줌. t_hobby의 i_hobby의 수를 이 테이블의 i_hobby가 초과하지 못함
    -- t_hobby의 i_hobby도 삭제 못함. 참조상태라서.
    -- 만약 지우게 하고 싶으면 cascade를 걸고 지움. 그러면 t_hobby의 i_hobby와 t_student_hobby의 i_hobby가 같이 삭제됨.
);
commit;
select * from t_student_hobby;
insert into t_student_hobby(i_student, i_hobby) values (1,2);
insert into t_student_hobby(i_student, i_hobby) values (1,4);
insert into t_student_hobby(i_student, i_hobby) values (2,1);
insert into t_student_hobby(i_student, i_hobby) values (3,5);
insert into t_student_hobby(i_student, i_hobby) values (3,3);
insert into t_student_hobby(i_student, i_hobby) values (3,1);
