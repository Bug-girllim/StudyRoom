USE studyroom;

    -- 스터디룸 샘플 데이터 5개
INSERT INTO study_room (room_name, location, capacity)
VALUES
    ('A룸', '1층', 4),
    ('B룸', '1층', 6),
    ('C룸', '2층', 4),
    ('D룸', '2층', 8),
    ('E룸', '3층', 10);

-- 타임슬롯 샘플 데이터 5개
INSERT INTO time_slot (start_time, end_time)
VALUES
    ('09:00:00', '11:00:00'),
    ('11:00:00', '13:00:00'),
    ('13:00:00', '15:00:00'),
    ('15:00:00', '17:00:00'),
    ('17:00:00', '19:00:00');

-- 회원 샘플 데이터 5개 (관리자 1명 + 일반 회원 4명)
INSERT INTO member
(login_id, password, member_name, phone, member_role)
VALUES
    ('admin01', 'test1234', '관리자', '010-0000-0001', 'ADMIN'),
    ('user01', 'test1234', '김민수', '010-0000-0002', 'USER'),
    ('user02', 'test1234', '이지은', '010-0000-0003', 'USER'),
    ('user03', 'test1234', '박서준', '010-0000-0004', 'USER'),
    ('user04', 'test1234', '최유진', '010-0000-0005', 'USER');

SELECT member_id, login_id FROM studyroom.member;
SELECT room_id, room_name FROM studyroom.study_room;
SELECT slot_id, start_time, end_time FROM studyroom.time_slot;


-- 예약 샘플 데이터 5개
-- member_id, room_id, slot_id가 1~5인 경우

INSERT INTO reservation
(member_id, room_id, slot_id, reservation_date)
VALUES
    (2, 1, 1, '2026-10-21'),
    (3, 2, 2, '2026-10-21'),
    (4, 3, 3, '2026-10-22'),
    (5, 4, 4, '2026-10-22'),
    (2, 5, 5, '2026-10-23');
