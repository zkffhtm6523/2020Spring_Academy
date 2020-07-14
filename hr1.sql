CREATE TABLE t_student(
    i_student number, --학생 번호
    nm VARCHAR2(15) not null, -- 이름 null이 안 들어가게
    age number(3) not null, -- 나이 null이 안 들어가게
    primary key(i_student) -- 학생 번호 : primary key
);
INSERT INTO t_student (i_student,nm,age) values (1,'권하균',20);

INSERT INTO t_student (i_student,nm,age) values (2,'김도빈',21);

INSERT INTO t_student (i_student,nm,age) values (3,'김수인',22);

INSERT INTO t_student (i_student,nm,age) values (4,'김시운',23);

INSERT INTO t_student (i_student,nm,age) values (5,'김은정',24);

INSERT INTO t_student (i_student,nm,age) values (6,'김은정',25);

INSERT INTO t_student (i_student,nm,age) values (7,'김은정',26);

INSERT INTO t_student (i_student,nm,age) values (8,'김은정',23);

INSERT INTO t_student (i_student,nm,age) values (9,'김은정',23);

UPDATE t_student set nm='김도빈',age=21 WHERE i_student=2;

DELETE from t_student where i_student in(1,2,3);

SELECT i_student,nm,age FROM t_student order by i_student desc; --오름차순 기본

/*별명을 줄 수 있다*/
SELECT i_student, nm , age as student_age from t_student order by i_student desc, age, nm;
-- 이번 테이블 보여줄 때 age -> student_age로 보여준다
-- orderby는 항상 맨 끝, desc 뒤에 만약 값이 같다면 age를 오름차순으로
SELECT i_student, nm , age as student_age from t_student where i_student > 3 and age > 21 order by i_student;
SELECT i_student, nm , age as student_age from t_student where i_student > 3 OR age > 21 order by i_student;

select * from t_student where nm = '김은정' order by i_student desc;
select * from t_student order by i_student desc, nm asc,age asc;

update t_student set nm = '김재섭' where i_student = 8;
update t_student set nm = '김효진' where i_student = 9;

select 2 as dd, 'haha' as nm from t_student union all
select 1 as dd, 'haha' as nm from t_student;
/*값이 없는 데 일련된 값을 얻고 싶을 때*/

select * from t_student where nm like '%효%' order by age desc, nm desc;

select 1 from t_student;


