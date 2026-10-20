package controller;

import model.dto.TimeSlotDTO;
import model.service.TimeSlotService;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotController {

    private final TimeSlotService service = new TimeSlotService();

    // 1. 타임슬롯 전체 조회
    public List<TimeSlotDTO> getAllSlots() throws SQLException {
        return service.findAll();
    }

    // 2. 타임슬롯 등록
    public String createSlot(LocalTime start, LocalTime end) {

        try {
            int result = service.insert(start, end);

            if (result > 0) {
                return "타임슬롯 등록이 완료되었습니다.";
            }

            return "타임슬롯 등록에 실패했습니다.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();

        } catch (SQLException e) {
            return "DB 오류로 등록할 수 없습니다.";
        }
    }

    // 3. 타임슬롯 수정
    public String updateSlot(
            long slotId,
            LocalTime start,
            LocalTime end
    ) {

        try {
            int result = service.update(slotId, start, end);

            if (result > 0) {
                return "타임슬롯 수정이 완료되었습니다.";
            }

            return "해당 타임슬롯이 존재하지 않습니다.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();

        } catch (SQLException e) {
            return "DB 오류로 수정할 수 없습니다.";
        }
    }

    // 4. 타임슬롯 삭제
    public String deleteSlot(long slotId) {

        try {
            int result = service.delete(slotId);

            if (result > 0) {
                return "타임슬롯 삭제가 완료되었습니다.";
            }

            return "해당 타임슬롯이 존재하지 않습니다.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();

        } catch (SQLException e) {
            return "DB 오류로 삭제할 수 없습니다.";
        }
    }
}

