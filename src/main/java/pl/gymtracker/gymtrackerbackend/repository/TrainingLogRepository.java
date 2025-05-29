package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gymtracker.gymtrackerbackend.entity.TrainingLog;
import java.time.LocalDate; // Upewnij się, że to jest LocalDate
import java.util.List;
import java.util.Optional;

public interface TrainingLogRepository extends JpaRepository<TrainingLog, Integer> {

    // Metoda do znajdowania konkretnego logu na podstawie ID użytkownika, daty i nazwy dnia
    // Spring Data JPA powinno zrozumieć tę nazwę i wygenerować odpowiednie zapytanie.
    // Upewnij się, że pola w encji TrainingLog to:
    // User user; (gdzie User ma pole id)
    // LocalDate date;
    // String dayName;
    Optional<TrainingLog> findByUserIdAndDateAndDayName(Integer userId, LocalDate date, String dayName);

    // Metoda do pobierania logów w zakresie dat (dla getActiveTrainingDaysInCurrentWeek)
    List<TrainingLog> findAllByUserIdAndDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);

    // existsById jest dostarczane przez JpaRepository, więc nie trzeba go definiować
}