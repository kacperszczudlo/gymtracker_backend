package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseExtremesDto; // DODAJ
import pl.gymtracker.gymtrackerbackend.dto.ExerciseProgressDto;
import pl.gymtracker.gymtrackerbackend.service.AchievementService;

import java.math.BigDecimal;
import java.util.Collections; // DODAJ
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/{userId}/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    // ... (istniejące endpointy getMaxWeights i getExerciseProgress) ...
    @GetMapping("/max-weights")
    public ResponseEntity<Map<String, BigDecimal>> getMaxWeights(
            @PathVariable Integer userId,
            @RequestParam List<String> exerciseNames) {
        Map<String, BigDecimal> maxWeights = achievementService.getMaxWeightsForExercises(userId, exerciseNames);
        return ResponseEntity.ok(maxWeights);
    }

    @GetMapping("/exercise-progress/{exerciseName}")
    public ResponseEntity<List<ExerciseProgressDto>> getExerciseProgress(
            @PathVariable Integer userId,
            @PathVariable String exerciseName) {
        List<ExerciseProgressDto> progressHistory = achievementService.getExerciseProgressHistory(userId, exerciseName);
        if (progressHistory == null || progressHistory.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(progressHistory);
    }


    // Nowy endpoint dla początkowej i końcowej wagi ćwiczenia
    @GetMapping("/exercise-extremes/{exerciseName}")
    public ResponseEntity<ExerciseExtremesDto> getExerciseExtremes(
            @PathVariable Integer userId,
            @PathVariable String exerciseName) {
        ExerciseExtremesDto extremes = achievementService.getExerciseExtremes(userId, exerciseName);
        // Nawet jeśli wartości są null, DTO zostanie zwrócone (np. {"initialWeight":null, "latestWeight":null})
        return ResponseEntity.ok(extremes);
    }
}