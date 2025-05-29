package pl.gymtracker.gymtrackerbackend.controller;

import pl.gymtracker.gymtrackerbackend.service.LogExerciseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/log-exercises")
public class LogExerciseController {

    private final LogExerciseService logExerciseService;

    public LogExerciseController(LogExerciseService logExerciseService) {
        this.logExerciseService = logExerciseService;
    }

    @DeleteMapping("/{id}")
    public void deleteLogExercise(@PathVariable Integer id) {
        logExerciseService.deleteLogExercise(id);
    }
}
