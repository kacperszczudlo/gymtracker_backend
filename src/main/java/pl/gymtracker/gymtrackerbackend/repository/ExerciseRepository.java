package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gymtracker.gymtrackerbackend.entity.Exercise;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Optional<Exercise> findByName(String name);

}