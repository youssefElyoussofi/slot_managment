package backend.slot_managment.services;

import backend.slot_managment.dtos.GenerateSlotsRequest;
import backend.slot_managment.dtos.SlotResponse;
import backend.slot_managment.entities.Slot;
import backend.slot_managment.repositories.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final SlotRepository slotRepository;

    @Transactional
    public List<SlotResponse> createSlot(GenerateSlotsRequest request){
        ZoneId zoneId = ZoneId.of("Africa/Casablanca");

        if (request.toDate().compareTo(request.fromDate()) <= 0)
            throw new RuntimeException("slots dates duration cannot end before start");

        LocalTime strictStart = request.startsAt().withMinute(0).withSecond(0).withNano(0);
        LocalTime strictEnd = request.endsAt().withMinute(0).withSecond(0).withNano(0);

        if (request.endsAt().compareTo(request.startsAt()) <= 0)
            throw new RuntimeException("slots times duration cannot end before start");

        LocalDate currDate = request.fromDate();

        List<SlotResponse> slotResponseList = new ArrayList<>();
        while (request.toDate().compareTo(currDate) >= 0){
            if (request.skipWeekends() && isWeekends(currDate))
                continue;
            while (strictEnd.compareTo(strictStart) > 0){

                Instant slotStartsAt = LocalDateTime.of(currDate, strictStart).atZone(zoneId).toInstant();
                Instant slotEndsAt = LocalDateTime.of(currDate, strictStart.plusHours(1)).atZone(zoneId).toInstant();
                if (slotRepository.existsByStartsAt(slotStartsAt) || slotStartsAt.isBefore(Instant.now()))
                    continue;
                Slot slot = new Slot();
                slot.setStartsAt(slotStartsAt);
                slot.setEndsAt(slotEndsAt);
                slot.setCapacity(request.capacity());
                slot.setBookedCount(0);

                Slot saved = slotRepository.save(slot);
                slotResponseList.add(SlotResponse.from(saved));

                strictStart = strictStart.plusHours(1);
            }

            currDate = currDate.plusDays(1);
        }
        return slotResponseList;
    }

    private boolean isWeekends(LocalDate date){
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
