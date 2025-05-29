package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gymtracker.gymtrackerbackend.entity.Profile;
import pl.gymtracker.gymtrackerbackend.entity.User;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findFirstByUserOrderByCreatedAtDesc(User user);

}
