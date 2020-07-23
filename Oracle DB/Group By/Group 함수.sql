-- trunc(소수점 자리수 지정 가능) /올림 : ceil, floor, round /스칼라 값
-- 그룹함수에서 group by를 안 했을 시 한 개의 값이 나옴, 전체 중 하나의 값을 반환
SELECT max(salary), min(salary), trunc(avg(salary),2),count(*),sum(salary) --(조회할 컬럼(열))
from employees;  --테이블명
--where commission_pct is null; -- commission_pct이 null의 값을 가지고 있는 데이터

-- 그룹함수에서 group by는 전체 값 중 남, 여로 나눈 연봉, 평균을 보고 싶다.. 
-- having은 group by에 where처럼 조건을 걸어주고 싶을 때
select job_id, manager_id, max(salary), min(salary), trunc(avg(salary),2), count(*)
from employees
group by job_id, manager_id
having count(*) >= 5 
order by job_id, manager_id;
-- group by : job_id별로 합계 및 평균...

-- job_id를 먼저 오름차순, 같을 시 manager_id 오름차순 등. 제일 마지막에 order by

commit;