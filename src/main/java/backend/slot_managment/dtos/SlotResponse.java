package backend.slot_managment.dtos;

import backend.slot_managment.entities.Slot;

import java.time.Instant;
import java.util.UUID;

public record SlotResponse(UUID slotId, Instant startsAt, Instant endsAt, int capacity,
                           int bookedCount,int remainingSeats, boolean isFull) {
    public static SlotResponse from(Slot slot){
        return new SlotResponse(slot.getId(),slot.getStartsAt(),slot.getEndsAt(),
                slot.getCapacity(),slot.getBookedCount(), slot.remainingSeats(), slot.isFull());
    }
}
