package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gymtracker.gymtrackerbackend.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    // Możesz dodać metody pomocnicze, jeśli konieczne
}
