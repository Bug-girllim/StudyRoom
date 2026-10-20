
package view.input;

import controller.RoomController;
import model.dto.RoomDTO;
import view.output.RoomOutputView;

import java.util.List;
import java.util.Scanner;

public class RoomInputView {

    /* comment.
     *   View 패키지의 책임
     *   - 사용자와 상호작용을 하는 책임을 갖는다.
     *   - InputView 의 할 일
     *     1. Scanner 객체로 사용자 입력 받기
     *     2. Controller 에게 입력한 값 전달
     *     3. OutputView 를 호출하여 결과 출력 요청
     *   - InputView 가 하면 안 되는 일
     *     1. SQL 작성
     *     2. 비즈니스 로직 처리 (권한/중복/참조 검사 등은 Service 의 일)
     *     3. commit / rollback
     * */

    private final RoomController roomController;
    private final RoomOutputView roomOutputView;
    private final Scanner sc = new Scanner(System.in);

    public RoomInputView(RoomController roomController, RoomOutputView roomOutputView) {
        this.roomController = roomController;
        this.roomOutputView = roomOutputView;
    }


    public void displayRoomList() {
        try {
            List<RoomDTO> rooms = roomController.findAllRooms();
            roomOutputView.printRooms(rooms);
        } catch (RuntimeException e) {
            printError(e);
        }
    }

    public void displayManageView() {
        while (true) {
            System.out.println("\n[스터디룸 관리]");
            System.out.println("1. 등록");
            System.out.println("2. 목록");
            System.out.println("3. 수정");
            System.out.println("4. 삭제");
            System.out.println("0. 뒤로");
            System.out.print("메뉴를 입력해주세요 : ");

            int menu = readInt();

            try {
                switch (menu) {
                    case 1:
                        register();
                        break;
                    case 2:
                        displayRoomList();
                        break;
                    case 3:
                        modify();
                        break;
                    case 4:
                        remove();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("올바른 메뉴 번호를 입력해 주세요.");
                        break;
                }
            } catch (RuntimeException e) {
                printError(e);
            }
        }
    }

    private void register() {
        String roomName = readLine("룸 이름 : ");
        String location = readLine("위치 : ");
        String capacity = readLine("수용 인원 : ");

        roomController.registerRoom(roomName, location, capacity);
        System.out.println("스터디룸이 등록되었습니다.");
    }

    private void modify() {
        displayRoomList();
        long roomId = readRoomId();
        String roomName = readLine("새 룸 이름 : ");
        String location = readLine("새 위치 : ");
        String capacity = readLine("새 수용 인원 : ");

        roomController.modifyRoom(roomId, roomName, location, capacity);
        System.out.println("스터디룸 정보가 수정되었습니다.");
    }

    private void remove() {
        displayRoomList();
        long roomId = readRoomId();

        roomController.removeRoom(roomId);
        System.out.println("스터디룸이 삭제되었습니다.");
    }

    private void printError(RuntimeException e) {
        System.out.println(e.getMessage());
        if (e.getCause() != null) {
            System.out.println("[DB 오류] " + e.getCause().getMessage());
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private long readRoomId() {
        System.out.print("룸 번호 : ");
        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 룸 번호를 입력해 주세요.");
        }
    }
}
