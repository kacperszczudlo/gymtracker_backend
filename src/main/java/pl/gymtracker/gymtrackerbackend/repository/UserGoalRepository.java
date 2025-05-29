package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gymtracker.gymtrackerbackend.entity.User;
import pl.gymtracker.gymtrackerbackend.entity.UserGoal;
import java.util.Optional;

public interface UserGoalRepository extends JpaRepository<UserGoal, Integer> {
    Optional<UserGoal> findByUser(User user);
    Optional<UserGoal> findByUserId(Integer userId);
}