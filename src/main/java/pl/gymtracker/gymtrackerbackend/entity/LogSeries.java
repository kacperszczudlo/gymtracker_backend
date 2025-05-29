package pl.gymtracker.gymtrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "log_series", schema = "gymtracker2")
public class LogSeries extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer reps;

    // Kluczowa zmiana: Double i columnDefinition
    @Column(columnDefinition = "NUMERIC(5,2)")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "log_exercise_id", nullable = false)
    @JsonBackReference
    private LogExercise logExercise;

    // Konstruktor bezparametrowy
    public LogSeries() {}

    // Gettery i settery
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LogExercise getLogExercise() {
        return logExercise;
    }

    public void setLogExercise(LogExercise logExercise) {
        this.logExercise = logExercise;
    }
}
