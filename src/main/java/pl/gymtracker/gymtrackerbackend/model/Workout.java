package pl.gymtracker.gymtrackerbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workout", schema = "gym")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Powiązanie z użytkownikiem – trening należy do jednego użytkownika
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Nazwa treningu – może być ustawiona na nazwę dnia treningowego (np. "Poniedziałek")
    @Column(name = "name", nullable = false)
    private String name;

    // Typ treningu: siłowy, kardio, stretching (zgodnie z typem gym.typ_treningu)
    @Column(name = "type", nullable = false)
    private String type;

    // Czas trwania treningu (w minutach) – korzystamy z domeny gym.czas_min
    @Column(name = "duration")
    private Integer duration;

    // Notatki – opcjonalne
    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Gettery i settery

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
