package view.input;

import controller.TimeSlotController;
import view.output.TimeSlotOutputView;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TimeSlotInputView {

    private final Scanner sc = new Scanner(System.in);

    private final TimeSlotController controller;

    private final TimeSlotOutputView output;

    public TimeSlotInputView(
            TimeSlotController controller,
            TimeSlotOutputView output
    ) { this.controller = controller;
        this.output = output;
    }


    // 타임슬롯 관리 메뉴
    public void showMenu() {

        while (true) {
            System.out.println("\n===== 타임슬롯 관리 =====");
            System.out.println("1. 타임슬롯 전체 조회");
            System.out.println("2. 타임슬롯 등록");
            System.out.println("3. 타임슬롯 수정");
            System.out.println("4. 타임슬롯 삭제");
            System.out.println("0. 이전 메뉴");
            System.out.print("메뉴 선택: ");

            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    showAllSlots();
                    break;
                case "2":
                    createSlot();
                    break;
                case "3":
                    updateSlot();
                    break;
                case "4":
                    deleteSlot();
                    break;
                case "0":
                    return;
                default:
                    output.showMessage("올바른 메뉴 번호를 입력해 주세요.");
            }
        }
    }

    public void showAllSlots() {
        try {
            output.showSlots(controller.getAllSlots());
        } catch (SQLException e) {
            output.showMessage("타임슬롯 조회 중 DB 오류가 발생했습니다.");
        }
    }

    private void createSlot() {
        try {
            System.out.println("\n[타임슬롯 등록]");

            LocalTime start = readTime("시작 시간 (HH:mm): ");
            LocalTime end = readTime("종료 시간 (HH:mm): ");

            output.showMessage(controller.createSlot(start, end));

        } catch (DateTimeParseException e) {
            output.showMessage("시간 형식이 올바르지 않습니다. 예: 09:00");
        }
    }

    private void updateSlot() {
        try {
            System.out.println("\n[타임슬롯 수정]");

            long slotId = readSlotId();
            LocalTime start = readTime("변경 시작 시간 (HH:mm): ");
            LocalTime end = readTime("변경 종료 시간 (HH:mm): ");

            output.showMessage(
                    controller.updateSlot(slotId, start, end)
            );

        } catch (NumberFormatException e) {
            output.showMessage("슬롯 번호는 숫자로 입력해 주세요.");
        } catch (DateTimeParseException e) {
            output.showMessage("시간 형식이 올바르지 않습니다. 예: 09:00");
        }
    }

    private void deleteSlot() {
        try {
            System.out.println("\n[타임슬롯 삭제]");

            long slotId = readSlotId();

            output.showMessage(controller.deleteSlot(slotId));

        } catch (NumberFormatException e) {
            output.showMessage("슬롯 번호는 숫자로 입력해 주세요.");
        }
    }

    private LocalTime readTime(String message) {
        System.out.print(message);
        return LocalTime.parse(sc.nextLine().trim());
    }

    private long readSlotId() {
        System.out.print("타임슬롯 번호: ");
        return Long.parseLong(sc.nextLine().trim());
    }
}
