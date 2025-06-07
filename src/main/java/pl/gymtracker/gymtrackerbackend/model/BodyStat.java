package pl.gymtracker.gymtrackerbackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "body_stat", schema = "gym")
public class BodyStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Jeden użytkownik – jeden rekord BodyStat.
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Używamy BigDecimal dla precyzyjnych wartości z domeny waga_kg itp.
    @Column(nullable = false)
    private BigDecimal weight;

    @Column(name = "waist_circumference")
    private BigDecimal waistCircumference;

    @Column(name = "arm_circumference")
    private BigDecimal armCircumference;

    @Column(name = "hip_circumference")
    private BigDecimal hipCircumference;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Gettery i settery...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(BigDecimal waistCircumference) { this.waistCircumference = waistCircumference; }

    public BigDecimal getArmCircumference() { return armCircumference; }
    public void setArmCircumference(BigDecimal armCircumference) { this.armCircumference = armCircumference; }

    public BigDecimal getHipCircumference() { return hipCircumference; }
    public void setHipCircumference(BigDecimal hipCircumference) { this.hipCircumference = hipCircumference; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
