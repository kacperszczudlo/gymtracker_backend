package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.BodyStatHistoryDto;
import pl.gymtracker.gymtrackerbackend.service.BodyStatHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/body-stats") // Poprawiony mapping bazowy
public class BodyStatHistoryController {

    private final BodyStatHistoryService bodyStatHistoryService;

    public BodyStatHistoryController(BodyStatHistoryService bodyStatHistoryService) {
        this.bodyStatHistoryService = bodyStatHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<BodyStatHistoryDto>> getBodyStatHistory(@PathVariable Integer userId) {
        List<BodyStatHistoryDto> history = bodyStatHistoryService.getBodyStatHistoryForUser(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/initial")
    public ResponseEntity<BodyStatHistoryDto> getInitialBodyStat(@PathVariable Integer userId) {
        BodyStatHistoryDto initialStat = bodyStatHistoryService.getInitialBodyStatForUser(userId);
        if (initialStat == null) {
            return ResponseEntity.ok(null); // Zwróć 200 OK z pustym ciałem, jeśli brak danych jest oczekiwany
            // return ResponseEntity.notFound().build(); // Lub 404, jeśli to błąd
        }
        return ResponseEntity.ok(initialStat);
    }
}