package pl.gymtracker.gymtrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_log", schema = "gymtracker2", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "date"})
})
public class TrainingLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "day_name", nullable = false)
    private String dayName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "trainingLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LogExercise> exercises = new ArrayList<>();

    // Konstruktor bezparametrowy
    public TrainingLog() {}

    // Gettery i settery (pełne!)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LogExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<LogExercise> exercises) {
        this.exercises = exercises;
    }
}
