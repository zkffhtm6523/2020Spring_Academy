select * from t_student
where i_student in ( --괄호부터 서브쿼리, 범위는 in으로 적어야 한다.
select i_student --컬럼 2개 적으면 안 된다.
from t_student
where i_student <= 4);
--null을 찾고 싶을 때 in null을 사용하자.
/**/
select * from t_student
where i_student = ( --괄호부터 서브쿼리
select i_student --컬럼 2개 적으면 안 된다.
from t_student
where i_student = 4);

select i_student, nm, age as student_age
from t_student
where (i_student <= 3 and age <= 21) or i_student = 6
order by i_student desc;

select i_student, nm, age as student_age
from t_student
where nm like '%균%'
order by age desc, nm desc;

select i_student, nm, age as student_age
from t_student
where((i_student <= 3 and age <= 21) or i_student = 5)
and nm like '%하%'
order by i_student desc;

select * from employees;
select * from t_student;
-- 중요

select 2020,first_name,employee_id from employees where employee_id = 10;
select * from employees;
select * from t_student order by i_student desc;

--lpad : 앞의 두자리를 채우고 싶을 때
select lpad(1,2,'0') from dual;

-- substr : 4번째 자리부터 2개 리턴
select first_name, substr(first_name, 4,2) from employees;

-- instr : first_name의 'a'가 발견된 자릿수
select first_name, instr(first_name,'a') from employees;
select first_name, instr(first_name,'a', -1) from employees;
-- trim ,왼쪽 공백 ltrim, 오른쪽 공백 rtrim
select trim('    a    ') from dual;
-- length
select trim('    a  '), length('   a  ') from dual;
-- concat : 문자열 합치기, mysql은 한 번에 된다. oracle은 concat을 한 번 씩 다 해줘야 함
select concat(concat('a','b'),'c') from dual;
commit;
