package mg.itu.repository;

import java.util.Optional;

import mg.itu.model.FlightPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightPromotionRepository extends JpaRepository<FlightPromotion, Integer> {
    Optional<FlightPromotion> findByFlightIdAndCategory(Integer flightId, String category);
}