package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gymtracker.gymtrackerbackend.model.Workout;
import pl.gymtracker.gymtrackerbackend.model.User;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    // Metoda zwracająca treningi dla danego użytkownika. Opcjonalnie filtruj po nazwie (jeśli przechowujesz dzień w nazwie)
    List<Workout> findByUser(User user);

    // Jeśli chcesz filtracja po dniu treningowym (przyjmijmy, że przechowujesz go w polu name)
    List<Workout> findByUserAndName(User user, String name);
}
