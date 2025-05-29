package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseRequest;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseResponse;
import pl.gymtracker.gymtrackerbackend.service.ExerciseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseResponse> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @PostMapping
    public ExerciseResponse addExercise(@RequestBody ExerciseRequest request) {
        return exerciseService.addExercise(request);
    }

    @PutMapping("/{id}")
    public ExerciseResponse updateExercise(@PathVariable Integer id, @RequestBody ExerciseRequest request) {
        return exerciseService.updateExercise(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable Integer id) {
        exerciseService.deleteExercise(id);
    }
}
