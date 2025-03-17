package mg.itu.repository;

import mg.itu.model.PercentageDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PercentageDiscountRepository extends JpaRepository<PercentageDiscount, Integer> {
    @Query("SELECT pd FROM PercentageDiscount pd WHERE :age <= pd.ageMax ORDER BY pd.ageMax ASC LIMIT 1")
    Optional<PercentageDiscount> findByAgeMax(@Param("age") int age);
}