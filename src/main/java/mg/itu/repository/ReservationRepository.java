package mg.itu.repository;

import mg.itu.model.Reservation;
import mg.itu.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByClient(Client client);
}