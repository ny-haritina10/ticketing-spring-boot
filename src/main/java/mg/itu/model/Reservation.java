package mg.itu.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        
    public void addDetail(ReservationDetail detail) {
        detail.setReservation(this);
        details.add(detail);
    }
}
