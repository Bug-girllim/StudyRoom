package model.dto;

public class RoomDTO {

    private int roomId;
    private String roomName;
    private String location;
    private int capacity;

    public RoomDTO() {}

    public RoomDTO(int roomId, String roomName, String location, int capacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.location = location;
        this.capacity = capacity;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}