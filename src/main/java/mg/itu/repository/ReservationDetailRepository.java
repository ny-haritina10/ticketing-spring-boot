package mg.itu.repository;

import mg.itu.model.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Integer> {
}
