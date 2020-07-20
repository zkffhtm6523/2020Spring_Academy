-- mysql 문법
select cast('22-oct-1998' as timestamp with local time zone) from dual;

select to_char(to_date('20161010 11:11:11', 'YYYYMMDD HH24:MI:SS'), 'mm-dd/yyyy') from dual;

select to_char(sysdate,'yyyy-mm-dd') from dual;

--oracle
select sysdate from dual;

--mysql
select now from dual;
-- sysdate : 현재 시간 나타내기