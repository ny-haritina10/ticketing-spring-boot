package mg.itu.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String flightNumber;
    
    @ManyToOne
    @JoinColumn(name = "id_plane", referencedColumnName = "id")
    private Plane plane;
    
    @ManyToOne
    @JoinColumn(name = "id_origin_city", referencedColumnName = "id")
    private City originCity;
    
    @ManyToOne
    @JoinColumn(name = "id_destination_city", referencedColumnName = "id")
    private City destinationCity;
    
    @Column(nullable = false)
    private ZonedDateTime departureTime;
    
    @Column(nullable = false)
    private ZonedDateTime arrivalTime;
    
    @Column(nullable = false, columnDefinition = "int default 3")
    private Integer reservationDeadlineHours = 3;
    
    @Column(nullable = false, columnDefinition = "int default 24")
    private Integer cancellationDeadlineHours = 24;
}
