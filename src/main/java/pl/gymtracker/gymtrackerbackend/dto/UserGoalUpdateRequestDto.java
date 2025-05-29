package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;

public class UserGoalUpdateRequestDto {
    private BigDecimal targetWeight;
    private Integer targetTrainingDays;

    // Gettery i Settery
    public BigDecimal getTargetWeight() { return targetWeight; }
    public void setTargetWeight(BigDecimal targetWeight) { this.targetWeight = targetWeight; }
    public Integer getTargetTrainingDays() { return targetTrainingDays; }
    public void setTargetTrainingDays(Integer targetTrainingDays) { this.targetTrainingDays = targetTrainingDays; }
}