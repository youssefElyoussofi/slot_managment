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
    public List<SlotResponse> generateSlots(GenerateSlotsRequest request){
        ZoneId zoneId = ZoneId.of("Africa/Casablanca");

        if (request.toDate().compareTo(request.fromDate()) <= 0)
            throw new RuntimeException("slots dates duration cannot end before start");

        LocalTime strictStart = request.startsAt().withMinute(0).withSecond(0).withNano(0);
        LocalTime strictEnd = request.endsAt().withMinute(0).withSecond(0).withNano(0);

        if (request.endsAt().compareTo(request.startsAt()) <= 0)
            throw new RuntimeException("slots times duration cannot end before start");

        LocalDate currDate = request.fromDate();

        List<SlotResponse> slotResponseList = new ArrayList<>();


        for (LocalDate day = request.fromDate(); request.toDate().compareTo(day) >= 0; day = day.plusDays(1)){
            if (request.skipWeekends() && isWeekends(day))
                continue;
            for (LocalTime time = strictStart; strictEnd.compareTo(time) > 0;time = time.plusHours(1)){

                Instant slotStartsAt = LocalDateTime.of(day, time).atZone(zoneId).toInstant();
                Instant slotEndsAt = LocalDateTime.of(day, time.plusHours(1)).atZone(zoneId).toInstant();
                if (slotRepository.existsByStartsAt(slotStartsAt) || slotStartsAt.isBefore(Instant.now()))
                    continue;
                Slot slot = new Slot();
                slot.setStartsAt(slotStartsAt);
                slot.setEndsAt(slotEndsAt);
                slot.setCapacity(request.capacity());
                slot.setBookedCount(0);

                Slot saved = slotRepository.save(slot);
                slotResponseList.add(SlotResponse.from(saved));
            }
        }
        return slotResponseList;
    }

//    @Transactional(readOnly = true)
    public List<SlotResponse> getSlots(){
        return SlotResponse.buildList(slotRepository.findAllBy());
    }

    private boolean isWeekends(LocalDate date){
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }


}
