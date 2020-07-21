select employee_id, nvl(commission_pct,0),commission_pct from employees;

--select nvl(컬럼명,'') as 컬럼명 from dual; 오라클
--select ifnull(컬럼명,'') mysql, cpu는 64비트라고 해도 처리 단위가 4바이트, int가 4바이트, integer은 8바이트-----

-- 자바에서 null값을 가질 수 있다는 것은 주소값을 가리킬 수 있다는 의미
-- 레코드 20개 중 2개 null값, null인 값을 특정 값으로 바꾸려고 할 때 ..
commit;