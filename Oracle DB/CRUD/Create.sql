CREATE TABLE t_student (
    i_student number,
    nm varchar2(15) not null,
    age number(3) not null,
    primary key(i_student)
);

DELETE FROM t_student;

INSERT INTO t_student
(i_student, nm, age)
VALUES
(1, '권하균', 20);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(2, '김도빈', 21);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(3, '김수인', 22);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(4, '김시운', 23);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(5, '김은정', 24);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(6, '김은정', 25);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(7, '김은정', 26);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(8, '김재섭', 23);

INSERT INTO t_student
(i_student, nm, age)
VALUES
(9, '김효진', 23);

UPDATE t_student
SET nm='김도빈'
, age=21
WHERE i_student=2;

UPDATE t_student
SET nm='김수인'
, age=age+1
WHERE i_student=3;

DELETE FROM t_student
WHERE i_student in (1,2,3);

SELECT i_student, nm, age FROM t_student;

SELECT i_student, nm as i_name, age as student_age 
FROM t_student 
WHERE (i_student <= 2 AND age <= 21) OR i_student=5
ORDER BY i_student ASC;

SELECT i_student, nm as i_name, age as student_age 
FROM t_student 
ORDER BY nm ASC, age DESC;

SELECT i_student, nm as i_name, age as student_age 
FROM t_student 
ORDER BY age DESC, nm ASC;

SELECT 1+1 as RESULT FROM dual;

SELECT i_student, nm as i_name, age as student_age 
FROM t_student
WHERE nm NOT LIKE '%진%'
ORDER BY age DESC, nm ASC;

--employees 테이블에서 전화번호가 423이 포함된 사람들 모두 나오도록
SELECT * FROM EMPLOYEES;

SELECT LOWER(FIRST_NAME) as FIRST_NAME
FROM EMPLOYEES
WHERE PHONE_NUMBER LIKE '%.423.%';

SELECT UPPER(FIRST_NAME) as FIRST_NAME
FROM EMPLOYEES
WHERE PHONE_NUMBER LIKE '%.423.%';
