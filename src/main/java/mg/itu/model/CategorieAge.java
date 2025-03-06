package mg.itu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_age")
public class CategorieAge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String label;

    // Constructors
    public CategorieAge() {}
    
    public CategorieAge(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}