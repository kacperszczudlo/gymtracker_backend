package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseExtremesDto; // DODAJ
import pl.gymtracker.gymtrackerbackend.dto.ExerciseProgressDto;
import pl.gymtracker.gymtrackerbackend.repository.LogSeriesRepository;
import pl.gymtracker.gymtrackerbackend.repository.LogSeriesRepository.ExerciseProgressProjection;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional; // DODAJ
import java.util.stream.Collectors;

@Service
public class AchievementService {

    private final LogSeriesRepository logSeriesRepository;
    private final UserRepository userRepository;

    public AchievementService(LogSeriesRepository logSeriesRepository, UserRepository userRepository) {
        this.logSeriesRepository = logSeriesRepository;
        this.userRepository = userRepository;
    }

    // ... (istniejące metody getMaxWeightsForExercises i getExerciseProgressHistory) ...

    @Transactional(readOnly = true)
    public ExerciseExtremesDto getExerciseExtremes(Integer userId, String exerciseName) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik o ID " + userId + " nie istnieje."));

        Optional<BigDecimal> initialWeightOpt = logSeriesRepository.findInitialMaxWeightForExercise(userId, exerciseName);
        Optional<BigDecimal> latestWeightOpt = logSeriesRepository.findLatestMaxWeightForExercise(userId, exerciseName);

        return new ExerciseExtremesDto(
                initialWeightOpt.orElse(null), // Zwróć null, jeśli nie ma danych
                latestWeightOpt.orElse(null)   // Zwróć null, jeśli nie ma danych
        );
    }

    // Istniejące metody
    public Map<String, BigDecimal> getMaxWeightsForExercises(Integer userId, List<String> exerciseNames) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik o ID " + userId + " nie istnieje"));

        Map<String, BigDecimal> maxWeightsMap = new HashMap<>();
        for (String exerciseName : exerciseNames) {
            BigDecimal maxWeight = logSeriesRepository.findMaxWeightByUserIdAndExerciseName(userId, exerciseName)
                    .orElse(BigDecimal.ZERO);
            maxWeightsMap.put(exerciseName, maxWeight);
        }
        return maxWeightsMap;
    }

    @Transactional(readOnly = true)
    public List<ExerciseProgressDto> getExerciseProgressHistory(Integer userId, String exerciseName) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik o ID " + userId + " nie istnieje"));

        List<ExerciseProgressProjection> projections = logSeriesRepository.findExerciseProgressByUserIdAndExerciseName(userId, exerciseName);

        return projections.stream()
                .map(proj -> {
                    BigDecimal maxWeightBigDecimal = proj.getMaxWeight() != null ? BigDecimal.valueOf(proj.getMaxWeight()) : null;
                    Date sqlDate = proj.getDate() != null ? Date.valueOf(proj.getDate()) : null;
                    return new ExerciseProgressDto(sqlDate, maxWeightBigDecimal);
                })
                .collect(Collectors.toList());
    }
}