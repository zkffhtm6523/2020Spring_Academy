SELECT
salary,
CASE WHEN SALARY >= 10000 THEN SALARY * 2 ELSE SALARY END as calcSalary,
1
FROM employees;

-- DECODE 
-- CASE
-- CASE WHEN : 숫자 비교가 용이