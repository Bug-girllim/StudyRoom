package controller;

import model.dto.RoomDTO;
import model.service.RoomService;

import java.util.List;

public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    public List<RoomDTO> findAllRooms() {
        return roomService.findAll();
    }

    public void registerRoom(String roomName, String location, String capacity) {
        roomService.register(roomName, location, capacity);
    }

    public void modifyRoom(long roomId, String roomName, String location, String capacity) {
        roomService.modify(roomId, roomName, location, capacity);
    }

    public void removeRoom(long roomId) {
        roomService.remove(roomId);
    }
}