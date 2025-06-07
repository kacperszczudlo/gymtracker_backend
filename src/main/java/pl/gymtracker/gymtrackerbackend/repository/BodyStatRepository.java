package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gymtracker.gymtrackerbackend.model.BodyStat;
import pl.gymtracker.gymtrackerbackend.model.User;

@Repository
public interface BodyStatRepository extends JpaRepository<BodyStat, Integer> {
    // Metoda wyszukująca rekord BodyStat dla danego użytkownika
    BodyStat findByUser(User user);
}
