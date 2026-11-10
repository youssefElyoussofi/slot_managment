package backend.slot_managment.dtos;

import backend.slot_managment.entities.Slot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SlotResponse(UUID id, Instant startsAt, Instant endsAt, int capacity,
                           int bookedCount,int remainingSeats, boolean isFull) {


    public static SlotResponse from(Slot slot){
        return new SlotResponse(slot.getId(),slot.getStartsAt(),slot.getEndsAt(),
                slot.getCapacity(),slot.getBookedCount(), slot.remainingSeats(), slot.isFull());
    }

    public static List<SlotResponse> buildList(List<Slot> slots){
        List<SlotResponse> list = new ArrayList<>(slots.size());
        for (Slot slot : slots)
            list.add(SlotResponse.from(slot));
        return list;
    }
}
