
--w3schools.com

--distinct  : 중복된 값 제거
--lpad : lpad(column name, 표시할 데이터 자릿수, 원본 데이터 자릿수 초과 시 빈 공백 메울 데이터)
select distinct job_id,manager_id from employees;

select lpad(salary,8,0) from employees;

select job_id,manager_id from employees;

select phone_number from employees;

select job_id, max(salary), min(salary),avg(salary), count(*), sum(salary),sum(salary)/count(salary)
from employees
group by job_id;
--job_id별로 합계 및 최대값 등...

select distinct lpad(phone_number,13,'1') from employees;

select lpad(phone_number,3), max(salary), min(salary)
from employees
group by lpad(phone_number,3)
having max(salary) >= 10000;