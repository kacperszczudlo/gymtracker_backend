package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gymtracker.gymtrackerbackend.dto.UserGoalDto;
import pl.gymtracker.gymtrackerbackend.dto.UserGoalUpdateRequestDto;
import pl.gymtracker.gymtrackerbackend.entity.BodyStatHistory;
import pl.gymtracker.gymtrackerbackend.entity.User;
import pl.gymtracker.gymtrackerbackend.entity.UserGoal;
import pl.gymtracker.gymtrackerbackend.repository.BodyStatHistoryRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserGoalRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class UserGoalService {

    private final UserGoalRepository userGoalRepository;
    private final UserRepository userRepository;
    private final BodyStatHistoryRepository bodyStatHistoryRepository;

    public UserGoalService(UserGoalRepository userGoalRepository, UserRepository userRepository, BodyStatHistoryRepository bodyStatHistoryRepository) {
        this.userGoalRepository = userGoalRepository;
        this.userRepository = userRepository;
        this.bodyStatHistoryRepository = bodyStatHistoryRepository;
    }

    @Transactional(readOnly = true)
    public UserGoalDto getUserGoals(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        return userGoalRepository.findByUser(user)
                .map(goal -> new UserGoalDto(goal.getTargetWeight(), goal.getStartWeight(), goal.getTargetTrainingDays()))
                .orElse(null); // Zwróć null, jeśli użytkownik nie ma jeszcze celów
    }

    @Transactional
    public UserGoalDto saveOrUpdateUserGoals(Integer userId, UserGoalUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        UserGoal userGoal = userGoalRepository.findByUser(user)
                .orElseGet(() -> {
                    UserGoal newGoal = new UserGoal();
                    newGoal.setUser(user);
                    return newGoal;
                });

        // Obsługa celu wagowego
        if (requestDto.getTargetWeight() != null) {
            userGoal.setTargetWeight(requestDto.getTargetWeight());
            // Jeśli startWeight nie jest jeszcze ustawiony (np. pierwszy raz ustawiany jest cel wagowy)
            // lub jeśli jest to nowy obiekt UserGoal, ustawiamy startWeight.
            if (userGoal.getStartWeight() == null) {
                // Pobierz najnowszy wpis wagi z body_stat_history jako wagę początkową
                List<BodyStatHistory> history = bodyStatHistoryRepository.findByUserOrderByDateDesc(user);
                if (!history.isEmpty() && history.get(0).getWeight() != null) {
                    userGoal.setStartWeight(history.get(0).getWeight());
                } else {
                    // Jeśli nie ma historii wagi, a użytkownik ustawia cel,
                    // można ustawić startWeight na targetWeight, lub poczekać, aż doda pomiar.
                    // Dla uproszczenia, jeśli nie ma historii, ustawiamy startWeight na targetWeight.
                    // To może wymagać dopracowania logiki biznesowej.
                    userGoal.setStartWeight(requestDto.getTargetWeight());
                }
            }
        } else { // Jeśli użytkownik usuwa cel wagowy (wysyła null)
            userGoal.setTargetWeight(null);
            userGoal.setStartWeight(null); // Usuwamy też wagę startową
        }

        // Obsługa celu dni treningowych
        if (requestDto.getTargetTrainingDays() != null) {
            userGoal.setTargetTrainingDays(requestDto.getTargetTrainingDays());
        } else { // Jeśli użytkownik usuwa cel dni (wysyła null)
            userGoal.setTargetTrainingDays(null); // Można też ustawić domyślną wartość, np. 3
        }

        UserGoal savedGoal = userGoalRepository.save(userGoal);
        return new UserGoalDto(savedGoal.getTargetWeight(), savedGoal.getStartWeight(), savedGoal.getTargetTrainingDays());
    }
}