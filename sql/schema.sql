
-- StudyRoom 데이터베이스 및 테이블 생성
-- MySQL 8.0.16 이상 권장

CREATE DATABASE IF NOT EXISTS studyroom
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE studyroom;

-- 1. 회원 테이블
CREATE TABLE IF NOT EXISTS member (
                                      member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      login_id VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    member_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    member_role VARCHAR(10) NOT NULL DEFAULT 'USER',
    CONSTRAINT chk_member_role
    CHECK (member_role IN ('USER', 'ADMIN'))
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. 스터디룸 테이블
CREATE TABLE IF NOT EXISTS study_room (
                                          room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          room_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    CONSTRAINT chk_room_capacity CHECK (capacity >= 1)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 타임슬롯 테이블
CREATE TABLE IF NOT EXISTS time_slot (
                                         slot_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         start_time TIME NOT NULL,
                                         end_time TIME NOT NULL,
                                         CONSTRAINT chk_slot_time CHECK (start_time < end_time)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. 예약 테이블
CREATE TABLE IF NOT EXISTS reservation (
                                           reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           member_id BIGINT NOT NULL,
                                           room_id BIGINT NOT NULL,
                                           slot_id BIGINT NOT NULL,
                                           reservation_date DATE NOT NULL,

                                           CONSTRAINT fk_reservation_member
                                           FOREIGN KEY (member_id)
    REFERENCES member(member_id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_reservation_room
    FOREIGN KEY (room_id)
    REFERENCES study_room(room_id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_reservation_slot
    FOREIGN KEY (slot_id)
    REFERENCES time_slot(slot_id)
    ON DELETE RESTRICT,

    CONSTRAINT uq_room_date_slot
    UNIQUE (room_id, reservation_date, slot_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 생성 결과 확인
SHOW TABLES;
