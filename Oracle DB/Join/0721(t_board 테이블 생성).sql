create table t_board(
    i_board number primary key,
    title varchar2(100) not null,
    ctnt varchar2(2000) not null,
    i_student number not null,
    foreign key(i_student) references t_student(i_student)
);
-- 커멘트 남기기
COMMENT on column t_board.i_student is '작성자';

insert into t_board(i_board, title, ctnt, i_student) values (1,'안녕','112122',3);
insert into t_board(i_board, title, ctnt, i_student) values (2,'하세요','ㅋㅋㅋㅋㅋㅋ',3);
insert into t_board(i_board, title, ctnt, i_student) values (3,'하하','ㅇㅇㅇㅇㅇ',1);
insert into t_board(i_board, title, ctnt, i_student) values (4,'좋아요','ㅇㄹㅇㄹㅋㅋㅋㅇ',2);
insert into t_board(i_board, title, ctnt, i_student) values (5,'크크','dfdfdfdfdf',3);
--insert into t_board(i_board, title, ctnt, i_student) values (5,'크크','dfdfdfdfdf',16);
--외래키로 t_student(i_student)를 설정했기 때문에 16번 학생은 만들지 못한다. 무결성 위배 

commit;
select * from t_board;
select * from t_student;

