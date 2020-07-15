/*employees 테이블에서 전화번호 (423이 포함된 사람들 모두 나오도록)*/
select lower(first_name) from employees where PHONE_NUMBER like '%.423.%' order by employee_id asc;

/*    A은행과               B은행
고객    금액                  금액
박도흠 100000  70000       10000   40000
        update문             update문
트랜잭션 : 둘 다 에러가 안 터졌을 때만 commit
두 번의 업데이트가 중앙은행 기관 입장에서 하나의 작업
중앙은행 기관에서 A,B 둘다 업데이트 했을 때 성공했을 때 1 리턴
중앙은행에서 허가하면 실행

게시판 글을 삭제하기 위해서 댓글들을 삭제하고 나서 
게시판 글을 삭제하는 것. 이상 있을 시 rollback
*/

insert into t_student (i_student,nm,age) values(10,'dddd',23);

select * from t_student order by i_student desc;


