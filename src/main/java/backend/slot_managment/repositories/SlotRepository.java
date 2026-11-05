package backend.slot_managment.repositories;

import backend.slot_managment.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {

    public boolean existsByStartsAt(Instant startsAt);
}
