package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gymtracker.gymtrackerbackend.dto.ExerciseDto;
import pl.gymtracker.gymtrackerbackend.dto.WorkoutDto;
import pl.gymtracker.gymtrackerbackend.model.Exercise;
import pl.gymtracker.gymtrackerbackend.model.User;
import pl.gymtracker.gymtrackerbackend.model.Workout;
import pl.gymtracker.gymtrackerbackend.repository.ExerciseRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;
import pl.gymtracker.gymtrackerbackend.repository.WorkoutRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    // Zapis treningu wraz z ćwiczeniami
    public WorkoutDto saveWorkout(Integer userId, WorkoutDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));

        Workout workout = new Workout();
        workout.setUser(user);
        workout.setName(dto.getName());
        workout.setType(dto.getType());
        workout.setDuration(dto.getDuration());
        workout.setNotes(dto.getNotes());
        workout = workoutRepository.save(workout);

        // Jeśli otrzymaliśmy listę ćwiczeń, zapisz je jako pod-treningi
        if (dto.getExercises() != null && !dto.getExercises().isEmpty()) {
            List<Exercise> exerciseList = new ArrayList<>();
            for (ExerciseDto exDto : dto.getExercises()) {
                Exercise exercise = new Exercise();
                exercise.setWorkout(workout);
                exercise.setName(exDto.getName());
                exercise.setWeight(exDto.getWeight());
                exerciseList.add(exercise);
            }
            exerciseRepository.saveAll(exerciseList);
        }

        // Zamapuj zapisany obiekt Workout na DTO, by zwrócić odpowiedź
        WorkoutDto result = new WorkoutDto();
        result.setId(workout.getId());
        result.setUserId(userId);
        result.setName(workout.getName());
        result.setType(workout.getType());
        result.setDuration(workout.getDuration());
        result.setNotes(workout.getNotes());
        // Możesz też dodać mapowanie ćwiczeń, jeśli potrzebne
        return result;
    }

    // Pobierz listę treningów dla danego użytkownika (opcjonalnie filtruj po dniu – zakładamy, że dzień jest zapisany w polu name)
    public List<WorkoutDto> getWorkoutsForUserAndDay(Integer userId, String day) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));

        List<Workout> workouts;
        if(day == null || day.isEmpty()){
            workouts = workoutRepository.findByUser(user);
        } else {
            workouts = workoutRepository.findByUserAndName(user, day);
        }

        List<WorkoutDto> dtoList = new ArrayList<>();
        for (Workout w : workouts) {
            WorkoutDto dto = new WorkoutDto();
            dto.setId(w.getId());
            dto.setUserId(userId);
            dto.setName(w.getName());
            dto.setType(w.getType());
            dto.setDuration(w.getDuration());
            dto.setNotes(w.getNotes());
            // Mapowanie ćwiczeń – możesz pobrać ćwiczenia dla danego treningu (jeśli chcesz, dodaj relację do encji Workout lub wykorzystaj ExerciseRepository)
            dtoList.add(dto);
        }
        return dtoList;
    }
}
