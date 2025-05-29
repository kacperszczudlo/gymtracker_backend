package pl.gymtracker.gymtrackerbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exercises", schema = "gymtracker2")
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Konstruktor bezparametrowy
    public Exercise() {}

    // Konstruktor z parametrem
    public Exercise(String name) {
        this.name = name;
    }

    // Gettery i settery (pełne!)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
