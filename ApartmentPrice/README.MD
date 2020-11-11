# 쿼리문

CREATE TABLE location_code(
	cd INT UNSIGNED PRIMARY KEY,
	local_nm varchar(5) NOT NULL,
	external_cd CHAR(5) NOT NULL
);

INSERT INTO location_code(cd, local_nm, external_cd)
VALUES
(1, '중구', '27110'),
(2, '동구', '27140'),
(3, '서구', '27170'),
(4, '남구', '27200'),
(5, '북구', '27230'),
(6, '수성구', '27260'),
(7, '달서구', '27290'),
(8, '달성군', '27710');

CREATE TABLE apartment_info(
	i_ai INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	deal_amount INT UNSIGNED NOT NULL,
	build_year CHAR(4) NOT NULL,
	deal_year CHAR(4) NOT NULL,
	deal_month CHAR(2) NOT NULL,
	deal_day CHAR(6) NOT NULL,
	dong VARCHAR(40) NOT NULL,
	apartment_name VARCHAR(40) NOT NULL,
	area_for_exclusive_use FLOAT NOT NULL,
	jibun VARCHAR(10) NOT NULL,
	flr INT UNSIGNED NOT NULL,
	location_cd INT UNSIGNED NOT NULL
);
	
## 습득 필요 정보
cors : https://www.google.com/search?q=cors&oq=cors&aqs=chrome.0.0i433l2j0l6.1585j0j7&sourceid=chrome&ie=UTF-8 <br>
내가 보낸 html이 아니면 막겠다는 의미_ajax는 막는데 서버끼리 통신은 cors가 안 걸림 <br>

정부 공공 데이터 : https://www.data.go.kr/data/15057511/openapi.do <br>
