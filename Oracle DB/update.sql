update t_student set nm = ' 김도빈', age = age + 1 where i_student = 2;
--0개 행이 업데이트 되었습니다. where절이 잘못 되었다고 해서 error가 발생되진 않음
--pk값이 수정
update t_student set nm = ' 김도빈', age = age + 1 where i_student = 21;
--
update t_student
set nm = (select nm from employees where employee_id = 100)
, age = (select employees from employees  where employee_id = 100);
-- 스칼라값(단일 값)
select * from t_student;
rollback;
commit;
