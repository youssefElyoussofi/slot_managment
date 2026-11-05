package backend.slot_managment.controllers;

import backend.slot_managment.dtos.GenerateSlotsRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlotController {



    @PostMapping("/slots/generate")
    public void generateSlots(@Valid @RequestBody GenerateSlotsRequest request){

    }
}
