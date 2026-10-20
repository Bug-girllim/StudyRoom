package model.service;

import model.dao.TimeSlotDAO;
import model.dto.TimeSlotDTO;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotService {

    private final TimeSlotDAO dao = new TimeSlotDAO();

    // 1. 전체 조회
    public List<TimeSlotDTO> findAll() throws SQLException {
        return dao.findAll();
    }

    // 2. 시간 있나 검사
    private boolean isValidTime(LocalTime start, LocalTime end) {
        return start != null
                && end != null
                && start.isBefore(end);
    }

    // 3. 기존 슬롯과 시간 중복 검사
    private boolean isOverlapping(
            long excludeSlotId,
            LocalTime start,
            LocalTime end
    ) throws SQLException {

        for (TimeSlotDTO slot : dao.findAll()) {

            // 수정 중인 자기 자신은 검사에서 제외
            if (slot.getSlotId() == excludeSlotId) {
                continue;
            }

            // start < 기존 end && end > 기존 start
            if (start.isBefore(slot.getEndTime())
                    && end.isAfter(slot.getStartTime())) {
                return true;
            }
        }

        return false;
    }

    // 4. 등록
    public int insert(LocalTime start, LocalTime end)
            throws SQLException {

        if (!isValidTime(start, end)) {
            throw new IllegalArgumentException(
                    "시작 시간은 종료 시간보다 빨라야 합니다.");
        }

        if (isOverlapping(-1, start, end)) {
            throw new IllegalArgumentException(
                    "기존 타임슬롯과 시간이 겹칩니다.");
        }

        return dao.insert(start, end);
    }

    // 5. 수정
    public int update(
            long slotId,
            LocalTime start,
            LocalTime end
    ) throws SQLException {

        if (!isValidTime(start, end)) {
            throw new IllegalArgumentException(
                    "시작 시간은 종료 시간보다 빨라야 합니다.");
        }

        if (isOverlapping(slotId, start, end)) {
            throw new IllegalArgumentException(
                    "기존 타임슬롯과 시간이 겹칩니다.");
        }

        if (dao.isReserved(slotId)) {
            throw new IllegalArgumentException(
                    "예약에 사용 중인 슬롯은 수정할 수 없습니다.");
        }

        return dao.update(slotId, start, end);
    }

    // 6. 삭제
    public int delete(long slotId) throws SQLException {

        if (dao.isReserved(slotId)) {
            throw new IllegalArgumentException(
                    "예약에 사용 중인 슬롯은 삭제할 수 없습니다.");
        }

        return dao.delete(slotId);
    }
}
