package mg.itu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "percentage_discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PercentageDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_categorie_age", nullable = false)
    @NotNull(message = "Category is required")
    private Integer categorieAgeId;

    @Column(name = "age_max", nullable = false)
    @NotNull(message = "Max age is required")
    private Integer ageMax;

    @Column(name = "percentage_discount")
    private Double percentageDiscount;

    @ManyToOne
    @JoinColumn(name = "id_categorie_age", insertable = false, updatable = false)
    private CategorieAge categorieAge;
}