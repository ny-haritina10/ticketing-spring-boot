package mg.itu.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "id_flight")
    private Flight flight;
    
    @Column(name = "reservation_time")
    private ZonedDateTime reservationTime;
    
    @Column(name = "nbr_billet_total", nullable = false)
    private Integer totalTickets;
    
    @Column(name = "nbr_billet_enfant", nullable = false)
    private Integer childTickets;
    
    @Column(name = "nbr_billet_adulte", nullable = false)
    private Integer adultTickets;
    
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationDetail> details = new ArrayList<>();
        
    // Helper method to add detail
    public void addDetail(ReservationDetail detail) {
        detail.setReservation(this);
        details.add(detail);
    }
}
