package mg.itu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "percentage_discount")
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

    // Constructors
    public PercentageDiscount() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCategorieAgeId() { return categorieAgeId; }
    public void setCategorieAgeId(Integer categorieAgeId) { this.categorieAgeId = categorieAgeId; }
    public Integer getAgeMax() { return ageMax; }
    public void setAgeMax(Integer ageMax) { this.ageMax = ageMax; }
    public Double getPercentageDiscount() { return percentageDiscount; }
    public void setPercentageDiscount(Double percentageDiscount) { this.percentageDiscount = percentageDiscount; }
    public CategorieAge getCategorieAge() { return categorieAge; }
    public void setCategorieAge(CategorieAge categorieAge) { this.categorieAge = categorieAge; }
}