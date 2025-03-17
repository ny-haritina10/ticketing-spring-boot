package mg.itu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat_configurations")
public class SeatConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_plane", referencedColumnName = "id")
    private Plane plane;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private Integer numberOfSeats;
}