package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.UserGoalDto;
import pl.gymtracker.gymtrackerbackend.dto.UserGoalUpdateRequestDto;
import pl.gymtracker.gymtrackerbackend.service.UserGoalService;
import pl.gymtracker.gymtrackerbackend.service.TrainingLogService; // Potrzebny do aktualnych dni

@RestController
@RequestMapping("/api/v1/users/{userId}") // Zmieniony bazowy mapping dla lepszej organizacji
public class UserGoalController {

    private final UserGoalService userGoalService;
    private final TrainingLogService trainingLogService; // Dodajemy

    public UserGoalController(UserGoalService userGoalService, TrainingLogService trainingLogService) {
        this.userGoalService = userGoalService;
        this.trainingLogService = trainingLogService;
    }

    @GetMapping("/goals")
    public ResponseEntity<UserGoalDto> getUserGoals(@PathVariable Integer userId) {
        UserGoalDto goals = userGoalService.getUserGoals(userId);
        if (goals == null) {
            // Zwracamy DTO z wartościami null lub domyślnymi, aby frontend wiedział, że nie ma celów
            return ResponseEntity.ok(new UserGoalDto(null, null, null)); // Lub 3 dla dni jako domyślne
        }
        return ResponseEntity.ok(goals);
    }

    @PutMapping("/goals") // Używamy PUT do aktualizacji/tworzenia zasobu goals
    public ResponseEntity<UserGoalDto> saveOrUpdateUserGoals(@PathVariable Integer userId, @RequestBody UserGoalUpdateRequestDto requestDto) {
        UserGoalDto updatedGoals = userGoalService.saveOrUpdateUserGoals(userId, requestDto);
        return ResponseEntity.ok(updatedGoals);
    }

    // Endpoint do pobierania aktualnej liczby aktywnych dni treningowych
    @GetMapping("/training-logs/active-days-current-week")
    public ResponseEntity<Integer> getActiveTrainingDaysInCurrentWeek(@PathVariable Integer userId) {
        int activeDays = trainingLogService.getActiveTrainingDaysInCurrentWeek(userId);
        return ResponseEntity.ok(activeDays);
    }
}