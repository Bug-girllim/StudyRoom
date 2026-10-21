# StudyRoom 🏫

**Java와 MySQL(JDBC)을 활용한 콘솔 기반 스터디룸 예약 관리 시스템**

> 4인 팀 · 1일 개발 프로젝트 · Java / Gradle / MySQL / JDBC

## 1. 프로젝트 소개

StudyRoom은 회원이 스터디룸과 날짜, 고정된 이용 시간대를 선택하여 예약하는 콘솔 프로그램입니다. **같은 스터디룸·날짜·시간대에는 한 건의 예약만 허용**하며, 중복이 없으면 별도의 관리자 승인 없이 바로 예약이 확정됩니다.

### 핵심 흐름

```text
회원가입 → 로그인 → 스터디룸/시간대 조회 → 예약 신청
                                         ├─ 중복 없음 → 예약 확정
                                         └─ 중복 있음 → 예약 거절
                                   → 내 예약 조회·변경·취소
```

## 2. 주요 기능

| 구분 | 기능 |
|---|---|
| 비로그인 | 회원가입, 로그인 |
| 회원 (`USER`) | 스터디룸 및 시간대 목록 조회, 예약 등록·조회·변경·취소, 본인 정보 수정, 로그아웃 |
| 관리자 (`ADMIN`) | 회원 목록·수정·삭제, 스터디룸 CRUD, 타임슬롯 CRUD, 전체 예약 조회, 로그아웃 |

- **자동 예약 확정:** `(room_id, reservation_date, slot_id)`가 중복되지 않을 때 즉시 확정
- **예약 날짜 제한:** 오늘 또는 미래 날짜만 허용
- **본인 예약 관리:** 회원은 자신의 예약만 수정·취소 가능
- **타임슬롯 겹침 방지:** 서로 다른 슬롯의 시간 범위가 겹치면 등록·수정 거절
- **참조 데이터 보호:** 예약이 연결된 회원·룸·슬롯 삭제 제한
- **관리자 역할 구분:** 일반 회원가입으로는 `ADMIN` 계정 생성 불가

예약은 **룸 전체 사용 기준**이며, 수용 인원(`capacity`)은 정보 표시용입니다. 좌석별 예약과 승인 절차는 구현하지 않습니다.

## 3. 기술 스택

| 항목 | 기술 |
|---|---|
| 언어 | Java |
| 빌드 | Gradle |
| DB | MySQL |
| DB 연결 | JDBC |
| 실행 방식 | 콘솔 프로그램 |
| 버전 관리 | Git / GitHub |

### 실제 프로젝트 폴더 구조

아래 구조는 IntelliJ IDEA에서 확인한 폴더와 파일을 기준으로 작성했습니다.

```text
StudyRoom/
├── sql/
│   ├── schema.sql
│   └── sample-data.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── common/
│   │   │   │   ├── DBUtil.java
│   │   │   │   └── LoginSession.java
│   │   │   ├── config/
│   │   │   │   └── JDBCTemplate.java
│   │   │   ├── controller/
│   │   │   │   ├── MemberController.java
│   │   │   │   ├── ReservationController.java
│   │   │   │   ├── RoomController.java
│   │   │   │   └── TimeSlotController.java
│   │   │   ├── model/
│   │   │   │   ├── dao/
│   │   │   │   │   ├── MemberDAO.java
│   │   │   │   │   ├── ReservationDAO.java
│   │   │   │   │   ├── RoomDAO.java
│   │   │   │   │   └── TimeSlotDAO.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── MemberDTO.java
│   │   │   │   │   ├── ReservationDTO.java
│   │   │   │   │   ├── RoomDTO.java
│   │   │   │   │   └── TimeSlotDTO.java
│   │   │   │   └── service/
│   │   │   │       ├── MemberService.java
│   │   │   │       ├── ReservationService.java
│   │   │   │       ├── RoomService.java
│   │   │   │       └── TimeSlotService.java
│   │   │   ├── view/
│   │   │   │   ├── input/
│   │   │   │   │   ├── MemberInputView.java
│   │   │   │   │   ├── ReservationInputView.java
│   │   │   │   │   ├── RoomInputView.java
│   │   │   │   │   └── TimeSlotInputView.java
│   │   │   │   └── output/
│   │   │   │       ├── MemberOutputView.java
│   │   │   │       ├── ReservationOutputView.java
│   │   │   │       ├── RoomOutputView.java
│   │   │   │       └── TimeSlotOutputView.java
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── queries.xml
│   └── test/
├── .gitignore
├── build.gradle
├── db.properties
├── db.properties.example
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```

`out/`은 IntelliJ에서 보이는 빌드 출력 폴더로, 위 소스 구조에서는 생략했습니다.

### 계층 및 공통 클래스 역할

```text
InputView → Controller → Service → DAO → MySQL
                                 ↕
                                DTO
OutputView ← 처리 결과
```

| 구분 | 위치 | 역할 |
|---|---|---|
| 입력 View | `view/input/` | 콘솔 메뉴와 사용자 입력 |
| 출력 View | `view/output/` | 조회 결과 및 처리 메시지 출력 |
| Controller | `controller/` | 요청을 Service로 전달 |
| Service | `model/service/` | 입력 검증과 예약·권한 등 업무 규칙 처리 |
| DAO | `model/dao/` | JDBC를 통한 SQL 실행 |
| DTO | `model/dto/` | 계층 사이의 데이터 전달 |
| JDBC 접속 템플릿 | `config/JDBCTemplate.java` | 프로젝트 루트의 `src/main/resources/db.properties`에서 `db.url`, `db.username`, `db.password`를 읽어 JDBC 연결을 생성하고 `Connection`·`Statement`·`ResultSet`을 해제 |
| SQL 조회 유틸 | `common/DBUtil.java` | 클래스패스의 `queries.xml`을 `Properties.loadFromXML()`로 읽고 `getQuery(String key)`로 SQL문 반환 |
| 로그인 세션 | `common/LoginSession.java` | 로그인 회원 정보 및 권한 관리 |
| SQL 리소스 | `src/main/resources/queries.xml` | DAO에서 사용할 SQL문을 키(`entry key`)별로 관리. 제공된 코드에는 `timeSlot.*` 쿼리가 포함됨 |
| 진입점 | `Application.java` | `main()` 실행 |

> **공통 코드 사용 기준:** DAO는 `JDBCTemplate.getConnection()`으로 DB 연결을 얻고, `DBUtil.getQuery("쿼리키")`로 XML에 등록된 SQL문을 가져옵니다. 두 클래스는 용도가 다르며 `QueryUtil.java`는 사용하지 않습니다. `queries.xml`의 현재 제공된 항목은 타임슬롯 관련 SQL로, 다른 기능의 SQL은 담당자가 추가해야 합니다.

## 4. 데이터베이스

| 테이블 | 설명 | 주요 컬럼 |
|---|---|---|
| `member` | 회원 정보 | `member_id`, `login_id`, `login_password`, `member_name`, `phone`, `member_role` |
| `study_room` | 스터디룸 정보 | `room_id`, `room_name`, `location`, `capacity` |
| `time_slot` | 공통 예약 시간대 | `slot_id`, `start_time`, `end_time` |
| `reservation` | 예약 정보 | `reservation_id`, `member_id`, `room_id`, `slot_id`, `reservation_date` |

**테이블 관계**

- `member` 1:N `reservation`
- `study_room` 1:N `reservation`
- `time_slot` 1:N `reservation`

**중요 제약조건**

- `member.login_id`: `UNIQUE`
- `study_room.room_name`: `UNIQUE`
- `reservation(room_id, reservation_date, slot_id)`: 복합 `UNIQUE`
- 예약의 외래키: `ON DELETE RESTRICT`
- `capacity >= 1`, `start_time < end_time` 등 `CHECK`

### 기본 시간대

| 번호 | 시간 |
|---:|---|
| 1 | 09:00 ~ 11:00 |
| 2 | 11:00 ~ 13:00 |
| 3 | 13:00 ~ 15:00 |
| 4 | 15:00 ~ 17:00 |
| 5 | 17:00 ~ 19:00 |

초기 데이터는 **4개 테이블 각각 최소 5행**을 기준으로 준비합니다(회원은 관리자 1명·일반 회원 4명 이상).

## 5. 프로젝트 실행 준비

1. **개발 환경:** 프로젝트에서 사용하는 JDK, Gradle, MySQL을 준비합니다. Java 및 MySQL의 정확한 버전은 확인이 필요합니다.
2. **DB 생성:** 팀에서 합의한 DB 스키마를 생성합니다.
3. **테이블 생성:** `sql/schema.sql`을 실행합니다.
4. **초기 데이터:** `sql/sample-data.sql`을 실행합니다. (`member`·`study_room`·`time_slot` → `reservation` 순서)
5. **DB 연결:** 저장소의 `db.properties.example`을 참고하여 프로젝트 루트의 `src/main/resources/db.properties`에 **자신의 로컬 접속 정보**를 입력합니다. 설정 키 `db.url`, `db.username`, `db.password`를 지정합니다.
6. **실행:** IntelliJ IDEA에서 `Application.java` 파일을 열고 `main()` 메서드를 실행합니다.

> **실행 전 확인:** DB 이름과 접속 설정 파일의 실제 위치는 통합한 소스 코드 기준으로 확인해야 합니다. 기획서에서 사용하는 `CHECK` 제약은 MySQL 8.0.16 이상에서 적용되므로 실제 DB 버전도 확인해야 합니다 (`SELECT VERSION();`).

> **SQL 재실행 주의:** `sample-data.sql`을 초기화 없이 여러 번 실행하면 PK·UNIQUE 제약으로 중복 삽입 오류가 날 수 있습니다. 다시 넣기 전 데이터를 초기화하거나 재삽입 방지 SQL을 사용해야 하며, 실제 초기화 절차는 팀에서 확정해야 합니다.

## 6. 콘솔 메뉴

```text
[시작 메뉴]
1. 회원가입
2. 로그인
0. 종료

[회원 메뉴]
1. 스터디룸 목록
2. 타임슬롯 목록
3. 예약 신청
4. 내 예약 목록
5. 내 예약 변경
6. 내 예약 취소
7. 내 정보 수정
0. 로그아웃

[관리자 메뉴]
1. 회원 관리
2. 스터디룸 관리
3. 타임슬롯 관리
4. 전체 예약 목록
0. 로그아웃
```

## 7. 팀 역할 및 협업

**팀원:** 이종민 · 김동윤 · 조성원 · 최은정

| 담당 | 팀원 | 영역 | 공통 책임 |
|---|---|---|---|
| A | 이종민 | 회원·로그인 | `Application`, `LoginSession` |
| B | 김동윤 | 스터디룸 | JDBC 연결, DB 스키마 및 샘플 SQL |
| C | 조성원 | 예약 | 예약 중복 검사 |
| D | 최은정 | 타임슬롯 | 시간 겹침 검사, 통합 테스트 |

**Git 협업:** `main`을 기준으로 각자 작업 브랜치에서 개발하고 Pull Request로 병합합니다. 현재 IntelliJ 화면에서 확인된 작업 브랜치 예시는 `feat_eun`입니다. 다른 팀원의 실제 브랜치명은 별도 확인이 필요합니다.

## 8. 테스트 핵심 시나리오

- [ ] 회원가입·로그인 및 USER/ADMIN 메뉴 분기
- [ ] 동일 아이디로 회원가입 시 거절
- [ ] 사용 가능한 룸·날짜·슬롯의 예약 성공
- [ ] 동일 룸·날짜·슬롯 중복 예약 거절
- [ ] 다른 룸 또는 다른 날짜는 예약 허용
- [ ] 과거 날짜 또는 존재하지 않는 룸·슬롯 거절
- [ ] 본인 예약만 변경·취소 허용
- [ ] 예약 변경 시 자기 예약을 제외한 중복 검사
- [ ] 취소 후 같은 시간대 재예약 허용
- [ ] 타임슬롯 겹침 거절, 종료와 시작이 맞닿은 슬롯은 허용
- [ ] 예약이 연결된 회원·룸·슬롯 삭제 방지
- [ ] 잘못된 메뉴 입력에도 프로그램 계속 실행
- [ ] 네 개 테이블에 각각 최소 5행의 샘플 데이터 생성

## 9. 프로젝트 범위

**포함:** 회원·룸·시간대·예약 관리, 권한 분리, 예약 중복 방지, 슬롯 시간 겹침 방지, JDBC 연동.

**제외:** 웹 UI, 비회원 조회, 결제, 공지사항, 좌석별 예약, 관리자 수동 승인, 예약 상태·취소 이력, 이메일 인증, 알림, 검색·페이징.

## 10. 저장소 및 참고 문서

- GitHub: https://github.com/Bug-girllim/StudyRoom.git
- 상세 기획안: `docs/StudyRoom_최종기획안.md` (추후 해당 위치에 업로드할 경우)
- SQL 파일: `sql/schema.sql`, `sql/sample-data.sql` (기획상 파일 경로)
- ERD: 팀에서 작성한 파일을 `docs/`에 추가하여 관리

## 11. 보안 주의

**보안:** 이번 학습용 버전은 실습 범위에 맞춰 가상 계정의 비밀번호를 해시 없이 저장하고 문자열로 비교합니다. 실제 사용자 정보나 실제 비밀번호를 사용하지 마세요. DB 접속 비밀번호가 들어간 `src/main/resources/db.properties` 등은 `.gitignore`에 등록하여 Git에 올리지 마세요. 외부 배포 시에는 검증된 비밀번호 해시 방식 등 보안 개선이 필요합니다.