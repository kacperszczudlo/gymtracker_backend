package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;

public class UserGoalDto {
    private BigDecimal targetWeight;
    private BigDecimal startWeight;
    private Integer targetTrainingDays;

    public UserGoalDto() {} // Potrzebny dla deserializacji

    public UserGoalDto(BigDecimal targetWeight, BigDecimal startWeight, Integer targetTrainingDays) {
        this.targetWeight = targetWeight;
        this.startWeight = startWeight;
        this.targetTrainingDays = targetTrainingDays;
    }

    // Gettery i Settery
    public BigDecimal getTargetWeight() { return targetWeight; }
    public void setTargetWeight(BigDecimal targetWeight) { this.targetWeight = targetWeight; }
    public BigDecimal getStartWeight() { return startWeight; }
    public void setStartWeight(BigDecimal startWeight) { this.startWeight = startWeight; }
    public Integer getTargetTrainingDays() { return targetTrainingDays; }
    public void setTargetTrainingDays(Integer targetTrainingDays) { this.targetTrainingDays = targetTrainingDays; }
}