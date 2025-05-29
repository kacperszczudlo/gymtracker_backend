package pl.gymtracker.gymtrackerbackend.dto;

public class LogSeriesRequest {

    private Integer reps;
    private Double weight;

    public LogSeriesRequest() {}

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
