package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.stereotype.Service;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseRequest;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseResponse;
import pl.gymtracker.gymtrackerbackend.entity.Exercise;
import pl.gymtracker.gymtrackerbackend.repository.ExerciseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(ex -> new ExerciseResponse(ex.getId(), ex.getName()))
                .collect(Collectors.toList());
    }

    public ExerciseResponse addExercise(ExerciseRequest request) {
        Exercise exercise = new Exercise(request.getName());
        exerciseRepository.save(exercise);
        return new ExerciseResponse(exercise.getId(), exercise.getName());
    }

    public ExerciseResponse updateExercise(Integer id, ExerciseRequest request) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        exercise.setName(request.getName());
        exerciseRepository.save(exercise);
        return new ExerciseResponse(exercise.getId(), exercise.getName());
    }

    public void deleteExercise(Integer id) {
        exerciseRepository.deleteById(id);
    }
}
