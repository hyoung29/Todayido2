-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

CREATE TABLE Owner
(
    `store_name` VARCHAR(20) NOT NULL COMMENT '가게 이름',
    `business_num` VARCHAR(20) NOT NULL COMMENT '사업자 번호',
    `owner_name` VARCHAR(10) NOT NULL COMMENT '사업주 이름',
    `owner_pwd` VARCHAR(200) NOT NULL COMMENT '사업장 비밀번호',
    CONSTRAINT PK_Owner PRIMARY KEY (business_num)
);


-- Store Table Create SQL
-- 테이블 생성 SQL - Store
CREATE TABLE Store
(
	`search`             TEXT           NULL        COMMENT '검색 키워드',
    `store_num`          BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '가게 식별번호. 가게', 
    `store_name`         VARCHAR(20)    NOT NULL    COMMENT '가게 이름', 
    `store_telephone`    TEXT           NOT NULL    COMMENT '가게 전화번호', 
    `store_adress`       VARCHAR(45)    NOT NULL    COMMENT '가게 주소', 
    `store_category_id`  INT            NOT NULL    COMMENT '가게 카테고리 식별번호', 
    `average_rating`     FLOAT          NOT NULL    DEFAULT 0 COMMENT '평균 별점. 5점 만점/ 정렬기준에 필요', 
    `store_description`  VARCHAR(200)   NOT NULL    COMMENT '가게 설명', 
    `open_time`          VARCHAR(30)    NOT NULL    COMMENT '운영시간. ex)매일-오전10:00~밤12:00', 
    `closed_day`         VARCHAR(45)    NOT NULL    COMMENT '휴무일. ex) 연중무휴', 
    `owner_name`         VARCHAR(10)    NOT NULL    COMMENT '대표자명', 
    `business_num`       VARCHAR(20)    NOT NULL    COMMENT '사업자등록번호', 
    `created_at`          TIMESTAMP     NOT NULL    DEFAULT current_timestamp COMMENT '생성일시', 
    `updated_at`          TIMESTAMP     NULL        DEFAULT null COMMENT '수정일시',
    PRIMARY KEY (store_num),
    foreign key (business_num) references Owner (business_num) ON DELETE RESTRICT ON UPDATE RESTRICT
);
SET foreign_key_checks = 0; 

INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('미식당', '010-0943-3994', '인천광역시 미추홀구 용현동 193-5', 1, 4.4, '카레, 덮밥, 라멘, 모듬', '오전 10:00 ~ 오후 11:00', '연중무휴', '김양섭', '732-19-57644', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('고목정', '010-9291-3947', '인천광역시 미추홀구 한나루로358번길 7', 1, 4.9, '삼겹살, 목살, 갈매기살', '오전 11:00 ~ 오후 9:30', '연중무휴', '김예빈', '263-33-98919', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('꿀꿀진순대', '010-0245-0580', '서울특별시 강동구 성내동 199-17', 1, 4.4, '순대, 순대국밥, 문어순대국', '오전 10:00 ~ 오후 10:00', '연중무휴', '김은형', '828-48-23621', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('성원닭갈비', '010-7793-2058', '인천광역시 계양구 효성2동 273-79', 1, 4.1, '닭갈비, 찜닭, 볶음밥', '오전 11:00 ~ 오후 11:00', '연중무휴', '김재희', '189-23-41071', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('스타벅스', '010-7031-6724', '강남구 청담동 52-1', 2, 4.9, '아메리카노, 카페라떼, 바닐라라떼', '오전 7:00 ~ 오후 9:00', '연중무휴', '김정은', '183-59-51733', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('디저트39', '010-5462-0894', '인천 미추홀구 소성로 237', 2, 4.8, '딸기라떼, 민트초코라떼, 레드벨벳케이크', '오전 9:00 ~ 오후 11:00', '연중무휴', '김태욱', '698-58-74153', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('투썸플레이스', '010-0564-4422', '인천광역시 미추홀구 아암대로 87', 2, 4.4, '케이크, 아메리카노, 카페라떼', '오전 8:00 ~ 새벽 1:00', '연중무휴', '박예은', '165-64-32725', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('빽다방', '010-8060-5416', '인천광역시 미추홀구 도화동 숙골로 87번길24', 2, 3.9, '빽커피, 녹차빽스치노, 뱅쇼', '오전 8:00 ~ 오후 9:00', '연중무휴', '박은정', '821-06-74757',current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('오늘술집', '010-6718-8188', '인천 미추홀구 한나루로 349 1층 오늘술집', 3, 4.4, '모듬전, 맥주, 치킨', '오후 3:00 ~ 새벽 2:00', '연중무휴', '백종욱', '594-78-78562', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('크라운호프', '010-0907-7623', '인천 미추홀구 소성로 234 1층', 3, 4.5, '치킨, 맥주, 소주', '오후 5:00 ~ 새벽 1:00', '일요일 휴무', '박종일', '444-01-13459', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('마을회관포차', '010-8959-1070', '인천 연수구 독배로40번길 5 마을회관포차 옥련시장점', 3, 4.7, '삼겹살, 곱창, 소주', '오후 4:00 ~ 새벽 3:00', '연중무휴', '이재원', '166-99-36122', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('동네 술집', '010-3189-6147', '서울 강서구 가로공원로 173 1층', 3, 4.2, '삼겹살, 곱창, 소주', '오전 6:00 ~ 새벽 1:00', '일요일 휴무', '임대현', '553-30-98486', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('국립현대미술관', '010-4348-1740', '서울 종로구 삼청로 30', 4, 4.4, '전시회', '오전 10:00 ~ 오후 9:00', '연중무휴', '장재영', '886-77-99821', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('인천시립박물관', '010-1239-5366', '인천 연수구 청량로160번길 26 인천시립박물관', 4, 4.7, '박물관, 미술관', '오전 9:00 ~ 오후 6:00', '연중무휴', '장재웅', '625-07-46447', current_timestamp,null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('한강 공원', '010-5960-0918', '서울 영등포구 여의동로 330 한강사업본부 여의도안내센터', 4, 4.8, '피크닉', '24:00', '연중무휴', '장현준', '737-06-41050', current_timestamp, null);
INSERT INTO Store(store_name, store_telephone, store_adress, store_category_id, average_rating, store_description, open_time, closed_day, owner_name, business_num, created_at, updated_at)
VALUES('학익 공원', '010-2062-9479', '인천광역시 미추홀구 학익동 662', 4, 4.5, '각종 체육 시설이 있음', '24:00', '연중무휴', '허수진', '130-72-34086', current_timestamp, null);

-- Menu Table Create SQL
-- 테이블 생성 SQL - Menu
CREATE TABLE Menu
(
    `menu_num`           BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '메뉴 식별번호', 
    `store_num`          BIGINT         NOT NULL    COMMENT '가게 식별번호', 
    `menu_name`          VARCHAR(50)    NOT NULL    COMMENT '메뉴 이름', 
    `menu_price`         INT            NOT NULL    COMMENT '가격', 
    `menu_descripton`    TEXT           NOT NULL    COMMENT '메뉴설명',
    `created_at`         TIMESTAMP      NOT NULL    DEFAULT current_timestamp COMMENT '생성일시', 
    `updated_at`          TIMESTAMP     NULL        DEFAULT null COMMENT '수정일시',
    `mn_oriname`         VARCHAR(50)    NULL,
    `mn_sysname`         VARCHAR(50)    NULL,
     PRIMARY KEY (menu_num),
     foreign key (store_num) references Store (store_num) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO Menu(store_num, menu_name, menu_price, menu_descripton, created_at, updated_at)
VALUES
(1, '머쉬룸 파스타', 7000, 'Cream_고소한 양송이', current_timestamp, null),
(1, '까르보나라', 7700, 'Cream_베이컨 + 특제 매콤양념 HOT', current_timestamp, null),
(1, '알리오 올리오', 5500, 'Oill_마늘향이 가득한 갈릭 오일 파스타(약간 매콤)', current_timestamp, null),
(4, '뼈 있는 불 닭갈비', 20000, '뜨끈한 국물닭갈비 먹고 싶을 땐?', current_timestamp, null),
(4, '순살 불 닭갈비', 25000, '부드러운 닭다리살이 매력있는', current_timestamp, null),
(4, '볶음밥', 2000, '닭갈비 먹고 볶음밥은 사랑입니다♥', current_timestamp, null);

-- User Table Create SQL
-- 테이블 생성 SQL - User
CREATE TABLE User
(
    `user_id`        VARCHAR(10)    NOT NULL    COMMENT '유저 아이디. 회원가입', 
    `user_name`      VARCHAR(12)    NOT NULL    COMMENT '이름(닉네임)', 
    `user_password`  VARCHAR(200)   NOT NULL    COMMENT '유저 비밀번호', 
    `user_pnum`      TEXT           NOT NULL    COMMENT '유저 전화번호', 
    `created_at`     TIMESTAMP      NOT NULL    DEFAULT current_timestamp COMMENT '생성일시', 
    `updated_at`     TIMESTAMP       NULL    DEFAULT null COMMENT '수정일시', 
    `user_address`   TEXT           NOT NULL    COMMENT '회원주소', 
    PRIMARY KEY (user_id)
);

INSERT INTO User(user_id, user_name, user_password, user_pnum, created_at, updated_at, user_address)
VALUES
('wogml', '김재희','1234', '010-6237-9248', DEFAULT, DEFAULT, '인천광역시'),
('rokoko', '박은정','5678', '010-4433-5211', DEFAULT, DEFAULT, '인천광역시'),
('adora29', '김은형', '0000', '010-5439-3306', DEFAULT, DEFAULT, '경기도 부천시');

-- Review Table Create SQL
-- 테이블 생성 SQL - Review
CREATE TABLE Review
(
    `review_num`           BIGINT       NOT NULL    AUTO_INCREMENT COMMENT '리뷰 식별번호', 
    `user_id`              VARCHAR(10)  NOT NULL    COMMENT '유저 아이디',
    `store_num`            BIGINT       NOT NULL    COMMENT '가게 식별번호', 
    `rating`               FLOAT        NOT NULL    COMMENT '별점', 
    `review_text`          TEXT         NOT NULL    COMMENT '리뷰내용', 
    `ower_review_comment`  TEXT         NULL    COMMENT '사장님 댓글', 
    `crated_at`            TIMESTAMP    NOT NULL    DEFAULT current_timestamp COMMENT '생성일시', 
    `updated_at`           TIMESTAMP    NULL    DEFAULT null COMMENT '수정일시', 
	`rv_oriname`           VARCHAR(50)  NULL,
    `rv_sysname`           VARCHAR(50)  NULL,
     PRIMARY KEY (review_num),
     foreign key (user_id) references user(user_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
     foreign key (store_num) references store(store_num) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO Review(review_num, user_id, store_num, rating, review_text, ower_review_comment, crated_at, updated_at)
VALUES
(NULL, 'wogml', 1, 5, '성원닭갈비 추운 날이면 생각나는 곳이에요. 국물닭갈비인데 맛있어요. 직원분들도 친절하시고 음료도 무한리필 가능해요. 닭갈비 먹고 동치미까지 먹으면 최고입니다!', '좋은 댓글 남겨주셔서 감사합니다! 노력하는 성원닭갈비가 되겠습니다.', current_timestamp, null);

-- admin Table Create SQL
-- 테이블 생성 SQL - admin
CREATE TABLE admin
(
    `admin_id`    VARCHAR(12)     NOT NULL        COMMENT '관리자아이디', 
    `admin_pw`    VARCHAR(150)    NOT NULL        COMMENT '관리자 비밀번호', 
    `admin_auth`  INT             NOT NULL        DEFAULT 1 COMMENT '권한',
     PRIMARY KEY (admin_auth)
);
INSERT INTO admin(admin_id, admin_pw, admin_auth)
VALUES
('admin01','1111', 1);


-- QnA Table Create SQL
-- 테이블 생성 SQL - QnA
CREATE TABLE QnA
(
    `qna_num`      BIGINT           NOT NULL    AUTO_INCREMENT COMMENT '게시글 번호', 
    `qna_title`    VARCHAR(200)     NOT NULL    COMMENT '게시판 제목', 
    `qna_contents` VARCHAR(2000)    NOT NULL    COMMENT '게시판 내용', 
    `user_id`      VARCHAR(10)      NOT NULL    COMMENT '유저아이디', 
    `create_at`    TIMESTAMP        NOT NULL    DEFAULT current_timestamp COMMENT '생성일시', 
    `update_at`    TIMESTAMP        NULL    DEFAULT null COMMENT '수정일시', 
    `qna_reply`    VARCHAR(1000)    NULL    DEFAULT null COMMENT '답변내용', 
     PRIMARY KEY (qna_num),
     foreign key (user_id) references user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
        
INSERT INTO QnA(qna_title, qna_contents, user_id, create_at, update_at, qna_reply)
VALUES
('예약하기를 어떻게 하면 되나요?', '안녕하세요! 제목 그대로 사이트에서 예약하기가 가능하다고 들었는데 방법을 몰라 여쭤봅니다!', 'wogml', DEFAULT, DEFAULT, 'wogml님 안녕하세요! 예약하기는 예약할 업체를 클릭하여 상세보기에서 예약을 진행할 수 있습니다! 좋은하루 보내세요~');


-- Reservation Table Create SQL
-- 테이블 생성 SQL - Reservation
CREATE TABLE Reservation
(
    `user_id`            VARCHAR(10)    NOT NULL    COMMENT '유저아이디', 
    `resevation_id`      BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '예약 식별번호', 
    `store_num`          BIGINT         NOT NULL    COMMENT '가게 식별번호',
    `reservation_date`   DATE           NOT NULL    COMMENT '예약 일자', 
    `resevation_time`    TIME           NOT NULL    COMMENT '예약 시간', 
    `resevation_person`  INT            NOT NULL    COMMENT '예약 인원', 
     PRIMARY KEY (resevation_id),
     foreign key (user_id) references user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
     foreign key (store_num) references store (store_num) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO Reservation(user_id, store_num, reservation_date, resevation_time, resevation_person)
VALUES
('wogml', 1, '2023-12-25', '14:00', 10);


commit;


create or replace view rlist as
select u.user_pnum, s.store_name, r.*
from reservation r, store s, user u
where r.store_num=s.store_num
and r.user_id = u.user_id
order by r.resevation_id desc;

create or replace view stor_menu_list as
select m.menu_num, m.menu_name, s.store_name, s.store_num, s.store_adress, s.average_rating, s.photo_path
from store s, menu m
where s.store_num = m.store_num;

