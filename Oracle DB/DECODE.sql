SELECT job_id as 아이디, 
decode(job_id, 'IT_PROG','프로그래머'
              , 'FI_ACCOUNT','금융업'
              , 'AD_VP','에이디 브이피',' ') as 결과값
FROM employees;
--decode(조회할 컬럼, 컬럼의 조건값이면, 조건값이 부합 시 "~~" 출력, 아니라면 "")
--NVL(~~~,"null 대체할 문자")

select decode(job_id, 'IT_PROG','프로그래머'
              , 'FI_ACCOUNT','금융업'
              , 'AD_VP','에이디 브이피'
              ,'ffff') as 결과값
-- case문과 decode을 똑같이 쓰기
,case job_id when 'IT_PROG' then '프로그래머'
             when 'FI_ACCOUNT' then '금융업'
             when 'AD_VP' THEN '에이디 브이피'
             ELSE 'ffff' END
FROM employees;