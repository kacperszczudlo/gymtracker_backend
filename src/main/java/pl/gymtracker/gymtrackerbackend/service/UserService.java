package pl.gymtracker.gymtrackerbackend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gymtracker.gymtrackerbackend.dto.LoginRequest;
import pl.gymtracker.gymtrackerbackend.dto.RegisterRequest;
import pl.gymtracker.gymtrackerbackend.dto.UserProfileResponse;
import pl.gymtracker.gymtrackerbackend.dto.UserProfileUpdateRequest;
import pl.gymtracker.gymtrackerbackend.entity.BodyStatHistory;
import pl.gymtracker.gymtrackerbackend.entity.Profile;
import pl.gymtracker.gymtrackerbackend.entity.User;
import pl.gymtracker.gymtrackerbackend.repository.BodyStatHistoryRepository;
import pl.gymtracker.gymtrackerbackend.repository.ProfileRepository;
import pl.gymtracker.gymtrackerbackend.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.Date; // Używamy java.sql.Date
import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final ProfileRepository profileRepo;
    private final BodyStatHistoryRepository bodyStatHistoryRepo;

    public UserService(UserRepository repo, PasswordEncoder encoder,
                       JwtService jwtService, ProfileRepository profileRepo,
                       BodyStatHistoryRepository bodyStatHistoryRepo) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.profileRepo = profileRepo;
        this.bodyStatHistoryRepo = bodyStatHistoryRepo;
    }

    @Transactional
    public User register(RegisterRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("E-mail zajęty");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        user = repo.save(user);

        // Zapis do Profile
        // Ponieważ request może teraz zawierać null dla pól pomiarowych,
        // BigDecimal.valueOf() nie zostanie wywołane, jeśli odpowiednie pole w request jest null.
        Profile profile = new Profile(
                user,
                request.getGender(), // Może być null
                request.getHeight() != null ? BigDecimal.valueOf(request.getHeight()) : null,
                request.getWeight() != null ? BigDecimal.valueOf(request.getWeight()) : null,
                request.getWaistCircumference() != null ? BigDecimal.valueOf(request.getWaistCircumference()) : null,
                request.getArmCircumference() != null ? BigDecimal.valueOf(request.getArmCircumference()) : null,
                request.getHipCircumference() != null ? BigDecimal.valueOf(request.getHipCircumference()) : null,
                Date.valueOf(LocalDate.now()) // Konwersja LocalDate na java.sql.Date
        );
        profileRepo.save(profile);

        // Zapis do BodyStatHistory (początkowy wpis), jeśli są dane
        // KLUCZOWA ZMIANA: Ten warunek teraz NIE BĘDZIE spełniony, jeśli RegisterRequest
        // przekazuje null dla wszystkich pól pomiarowych, co jest teraz oczekiwane.
        if (request.getWeight() != null || request.getArmCircumference() != null ||
                request.getWaistCircumference() != null || request.getHipCircumference() != null) {

            // Ten blok kodu nie powinien być wykonany podczas początkowej rejestracji,
            // jeśli RegisterActivity wysyła null dla pomiarów.
            // Zostanie wykonany, jeśli w przyszłości RegisterRequest będzie zawierał te dane.
            BodyStatHistory initialStat = new BodyStatHistory();
            initialStat.setUser(user);
            initialStat.setDate(Date.valueOf(LocalDate.now())); // Konwersja LocalDate na java.sql.Date
            initialStat.setWeight(request.getWeight() != null ? BigDecimal.valueOf(request.getWeight()) : null);
            initialStat.setArmCircumference(request.getArmCircumference() != null ? BigDecimal.valueOf(request.getArmCircumference()) : null);
            initialStat.setWaistCircumference(request.getWaistCircumference() != null ? BigDecimal.valueOf(request.getWaistCircumference()) : null);
            initialStat.setHipCircumference(request.getHipCircumference() != null ? BigDecimal.valueOf(request.getHipCircumference()) : null);
            bodyStatHistoryRepo.save(initialStat);
        }
        return user;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse login(LoginRequest request) {
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy e-mail lub hasło"));

        if (!encoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Nieprawidłowy e-mail lub hasło");
        }

        String token = jwtService.generateToken(user);

        // Pobierz najnowszy profil, jeśli istnieje (wg. daty utworzenia rekordu profilu)
        Profile profile = profileRepo.findFirstByUserOrderByCreatedAtDesc(user)
                .orElse(null);

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getSurname(),
                profile != null ? profile.getGender() : null,
                profile != null && profile.getHeight() != null ? profile.getHeight().intValue() : null,
                profile != null ? profile.getWeight() : null,
                profile != null ? profile.getWaistCircumference() : null,
                profile != null ? profile.getArmCircumference() : null,
                profile != null ? profile.getHipCircumference() : null,
                token
        );
    }

    @Transactional
    public void updateUserProfile(Integer userId, UserProfileUpdateRequest request) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik o ID " + userId + " nie istnieje"));

        // Aktualizacja danych użytkownika w tabeli User
        boolean userChanged = false;
        if (request.getUsername() != null && !request.getUsername().isEmpty() && !request.getUsername().equals(user.getUsername())) {
            user.setUsername(request.getUsername());
            userChanged = true;
        }
        if (request.getSurname() != null && !request.getSurname().isEmpty() && !request.getSurname().equals(user.getSurname())) {
            user.setSurname(request.getSurname());
            userChanged = true;
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty() && !request.getEmail().equals(user.getEmail())) {
            // Sprawdź tylko jeśli email się faktycznie zmienia
            if (repo.existsByEmail(request.getEmail())) {
                throw new IllegalStateException("Podany adres e-mail jest już zajęty.");
            }
            user.setEmail(request.getEmail());
            userChanged = true;
        }
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            user.setPasswordHash(encoder.encode(request.getNewPassword()));
            userChanged = true;
        }

        if (userChanged) {
            // user.setUpdatedAt(java.time.LocalDateTime.now()); // BaseEntity zrobi to automatycznie przy @PreUpdate
            repo.save(user);
        }

        // Sprawdź, czy są jakiekolwiek dane profilowe do zaktualizowania/utworzenia
        boolean hasProfileDataToUpdate = request.getGender() != null ||
                request.getHeight() != null ||
                request.getWeight() != null ||
                request.getWaistCircumference() != null ||
                request.getArmCircumference() != null ||
                request.getHipCircumference() != null;

        Profile profileToUpdate = null;
        if (hasProfileDataToUpdate) {
            profileToUpdate = profileRepo.findFirstByUserOrderByCreatedAtDesc(user)
                    .orElse(null); // Szukamy najnowszego profilu

            // Jeśli nie ma profilu, a są dane do utworzenia, stwórz nowy
            if (profileToUpdate == null) {
                profileToUpdate = new Profile();
                profileToUpdate.setUser(user);
                profileToUpdate.setDate(Date.valueOf(LocalDate.now())); // Data utworzenia/pierwszego wpisu
            } else {
                // Jeśli profil istnieje i aktualizujemy go, data profilu powinna być datą aktualizacji pomiarów
                profileToUpdate.setDate(Date.valueOf(LocalDate.now()));
            }

            // Aktualizuj pola profilu tylko jeśli są podane w żądaniu
            if (request.getGender() != null) profileToUpdate.setGender(request.getGender());
            if (request.getHeight() != null) profileToUpdate.setHeight(BigDecimal.valueOf(request.getHeight()));
            if (request.getWeight() != null) profileToUpdate.setWeight(request.getWeight()); // request.getWeight() jest już BigDecimal
            if (request.getWaistCircumference() != null) profileToUpdate.setWaistCircumference(request.getWaistCircumference());
            if (request.getArmCircumference() != null) profileToUpdate.setArmCircumference(request.getArmCircumference());
            if (request.getHipCircumference() != null) profileToUpdate.setHipCircumference(request.getHipCircumference());

            // profileToUpdate.setUpdatedAt(java.time.LocalDateTime.now()); // BaseEntity zrobi to automatycznie
            profileRepo.save(profileToUpdate);
        }

        // Zawsze twórz nowy wpis w BodyStatHistory, jeśli są jakiekolwiek dane pomiarowe (waga lub obwody)
        // i zostały one przesłane w żądaniu
        boolean measurementDataProvidedInRequest = request.getWeight() != null ||
                request.getArmCircumference() != null ||
                request.getWaistCircumference() != null ||
                request.getHipCircumference() != null;

        if (measurementDataProvidedInRequest) {
            BodyStatHistory newStat = new BodyStatHistory();
            newStat.setUser(user);
            newStat.setDate(Date.valueOf(LocalDate.now())); // Data bieżącego pomiaru
            newStat.setWeight(request.getWeight());
            newStat.setArmCircumference(request.getArmCircumference());
            newStat.setWaistCircumference(request.getWaistCircumference());
            newStat.setHipCircumference(request.getHipCircumference());
            // newStat.setCreatedAt(...); // BaseEntity zrobi to automatycznie
            bodyStatHistoryRepo.save(newStat);
        }
    }
}