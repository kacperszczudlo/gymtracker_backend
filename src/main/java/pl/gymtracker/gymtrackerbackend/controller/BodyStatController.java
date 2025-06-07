package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.BodyStatDto;
import pl.gymtracker.gymtrackerbackend.model.BodyStat;
import pl.gymtracker.gymtrackerbackend.service.BodyStatService;

@RestController
@RequestMapping("/api/users")
public class BodyStatController {

    @Autowired
    private BodyStatService bodyStatService;

    @PutMapping("/{userId}/bodystat")
    public ResponseEntity<BodyStat> updateBodyStat(@PathVariable Integer userId, @RequestBody BodyStatDto dto) {
        BodyStat bodyStat = bodyStatService.updateBodyStat(userId, dto);
        return ResponseEntity.ok(bodyStat);
    }
}
