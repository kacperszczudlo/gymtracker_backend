package pl.gymtracker.gymtrackerbackend.dto;

public class ExerciseResponse {

    private Integer id;
    private String name;

    public ExerciseResponse() {}

    public ExerciseResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
