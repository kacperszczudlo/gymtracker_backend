package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;

public class ExerciseExtremesDto {
    private BigDecimal initialWeight;
    private BigDecimal latestWeight;

    public ExerciseExtremesDto() {}

    public ExerciseExtremesDto(BigDecimal initialWeight, BigDecimal latestWeight) {
        this.initialWeight = initialWeight;
        this.latestWeight = latestWeight;
    }

    // Gettery i Settery
    public BigDecimal getInitialWeight() { return initialWeight; }
    public void setInitialWeight(BigDecimal initialWeight) { this.initialWeight = initialWeight; }
    public BigDecimal getLatestWeight() { return latestWeight; }
    public void setLatestWeight(BigDecimal latestWeight) { this.latestWeight = latestWeight; }
}