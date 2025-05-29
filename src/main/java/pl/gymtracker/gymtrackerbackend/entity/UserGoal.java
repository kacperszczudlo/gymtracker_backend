package pl.gymtracker.gymtrackerbackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime; // Jeśli używasz BaseEntity z tymi polami

@Entity
@Table(name = "user_goals", schema = "gymtracker2")
public class UserGoal extends BaseEntity { // Zakładając, że BaseEntity ma createdAt, updatedAt

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_goals_id_seq_gen")
    @SequenceGenerator(name = "user_goals_id_seq_gen", sequenceName = "gymtracker2.user_goals_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "target_weight")
    private BigDecimal targetWeight;

    @Column(name = "start_weight")
    private BigDecimal startWeight;

    @Column(name = "target_training_days")
    private Integer targetTrainingDays;

    public UserGoal() {}

    // Gettery i Settery
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public BigDecimal getTargetWeight() { return targetWeight; }
    public void setTargetWeight(BigDecimal targetWeight) { this.targetWeight = targetWeight; }
    public BigDecimal getStartWeight() { return startWeight; }
    public void setStartWeight(BigDecimal startWeight) { this.startWeight = startWeight; }
    public Integer getTargetTrainingDays() { return targetTrainingDays; }
    public void setTargetTrainingDays(Integer targetTrainingDays) { this.targetTrainingDays = targetTrainingDays; }
}