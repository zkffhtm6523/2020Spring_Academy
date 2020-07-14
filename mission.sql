/*employees 테이블에서 전화번호 (423이 포함된 사람들 모두 나오도록)*/
select * from employees where PHONE_NUMBER like '%.423.%' order by employee_id asc;

