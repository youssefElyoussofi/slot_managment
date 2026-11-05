package backend.slot_managment.dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record GenerateSlotsRequest(@NotNull LocalDate fromDate,
                                   @NotNull LocalDate toDate,
                                   @NotNull LocalTime startsAt,
                                   @NotNull LocalTime endsAt,
                                   @NotNull @Min(value = 1) @Max(value = 100) Integer capacity,
                                   @NotNull @Min(value = 30) @Max(value = 240) Integer durationMinutes,
                                   boolean skipWeekends) {
}
