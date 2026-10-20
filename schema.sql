SET NAMES utf8mb4;

DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS time_slot;
DROP TABLE IF EXISTS study_room;
DROP TABLE IF EXISTS member;

-- 회원
CREATE TABLE member (
    member_id       BIGINT       NOT NULL AUTO_INCREMENT,
    login_id        VARCHAR(50)  NOT NULL,
    login_password  VARCHAR(100) NOT NULL, 
    member_name     VARCHAR(50)  NOT NULL,
    phone           VARCHAR(20)  NOT NULL,
    member_role     VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (member_id),
    CONSTRAINT uk_member_login_id UNIQUE (login_id),
    CONSTRAINT ck_member_role CHECK (member_role IN ('USER', 'ADMIN'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 스터디룸
CREATE TABLE study_room (
    room_id    BIGINT       NOT NULL AUTO_INCREMENT,
    room_name  VARCHAR(100) NOT NULL,
    location   VARCHAR(100) NOT NULL,
    capacity   INT          NOT NULL, 
    CONSTRAINT pk_study_room PRIMARY KEY (room_id),
    CONSTRAINT ck_study_room_capacity CHECK (capacity >= 1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 예약
CREATE TABLE reservation (
    reservation_id    BIGINT NOT NULL AUTO_INCREMENT,
    member_id         BIGINT NOT NULL,
    room_id           BIGINT NOT NULL,
    slot_id           BIGINT NOT NULL,
    reservation_date  DATE   NOT NULL,
    CONSTRAINT pk_reservation PRIMARY KEY (reservation_id),
    CONSTRAINT uk_reservation_room_date_slot UNIQUE (room_id, reservation_date, slot_id),
    CONSTRAINT fk_reservation_member FOREIGN KEY (member_id)
        REFERENCES member (member_id) ON DELETE RESTRICT,
    CONSTRAINT fk_reservation_room FOREIGN KEY (room_id)
        REFERENCES study_room (room_id) ON DELETE RESTRICT,
    CONSTRAINT fk_reservation_slot FOREIGN KEY (slot_id)
        REFERENCES time_slot (slot_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 타임 슬롯
CREATE TABLE time_slot (
    slot_id     BIGINT NOT NULL AUTO_INCREMENT,
    start_time  TIME   NOT NULL,
    end_time    TIME   NOT NULL,
    CONSTRAINT pk_time_slot PRIMARY KEY (slot_id),
    CONSTRAINT ck_time_slot_range CHECK (start_time < end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
