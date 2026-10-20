package model.dao;

import model.dto.RoomDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    /* comment.
     *   DAO 계층의 책임
     *   - DAO 는 DB 로 접근하기 위한 관문이다.
     *   DAO 계층의 해야 할 일
     *   - 1. SQL 구문을 실행한다.
     *   - 2. Statement 객체 생성
     *   - 3. 전달 받은 파라미터를 SQL 에 바인딩
     *   - 4. ResultSet 으로 SQL 결과 받기
     *   - 5. SQL 결과를 Java 객체로 변환
     *  */

    private final Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    public RoomDTO findById(long roomId) throws SQLException {

        String query = "SELECT room_id, room_name, location, capacity "
                + "FROM studyroom.study_room WHERE room_id = ?";
        RoomDTO room = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, roomId);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                room = new RoomDTO(
                        rset.getInt("room_id"),
                        rset.getString("room_name"),
                        rset.getString("location"),
                        rset.getInt("capacity")
                );
            }
        }

        return room;
    }

    // 목록 조회: 없으면 빈 리스트
    public List<RoomDTO> findAll() throws SQLException {

        String query = "SELECT room_id, room_name, location, capacity "
                + "FROM studyroom.study_room ORDER BY room_id";
        // 조회 결과를 담을 객체 생성
        List<RoomDTO> rooms = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                RoomDTO room = new RoomDTO(
                        rset.getInt("room_id"),
                        rset.getString("room_name"),
                        rset.getString("location"),
                        rset.getInt("capacity")
                );

                rooms.add(room);
            }
        }

        return rooms;
    }

    public boolean insert(RoomDTO room) throws SQLException {

        String query = "INSERT INTO studyroom.study_room (room_name, location, capacity) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, room.getRoomName());
            pstmt.setString(2, room.getLocation());
            pstmt.setInt(3, room.getCapacity());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(RoomDTO room) throws SQLException {

        String query = "UPDATE studyroom.study_room SET room_name = ?, location = ?, capacity = ? "
                + "WHERE room_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, room.getRoomName());
            pstmt.setString(2, room.getLocation());
            pstmt.setInt(3, room.getCapacity());
            pstmt.setLong(4, room.getRoomId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(long roomId) throws SQLException {

        String query = "DELETE FROM studyroom.study_room WHERE room_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, roomId);

            return pstmt.executeUpdate() > 0;
        }
    }

    // 삭제 전에 이 룸을 쓰는 예약이 있는지 확인 (F-09)
    public boolean hasReservation(long roomId) throws SQLException {

        String query = "SELECT 1 FROM studyroom.reservation WHERE room_id = ? LIMIT 1";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, roomId);

            ResultSet rset = pstmt.executeQuery();

            return rset.next();
        }
    }
}