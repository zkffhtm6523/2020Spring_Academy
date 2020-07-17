insert into t_student (i_student,nm,age) values(10,'dddd',23);
-- 굳이 순서가 테이블의 컬럼 순서와 같을 필요가 없다. 만약 컬럼 타입이 not null이라면 생략도 가능하다
insert into t_student values (10,'유영혁',27);
-- 테이블명 뒤에 컬럼명이 생략가능하나 실수 유발하는것..
-- insert into t_student values (10,'유영혁',27),(11,'유영혁',27),(12,'유영혁',27); DB에서는 가능하나
-- Java에서 쿼리문을 날릴 때는 FM대로 하고 편법쓰지 말자..

-- 테이블명 뒤에 컬럼명이 생략가능하나 실수 유발하는것..
INSERT INTO t_student (i_student,nm,age) select 15,first_name,employee_id from employees
where employee_id = 100;

INSERT INTO t_student (i_student, nm, age) select 13, nm, age from t_student where i_student = 12;

INSERT INTO t_student (i_student, nm, age) SELECT 12, '사공수기',28 from dual;

insert into t_student (i_student, nm, age) select 17, first_name, employee_id from employees
where employee_id = 101;

delete from t_student where age = 100;

select * from t_student order by i_student asc;

commit;