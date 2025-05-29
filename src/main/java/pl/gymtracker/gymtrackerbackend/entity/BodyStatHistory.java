package pl.gymtracker.gymtrackerbackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date; // Używamy java.sql.Date dla zgodności z typem DATE w PostgreSQL

@Entity
@Table(name = "body_stat_history", schema = "gymtracker2")
public class BodyStatHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "body_stat_history_id_seq_gen")
    @SequenceGenerator(
            name = "body_stat_history_id_seq_gen",
            sequenceName = "gymtracker2.body_stat_history_id_seq", // Pełna nazwa sekwencji ze schematem
            allocationSize = 1
    )
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) // LAZY, bo User może nie być od razu potrzebny
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Date date;

    private BigDecimal weight;

    @Column(name = "arm_circumference")
    private BigDecimal armCircumference;

    @Column(name = "waist_circumference")
    private BigDecimal waistCircumference;

    @Column(name = "hip_circumference")
    private BigDecimal hipCircumference;

    // Konstruktory
    public BodyStatHistory() {}

    // Gettery i Settery
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public BigDecimal getArmCircumference() { return armCircumference; }
    public void setArmCircumference(BigDecimal armCircumference) { this.armCircumference = armCircumference; }
    public BigDecimal getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(BigDecimal waistCircumference) { this.waistCircumference = waistCircumference; }
    public BigDecimal getHipCircumference() { return hipCircumference; }
    public void setHipCircumference(BigDecimal hipCircumference) { this.hipCircumference = hipCircumference; }
}