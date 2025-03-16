package mg.itu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "reservations_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
    
    @Column(name = "seat_category", nullable = false)
    private String seatCategory;
    
    @Column(name = "name_voyageur", nullable = false)
    private String passengerName;
    
    @Column(name = "dtn_voyageur", nullable = false)
    private LocalDate passengerBirthDate;
    
    @Column(name = "passport_image", nullable = false)
    private String passportImage;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Column(name = "is_promotional", nullable = false)
    private boolean promotional;
    
    @Column(name = "is_cancel", nullable = false)
    private boolean cancelled;
    
    @Column(name = "cancellation_time")
    private ZonedDateTime cancellationTime;
}