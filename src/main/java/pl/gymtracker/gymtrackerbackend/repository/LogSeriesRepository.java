package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.gymtracker.gymtrackerbackend.entity.LogSeries;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LogSeriesRepository extends JpaRepository<LogSeries, Integer> {

    // Istniejąca metoda (najnowszy max globalny)
    @Query("SELECT MAX(ls.weight) FROM LogSeries ls " +
            "JOIN ls.logExercise le " +
            "JOIN le.trainingLog tl " +
            "WHERE tl.user.id = :userId AND le.exerciseName = :exerciseName")
    Optional<BigDecimal> findMaxWeightByUserIdAndExerciseName(@Param("userId") Integer userId, @Param("exerciseName") String exerciseName);

    // Istniejąca projekcja dla historii
    interface ExerciseProgressProjection {
        LocalDate getDate();
        Double getMaxWeight();
    }

    @Query("SELECT tl.date AS date, MAX(ls.weight) AS maxWeight " +
            "FROM LogSeries ls " +
            "JOIN ls.logExercise le " +
            "JOIN le.trainingLog tl " +
            "WHERE tl.user.id = :userId AND LOWER(le.exerciseName) = LOWER(:exerciseName) AND ls.weight > 0 " +
            "GROUP BY tl.date " +
            "ORDER BY tl.date ASC")
    List<ExerciseProgressProjection> findExerciseProgressByUserIdAndExerciseName(
            @Param("userId") Integer userId,
            @Param("exerciseName") String exerciseName
    );

    // Nowa metoda: Pobranie maksymalnej wagi z pierwszego dnia treningowego dla danego ćwiczenia
    @Query(value = "SELECT ls.weight FROM gymtracker2.log_series ls " +
            "JOIN gymtracker2.log_exercise le ON ls.log_exercise_id = le.id " +
            "JOIN gymtracker2.training_log tl ON le.log_id = tl.id " +
            "WHERE tl.user_id = :userId AND le.exercise_name = :exerciseName AND ls.weight > 0 " +
            "ORDER BY tl.date ASC, ls.weight DESC " + // Sortuj po dacie, potem po wadze malejąco
            "LIMIT 1", nativeQuery = true)
    Optional<BigDecimal> findInitialMaxWeightForExercise(@Param("userId") Integer userId, @Param("exerciseName") String exerciseName);

    // Nowa metoda: Pobranie maksymalnej wagi z ostatniego dnia treningowego dla danego ćwiczenia
    @Query(value = "SELECT ls.weight FROM gymtracker2.log_series ls " +
            "JOIN gymtracker2.log_exercise le ON ls.log_exercise_id = le.id " +
            "JOIN gymtracker2.training_log tl ON le.log_id = tl.id " +
            "WHERE tl.user_id = :userId AND le.exercise_name = :exerciseName AND ls.weight > 0 " +
            "ORDER BY tl.date DESC, ls.weight DESC " + // Sortuj po dacie malejąco, potem po wadze malejąco
            "LIMIT 1", nativeQuery = true)
    Optional<BigDecimal> findLatestMaxWeightForExercise(@Param("userId") Integer userId, @Param("exerciseName") String exerciseName);
}