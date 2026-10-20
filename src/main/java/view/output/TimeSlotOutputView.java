package view.output;

import model.dto.TimeSlotDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeSlotOutputView {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    // 타임슬롯 전체 조회 결과 출력
    public void showSlots(List<TimeSlotDTO> slots) {

        System.out.println("\n===== 타임슬롯 목록 =====");

        if (slots == null || slots.isEmpty()) {
            System.out.println("등록된 타임슬롯이 없습니다.");
            return;
        }

        System.out.printf("%-10s %-12s %-12s%n",
                "슬롯번호", "시작시간", "종료시간");

        System.out.println("--------------------------------");

        for (TimeSlotDTO slot : slots) {
            System.out.printf("%-10d %-12s %-12s%n",
                    slot.getSlotId(),
                    slot.getStartTime().format(FORMATTER),
                    slot.getEndTime().format(FORMATTER)
            );
        }
    }

    // 성공 및 실패 메시지 출력
    public void showMessage(String message) {
        System.out.println(message);
    }
}
