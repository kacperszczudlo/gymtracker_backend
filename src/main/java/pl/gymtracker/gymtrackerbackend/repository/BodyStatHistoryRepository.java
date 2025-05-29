package pl.gymtracker.gymtrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gymtracker.gymtrackerbackend.entity.BodyStatHistory;
import pl.gymtracker.gymtrackerbackend.entity.User;
import java.sql.Date;
import java.util.List;

public interface BodyStatHistoryRepository extends JpaRepository<BodyStatHistory, Integer> {
    List<BodyStatHistory> findByUserOrderByDateAsc(User user);
    List<BodyStatHistory> findByUserOrderByDateDesc(User user);
    BodyStatHistory findFirstByUserOrderByDateAsc(User user);
}