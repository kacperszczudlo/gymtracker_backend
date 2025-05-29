package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;
import java.sql.Date; // Używamy java.sql.Date dla bezpośredniego mapowania z wyniku zapytania

public class ExerciseProgressDto {
    private Date date;
    private BigDecimal maxWeight;

    public ExerciseProgressDto() {}

    public ExerciseProgressDto(Date date, BigDecimal maxWeight) {
        this.date = date;
        this.maxWeight = maxWeight;
    }

    // Gettery i Settery
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public BigDecimal getMaxWeight() { return maxWeight; }
    public void setMaxWeight(BigDecimal maxWeight) { this.maxWeight = maxWeight; }
}