package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;

public class BodyStatDto {
    private BigDecimal weight;
    private BigDecimal waistCircumference;
    private BigDecimal armCircumference;
    private BigDecimal hipCircumference;

    // Gettery i settery
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(BigDecimal waistCircumference) { this.waistCircumference = waistCircumference; }

    public BigDecimal getArmCircumference() { return armCircumference; }
    public void setArmCircumference(BigDecimal armCircumference) { this.armCircumference = armCircumference; }

    public BigDecimal getHipCircumference() { return hipCircumference; }
    public void setHipCircumference(BigDecimal hipCircumference) { this.hipCircumference = hipCircumference; }
}
