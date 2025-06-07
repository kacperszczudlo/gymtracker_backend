package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gymtracker.gymtrackerbackend.dto.BodyStatDto;
import pl.gymtracker.gymtrackerbackend.model.BodyStat;
import pl.gymtracker.gymtrackerbackend.model.User;
import pl.gymtracker.gymtrackerbackend.repository.BodyStatRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;

@Service
public class BodyStatService {

    @Autowired
    private BodyStatRepository bodyStatRepository;

    @Autowired
    private UserRepository userRepository;

    // Aktualizacja lub utworzenie rekordu body_stat dla danego użytkownika
    public BodyStat updateBodyStat(Integer userId, BodyStatDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        BodyStat bodyStat = bodyStatRepository.findByUser(user);
        if (bodyStat == null) {
            bodyStat = new BodyStat();
            bodyStat.setUser(user);
        }
        bodyStat.setWeight(dto.getWeight());
        bodyStat.setWaistCircumference(dto.getWaistCircumference());
        bodyStat.setArmCircumference(dto.getArmCircumference());
        bodyStat.setHipCircumference(dto.getHipCircumference());
        return bodyStatRepository.save(bodyStat);
    }
}
