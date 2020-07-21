delete from t_student where i_student in(4,5,6,7);
delete from t_student where i_student between 4 and 7;
delete from t_student where i_student >= 4 and i_student <= 7;

DELETE FROM t_student WHERE i_student=10;

select * from t_student;
rollback;
commit;