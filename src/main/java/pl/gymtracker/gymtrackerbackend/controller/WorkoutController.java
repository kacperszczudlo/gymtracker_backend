package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.WorkoutDto;
import pl.gymtracker.gymtrackerbackend.service.WorkoutService;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    // Endpoint do zapisu treningu wraz z ćwiczeniami
    @PostMapping
    public ResponseEntity<WorkoutDto> saveWorkout(
            @PathVariable Integer userId,
            @RequestBody WorkoutDto workoutDto) {
        WorkoutDto savedWorkout = workoutService.saveWorkout(userId, workoutDto);
        return ResponseEntity.ok(savedWorkout);
    }

    // Endpoint do pobrania treningów danego użytkownika
    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getWorkoutsForUser(
            @PathVariable Integer userId,
            @RequestParam(value = "day", required = false) String day) {
        List<WorkoutDto> workouts = workoutService.getWorkoutsForUserAndDay(userId, day);
        return ResponseEntity.ok(workouts);
    }
}
