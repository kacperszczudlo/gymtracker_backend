package pl.gymtracker.gymtrackerbackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "profile", schema = "gymtracker2")
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_seq_gen")
    @SequenceGenerator(
            name = "profile_id_seq_gen",
            sequenceName = "gymtracker2.profile_id_seq",
            allocationSize = 1
    )
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private String gender;

    private BigDecimal height;

    private BigDecimal weight;

    @Column(name = "waist_circumference")
    private BigDecimal waistCircumference;

    @Column(name = "arm_circumference")
    private BigDecimal armCircumference;

    @Column(name = "hip_circumference")
    private BigDecimal hipCircumference;

    private java.sql.Date date; // w bazie `DATE`, nie `TIMESTAMP`!

    // Konstruktor bezargumentowy
    public Profile() {}

    // Konstruktor do inicjalizacji
    public Profile(User user,
                   String gender,
                   BigDecimal height,
                   BigDecimal weight,
                   BigDecimal waistCircumference,
                   BigDecimal armCircumference,
                   BigDecimal hipCircumference,
                   java.sql.Date date) {
        this.user = user;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.waistCircumference = waistCircumference;
        this.armCircumference = armCircumference;
        this.hipCircumference = hipCircumference;
        this.date = date;
    }

    // Gettery i settery
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(BigDecimal waistCircumference) { this.waistCircumference = waistCircumference; }

    public BigDecimal getArmCircumference() { return armCircumference; }
    public void setArmCircumference(BigDecimal armCircumference) { this.armCircumference = armCircumference; }

    public BigDecimal getHipCircumference() { return hipCircumference; }
    public void setHipCircumference(BigDecimal hipCircumference) { this.hipCircumference = hipCircumference; }

    public java.sql.Date getDate() { return date; }
    public void setDate(java.sql.Date date) { this.date = date; }
}
