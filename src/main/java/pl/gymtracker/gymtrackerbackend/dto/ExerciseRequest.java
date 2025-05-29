package pl.gymtracker.gymtrackerbackend.dto;

public class ExerciseRequest {

    private String name;

    public ExerciseRequest() {}

    public ExerciseRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}