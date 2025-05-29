package pl.gymtracker.gymtrackerbackend.service;

import pl.gymtracker.gymtrackerbackend.repository.LogExerciseRepository;
import org.springframework.stereotype.Service;

@Service
public class LogExerciseService {

    private final LogExerciseRepository logExerciseRepository;

    public LogExerciseService(LogExerciseRepository logExerciseRepository) {
        this.logExerciseRepository = logExerciseRepository;
    }

    public void deleteLogExercise(Integer id) {
        logExerciseRepository.deleteById(id);
    }
}
