package model.dao;

import common.DBUtil;
import config.JDBCTemplate;
import model.dto.TimeSlotDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDAO {

    public List<TimeSlotDTO> findAll() throws SQLException {

        List<TimeSlotDTO> slots = new ArrayList<>();

        String sql = DBUtil.getQuery("timeSlot.selectAll");

        try (Connection conn = JDBCTemplate.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TimeSlotDTO slot = new TimeSlotDTO(
                        rs.getLong("slot_id"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime()
                );

                slots.add(slot);
            }
        }

        return slots;
    }

    public int insert(LocalTime startTime, LocalTime endTime)
            throws SQLException {

        String sql = DBUtil.getQuery("timeSlot.insert");

        try (Connection conn = JDBCTemplate.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTime(1, Time.valueOf(startTime));
            pstmt.setTime(2, Time.valueOf(endTime));

            return pstmt.executeUpdate();
        }
    }

    public int update(long slotId, LocalTime startTime, LocalTime endTime)
            throws SQLException {

        String sql = DBUtil.getQuery("timeSlot.update");

        try (Connection conn = JDBCTemplate.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTime(1, Time.valueOf(startTime));
            pstmt.setTime(2, Time.valueOf(endTime));
            pstmt.setLong(3, slotId);

            return pstmt.executeUpdate();
        }
    }

    public int delete(long slotId) throws SQLException {

        String sql = DBUtil.getQuery("timeSlot.delete");

        try (Connection conn = JDBCTemplate.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, slotId);

            return pstmt.executeUpdate();
        }
    }

    public boolean isReserved(long slotId) throws SQLException {

        String sql = DBUtil.getQuery("timeSlot.isReserved");

        try (Connection conn = JDBCTemplate.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, slotId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }
}
