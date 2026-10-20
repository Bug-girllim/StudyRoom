package view.output;

import model.dto.RoomDTO;

import java.util.List;

public class RoomOutputView {

    public void printRooms(List<RoomDTO> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("등록된 스터디룸이 없습니다.");
            return;
        }
        System.out.println("\n번호 | 이름 | 위치 | 수용 인원");
        for (RoomDTO room : rooms) {
            System.out.println(room.getRoomId() + " | " + room.getRoomName() + " | "
                    + room.getLocation() + " | " + room.getCapacity() + "명");
        }
    }
}