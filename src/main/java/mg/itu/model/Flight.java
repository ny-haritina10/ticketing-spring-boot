package mg.itu.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "flight_number", nullable = false, unique = true)
    private String flightNumber;
    
    @Column(name = "departure_time", nullable = false)
    private ZonedDateTime departureTime;
    
    @Column(name = "arrival_time", nullable = false)
    private ZonedDateTime arrivalTime;
}