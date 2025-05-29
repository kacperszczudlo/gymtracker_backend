package pl.gymtracker.gymtrackerbackend.controller;

import pl.gymtracker.gymtrackerbackend.dto.TrainingLogRequest;
import pl.gymtracker.gymtrackerbackend.entity.TrainingLog;
import pl.gymtracker.gymtrackerbackend.service.TrainingLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/training-logs")
public class TrainingLogController {

    private final TrainingLogService trainingLogService;

    public TrainingLogController(TrainingLogService trainingLogService) {
        this.trainingLogService = trainingLogService;
    }

    @PostMapping
    public void saveTrainingLog(@RequestBody TrainingLogRequest request) {
        trainingLogService.saveTrainingLog(request);
    }

    @GetMapping
    public ResponseEntity<TrainingLog> getTrainingLog(@RequestParam Integer userId, @RequestParam String date, @RequestParam String dayName) {
        TrainingLog log = trainingLogService.getTrainingLog(userId, date, dayName);
        if (log == null) {
            // 🟢 Jeśli nie ma logu, zwróć pustą odpowiedź 200 OK (bez błędu)
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok(log);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainingLog(@PathVariable Integer id) {
        trainingLogService.deleteTrainingLog(id);
    }
}
