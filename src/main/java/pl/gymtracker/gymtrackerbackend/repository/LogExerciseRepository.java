package pl.gymtracker.gymtrackerbackend.repository;

import pl.gymtracker.gymtrackerbackend.entity.LogExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogExerciseRepository extends JpaRepository<LogExercise, Integer> {
}
