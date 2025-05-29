package pl.gymtracker.gymtrackerbackend.dto;

import java.util.List;

public class LogExerciseRequest {

    private String exerciseName;
    private List<LogSeriesRequest> series;

    public LogExerciseRequest() {}

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<LogSeriesRequest> getSeries() {
        return series;
    }

    public void setSeries(List<LogSeriesRequest> series) {
        this.series = series;
    }
}
