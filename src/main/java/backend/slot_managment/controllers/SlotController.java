package backend.slot_managment.controllers;

import backend.slot_managment.dtos.GenerateSlotsRequest;
import backend.slot_managment.dtos.SlotResponse;
import backend.slot_managment.services.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SlotController {
    final SlotService slotService;

    @PostMapping("/slots/generate")
    public ResponseEntity<List<SlotResponse>> generateSlots(@Valid @RequestBody GenerateSlotsRequest request){
        return ResponseEntity.ok(slotService.createSlot(request));
    }
}
