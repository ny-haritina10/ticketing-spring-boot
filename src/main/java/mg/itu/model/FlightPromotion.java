package mg.itu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "flight_promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_flight", referencedColumnName = "id")
    private Flight flight;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @Column(nullable = false)
    private Integer seatsAvailable;
}
