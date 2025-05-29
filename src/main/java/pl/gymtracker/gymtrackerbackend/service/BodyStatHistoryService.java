package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gymtracker.gymtrackerbackend.dto.BodyStatHistoryDto;
import pl.gymtracker.gymtrackerbackend.entity.BodyStatHistory;
import pl.gymtracker.gymtrackerbackend.entity.User;
import pl.gymtracker.gymtrackerbackend.repository.BodyStatHistoryRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BodyStatHistoryService {

    private final BodyStatHistoryRepository bodyStatHistoryRepository;
    private final UserRepository userRepository;

    public BodyStatHistoryService(BodyStatHistoryRepository bodyStatHistoryRepository, UserRepository userRepository) {
        this.bodyStatHistoryRepository = bodyStatHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true) // Transakcja tylko do odczytu
    public List<BodyStatHistoryDto> getBodyStatHistoryForUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return bodyStatHistoryRepository.findByUserOrderByDateAsc(user).stream()
                .map(history -> new BodyStatHistoryDto(
                        history.getId(),
                        history.getDate(),
                        history.getWeight(),
                        history.getArmCircumference(),
                        history.getWaistCircumference(),
                        history.getHipCircumference()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // Transakcja tylko do odczytu
    public BodyStatHistoryDto getInitialBodyStatForUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        BodyStatHistory initialStat = bodyStatHistoryRepository.findFirstByUserOrderByDateAsc(user); // Pobierz pierwszy wg daty
        if (initialStat == null) {
            return null; // Lub rzuć wyjątek jeśli biznesowo to błąd
        }
        return new BodyStatHistoryDto(
                initialStat.getId(),
                initialStat.getDate(),
                initialStat.getWeight(),
                initialStat.getArmCircumference(),
                initialStat.getWaistCircumference(),
                initialStat.getHipCircumference()
        );
    }
}