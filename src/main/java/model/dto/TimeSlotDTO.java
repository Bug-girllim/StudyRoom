package model.dto;

import java.time.LocalTime;

public class TimeSlotDTO {

    private long slotId;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlotDTO() {
    }

    public TimeSlotDTO(long slotId, LocalTime startTime, LocalTime endTime) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getSlotId() {
        return slotId;
    }

    public void setSlotId(long slotId) {
        this.slotId = slotId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
