package mg.itu.repository;

import java.util.Optional;
import mg.itu.model.FlightPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightPriceRepository extends JpaRepository<FlightPrice, Integer> {
    Optional<FlightPrice> findByFlightIdAndCategory(Integer flightId, String category);
}