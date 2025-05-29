package pl.gymtracker.gymtrackerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "log_exercise", schema = "gymtracker2")
public class LogExercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @ManyToOne
    @JoinColumn(name = "log_id", nullable = false)
    @JsonBackReference // Dodaj tę adnotację
    private TrainingLog trainingLog;

    @OneToMany(mappedBy = "logExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LogSeries> seriesList = new ArrayList<>();

    // Konstruktor bezparametrowy
    public LogExercise() {}

    // Gettery i settery
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public TrainingLog getTrainingLog() {
        return trainingLog;
    }

    public void setTrainingLog(TrainingLog trainingLog) {
        this.trainingLog = trainingLog;
    }

    public List<LogSeries> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<LogSeries> seriesList) {
        this.seriesList = seriesList;
    }

    // Dodatkowe metody pomocnicze do zarządzania relacjami (opcjonalnie, ale bardzo polecane):

    public void addSeries(LogSeries series) {
        seriesList.add(series);
        series.setLogExercise(this);
    }

    public void removeSeries(LogSeries series) {
        seriesList.remove(series);
        series.setLogExercise(null);
    }
}
