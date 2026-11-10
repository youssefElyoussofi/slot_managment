package backend.slot_managment.repositories;

import backend.slot_managment.dtos.SlotResponse;
import backend.slot_managment.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {

    public boolean existsByStartsAt(Instant startsAt);

    public List<Slot> findAllBy();
}
