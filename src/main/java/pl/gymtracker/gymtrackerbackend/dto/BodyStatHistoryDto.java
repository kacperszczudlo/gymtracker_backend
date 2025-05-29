package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;
import java.sql.Date; // Lub String, jeśli preferujesz formatowanie daty tutaj

public class BodyStatHistoryDto {
    private Integer id;
    private Date date;
    private BigDecimal weight;
    private BigDecimal armCircumference;
    private BigDecimal waistCircumference;
    private BigDecimal hipCircumference;

    // Konstruktor pełny
    public BodyStatHistoryDto(Integer id, Date date, BigDecimal weight, BigDecimal armCircumference, BigDecimal waistCircumference, BigDecimal hipCircumference) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.armCircumference = armCircumference;
        this.waistCircumference = waistCircumference;
        this.hipCircumference = hipCircumference;
    }

    // Konstruktor pusty (dla serializacji/deserializacji)
    public BodyStatHistoryDto() {}

    // Gettery i Settery
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
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