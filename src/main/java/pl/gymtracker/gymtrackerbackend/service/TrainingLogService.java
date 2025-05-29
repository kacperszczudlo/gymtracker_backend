package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gymtracker.gymtrackerbackend.dto.LogExerciseRequest;
import pl.gymtracker.gymtrackerbackend.dto.LogSeriesRequest;
import pl.gymtracker.gymtrackerbackend.dto.TrainingLogRequest;
import pl.gymtracker.gymtrackerbackend.entity.LogExercise;
import pl.gymtracker.gymtrackerbackend.entity.LogSeries;
import pl.gymtracker.gymtrackerbackend.entity.TrainingLog;
import pl.gymtracker.gymtrackerbackend.entity.User;
import pl.gymtracker.gymtrackerbackend.repository.LogExerciseRepository; // Potrzebne, jeśli będziesz z niego korzystać (np. do czyszczenia)
import pl.gymtracker.gymtrackerbackend.repository.TrainingLogRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingLogService {

    private final TrainingLogRepository trainingLogRepository;
    private final UserRepository userRepository;
    private final LogExerciseRepository logExerciseRepository; // Dodajemy do wstrzyknięcia

    public TrainingLogService(TrainingLogRepository trainingLogRepository,
                              UserRepository userRepository,
                              LogExerciseRepository logExerciseRepository) { // Dodajemy do konstruktora
        this.trainingLogRepository = trainingLogRepository;
        this.userRepository = userRepository;
        this.logExerciseRepository = logExerciseRepository; // Inicjalizujemy
    }

    @Transactional
    public void saveTrainingLog(TrainingLogRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        LocalDate date = LocalDate.parse(request.getDate());

        Optional<TrainingLog> existingLogOpt = trainingLogRepository.findByUserIdAndDateAndDayName(
                user.getId(), date, request.getDayName()
        );

        TrainingLog trainingLog;
        if (existingLogOpt.isPresent()) {
            trainingLog = existingLogOpt.get();
            // Usuń istniejące ćwiczenia i serie z tego logu, aby zastąpić je nowymi.
            // Ważne: kolejność operacji i flush(), aby uniknąć constraint violations.
            List<LogExercise> exercisesToDelete = new ArrayList<>(trainingLog.getExercises());
            trainingLog.getExercises().clear(); // Usuń z kolekcji zarządzanej przez encję
            trainingLogRepository.flush(); // Wymuś synchronizację z bazą (usunięcie starych powiązań)
            exercisesToDelete.forEach(logExerciseRepository::delete); // Jawnie usuń osierocone LogExercise
        } else {
            trainingLog = new TrainingLog();
            trainingLog.setUser(user);
            trainingLog.setDate(date);
            trainingLog.setDayName(request.getDayName());
        }

        // Dodaj nowe ćwiczenia i serie
        List<LogExercise> newLogExercises = new ArrayList<>();
        if (request.getExercises() != null) {
            for (LogExerciseRequest exReq : request.getExercises()) {
                LogExercise logExercise = new LogExercise();
                logExercise.setExerciseName(exReq.getExerciseName());
                logExercise.setTrainingLog(trainingLog); // Ustaw powiązanie zwrotne

                List<LogSeries> newLogSeriesList = new ArrayList<>();
                if (exReq.getSeries() != null) {
                    for (LogSeriesRequest sReq : exReq.getSeries()) {
                        LogSeries series = new LogSeries();
                        series.setReps(sReq.getReps());
                        series.setWeight(sReq.getWeight()); // Zakładając, że LogSeries.weight jest Double
                        series.setLogExercise(logExercise); // Ustaw powiązanie zwrotne
                        newLogSeriesList.add(series);
                    }
                }
                logExercise.setSeriesList(newLogSeriesList);
                newLogExercises.add(logExercise);
            }
        }
        trainingLog.getExercises().addAll(newLogExercises); // Dodaj wszystkie nowe ćwiczenia do logu
        trainingLogRepository.save(trainingLog);
    }

    @Transactional(readOnly = true)
    public TrainingLog getTrainingLog(Integer userId, String dateString, String dayName) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        LocalDate date = LocalDate.parse(dateString);
        return trainingLogRepository.findByUserIdAndDateAndDayName(userId, date, dayName)
                .orElse(null); // Zwróć null, jeśli log nie istnieje
    }

    @Transactional
    public void deleteTrainingLog(Integer logId) {
        TrainingLog logToDelete = trainingLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("TrainingLog not found with id: " + logId));
        // Dzięki CascadeType.ALL i orphanRemoval=true, powiązane LogExercise i LogSeries powinny zostać usunięte.
        trainingLogRepository.delete(logToDelete);
    }

    @Transactional(readOnly = true)
    public int getActiveTrainingDaysInCurrentWeek(Integer userId) {
        userRepository.findById(userId) // Walidacja użytkownika
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<TrainingLog> logsThisWeek = trainingLogRepository.findAllByUserIdAndDateBetween(userId, startOfWeek, endOfWeek);

        if (logsThisWeek == null || logsThisWeek.isEmpty()) {
            return 0;
        }

        long activeDaysCount = logsThisWeek.stream()
                .filter(log -> log.getExercises() != null && !log.getExercises().isEmpty()) // Dzień aktywny, jeśli ma ćwiczenia
                .map(TrainingLog::getDate) // Bierzemy tylko daty
                .distinct() // Liczymy unikalne dni
                .count();

        return (int) activeDaysCount;
    }
}