package model.service;

import common.DBUtil;
import common.LoginSession;
import model.dao.RoomDAO;
import model.dto.RoomDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomService {

    /* comment.
     *   Service 계층의 책임
     *   - Service 계층은 비즈니스 로직을 담당하는 계층이다.
     *   Service 계층이 해야 할 일
     *   - 1. DAO 계층 호출
     *   - 2. 데이터 검증 및 가공 (권한, 입력값, 존재 여부, 예약 참조)
     *   - 3. Connection 생성
     *   - 4. 예외처리
     *  */

    public List<RoomDTO> findAll() {

        checkLogin();

        try (Connection con = DBUtil.getConnection()) {

            return new RoomDAO(con).findAll();

        } catch (SQLException e) {
            throw new RuntimeException("스터디룸을 처리할 수 없습니다.", e);
        }
    }

    public void register(String roomName, String location, String capacityInput) {

        checkAdmin();
        int capacity = validate(roomName, location, capacityInput);

        try (Connection con = DBUtil.getConnection()) {

            new RoomDAO(con).insert(new RoomDTO(0, roomName.trim(), location.trim(), capacity));

        } catch (SQLException e) {
            throw new RuntimeException("스터디룸을 처리할 수 없습니다.", e);
        }
    }

    public void modify(long roomId, String roomName, String location, String capacityInput) {

        checkAdmin();

        try (Connection con = DBUtil.getConnection()) {

            RoomDAO roomDAO = new RoomDAO(con);

            if (roomDAO.findById(roomId) == null) {
                throw new IllegalArgumentException("존재하지 않는 스터디룸입니다.");
            }

            int capacity = validate(roomName, location, capacityInput);

            roomDAO.update(new RoomDTO((int) roomId, roomName.trim(), location.trim(), capacity));

        } catch (SQLException e) {
            throw new RuntimeException("스터디룸을 처리할 수 없습니다.", e);
        }
    }

    public void remove(long roomId) {

        checkAdmin();

        try (Connection con = DBUtil.getConnection()) {

            RoomDAO roomDAO = new RoomDAO(con);

            if (roomDAO.findById(roomId) == null) {
                throw new IllegalArgumentException("존재하지 않는 스터디룸입니다.");
            }
            if (roomDAO.hasReservation(roomId)) {
                throw new IllegalArgumentException("연결된 예약이 있어 삭제할 수 없습니다.");
            }

            roomDAO.delete(roomId);

        } catch (SQLException e) {
            throw new RuntimeException("스터디룸을 처리할 수 없습니다.", e);
        }
    }

    private void checkLogin() {
        if (!LoginSession.isLoggedIn()) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
    }

    private void checkAdmin() {
        checkLogin();
        if (!LoginSession.isAdmin()) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    private int validate(String roomName, String location, String capacityInput) {

        if (roomName == null || roomName.isBlank()
                || location == null || location.isBlank()
                || capacityInput == null || capacityInput.isBlank()) {
            throw new IllegalArgumentException("모든 항목을 입력해 주세요.");
        }

        try {
            int capacity = Integer.parseInt(capacityInput.trim());
            if (capacity < 1) {
                throw new IllegalArgumentException("수용 인원을 올바르게 입력하세요.");
            }
            return capacity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수용 인원을 올바르게 입력하세요.");
        }
    }
}
