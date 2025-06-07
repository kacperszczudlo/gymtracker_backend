# GymTracker - Backend API (Spring Boot)

Witamy w repozytorium serwera API dla aplikacji GymTracker! Ten projekt stanowi backend systemu, zbudowany w technologii **Spring Boot** z wykorzystaniem języka **Java**. Odpowiada za całą logikę biznesową, uwierzytelnianie, autoryzację oraz zarządzanie danymi użytkowników, ich treningów, pomiarów ciała, celów fitness i osiągnięć.

Backend udostępnia **API REST** dla klienta mobilnego GymTracker (aplikacja Android).

## Kluczowe Funkcjonalności

*   Rejestracja i bezpieczne logowanie użytkowników (z wykorzystaniem JWT).
*   Operacje CRUD (Create, Read, Update, Delete) na danych:
    *   Użytkownicy i ich profile.
    *   Ćwiczenia.
    *   Dzienniki treningowe (wraz z seriami i ćwiczeniami).
    *   Historia pomiarów ciała.
    *   Cele użytkowników.
*   Logika biznesowa do obliczania postępów i osiągnięć.
*   Zarządzanie sesjami i autoryzacją.

## Wymagania Systemowe

*   Java Development Kit (JDK) - wersja 17 lub nowsza (projekt aktualnie skonfigurowany pod Javę 23, ale powinien działać z 17+).
*   Maven (do zarządzania zależnościami i budowania projektu).
*   IntelliJ IDEA (zalecane) lub inne IDE wspierające Spring Boot.
*   Serwer bazy danych PostgreSQL.

## Uruchomienie Serwera

1.  **Sklonuj repozytorium:**
    ```bash
    git clone [URL_TWOJEGO_BACKEND_REPO]
    cd [nazwa_folderu_backend]
    ```
2.  **Konfiguracja Bazy Danych PostgreSQL:**
    *   Upewnij się, że masz zainstalowany i uruchomiony serwer PostgreSQL.
    *   **Utwórz bazę danych** oraz dedykowanego użytkownika z uprawnieniami do tej bazy. Przykładowo:
        ```sql
        CREATE DATABASE gymtracker_main_db; -- Możesz wybrać inną nazwę bazy
        CREATE USER gymtracker_app_user WITH PASSWORD 'twoje_bezpieczne_haslo';
        GRANT ALL PRIVILEGES ON DATABASE gymtracker_main_db TO gymtracker_app_user;
        ```
    *   Dane konfiguracyjne połączenia z bazą danych znajdują się w pliku:
        `src/main/resources/application.properties`
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/gymtracker_main_db
        spring.datasource.username=gymtracker_app_user
        spring.datasource.password=twoje_bezpieczne_haslo
        spring.datasource.driver-class-name=org.postgresql.Driver
        
        spring.jpa.properties.hibernate.default_schema=gymtracker
        ```
    *   **Dostosuj wartości** `spring.datasource.url`, `spring.datasource.username` oraz `spring.datasource.password` do swojej lokalnej konfiguracji PostgreSQL. Pamiętaj, aby nazwa bazy danych w URL (`gymtracker_main_db` w przykładzie) odpowiadała tej, którą utworzyłeś.
    *   **Schemat `gymtracker`:** Aplikacja oczekuje, że tabele będą znajdować się w schemacie `gymtracker`.
        *   Skrypt inicjalizujący strukturę tabel (tworzący schemat `gymtracker` i tabele) znajduje się poniżej. **Upewnij się, że poniższy skrypt został wykonany na Twojej bazie danych (np. `gymtracker_main_db`), aby utworzyć niezbędny schemat, tabele i sekwencje:**

        ```sql
        -- Usunięcie istniejącego schematu (opcjonalne, jeśli zaczynasz od zera lub chcesz wyczyścić)
        DROP SCHEMA IF EXISTS gymtracker CASCADE;

        -- Utworzenie nowego schematu
        CREATE SCHEMA gymtracker;
        -- Ustawienie domyślnej ścieżki wyszukiwania dla bieżącej sesji, aby kolejne polecenia odnosiły się do tego schematu
        -- (Ważne, jeśli wykonujesz skrypt w narzędziu, które nie ustawia schematu automatycznie po jego utworzeniu)
        -- Dla psql można też połączyć się bezpośrednio z bazą i wykonać: SET search_path TO gymtracker;
        -- lub upewnić się, że użytkownik bazy danych ma ten schemat jako domyślny.
        -- Wiele narzędzi GUI (jak DBeaver, pgAdmin) pozwala wybrać aktywny schemat.

        -- Sekwencje w schemacie gymtracker
        CREATE SEQUENCE gymtracker.users_id_seq START 1;
        CREATE SEQUENCE gymtracker.profile_id_seq START 1;
        CREATE SEQUENCE gymtracker.user_goals_id_seq START 1;
        CREATE SEQUENCE gymtracker.exercises_id_seq START 1;
        CREATE SEQUENCE gymtracker.training_log_id_seq START 1;
        CREATE SEQUENCE gymtracker.log_exercise_id_seq START 1;
        CREATE SEQUENCE gymtracker.log_series_id_seq START 1;
        CREATE SEQUENCE gymtracker.body_stat_history_id_seq START 1;

        -- Tabela użytkowników w schemacie gymtracker
        CREATE TABLE gymtracker.users (
            id            INT PRIMARY KEY DEFAULT nextval('gymtracker.users_id_seq'),
            username      VARCHAR(50)  NOT NULL UNIQUE,
            password      TEXT         NOT NULL,
            email         VARCHAR(100) NOT NULL UNIQUE,
            surname       VARCHAR(50),
            created_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
        );

        -- Tabela profilu użytkownika w schemacie gymtracker
        CREATE TABLE gymtracker.profile (
            id                 INT PRIMARY KEY DEFAULT nextval('gymtracker.profile_id_seq'),
            user_id            INT UNIQUE REFERENCES gymtracker.users(id) ON DELETE CASCADE,
            gender             TEXT,
            height             NUMERIC(5,2) CHECK (height > 0 OR height IS NULL),
            arm_circumference  NUMERIC(5,2),
            waist_circumference NUMERIC(5,2),
            hip_circumference  NUMERIC(5,2),
            weight             NUMERIC(5,2),
            date               DATE,
            created_at         TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at         TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
        );

        -- Tabela celów użytkownika w schemacie gymtracker
        CREATE TABLE gymtracker.user_goals (
            id                     INT PRIMARY KEY DEFAULT nextval('gymtracker.user_goals_id_seq'),
            user_id                INT UNIQUE REFERENCES gymtracker.users(id) ON DELETE CASCADE,
            target_weight          NUMERIC(5,2),
            start_weight           NUMERIC(5,2),
            target_training_days   INT,
            created_at             TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at             TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
        );

        -- Tabela ćwiczeń w schemacie gymtracker
        CREATE TABLE gymtracker.exercises (
            id           INT PRIMARY KEY DEFAULT nextval('gymtracker.exercises_id_seq'),
            name         VARCHAR(100) NOT NULL UNIQUE,
            created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
        );

        -- Tabela dziennika treningowego w schemacie gymtracker
        CREATE TABLE gymtracker.training_log (
            id          INT PRIMARY KEY DEFAULT nextval('gymtracker.training_log_id_seq'),
            user_id     INT REFERENCES gymtracker.users(id) ON DELETE CASCADE,
            date        DATE NOT NULL,
            day_name    TEXT NOT NULL,
            created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            CONSTRAINT training_log_user_date_day_uq UNIQUE (user_id, date, day_name)
        );

        -- Tabela ćwiczeń w dzienniku treningowym w schemacie gymtracker
        CREATE TABLE gymtracker.log_exercise (
            id            INT PRIMARY KEY DEFAULT nextval('gymtracker.log_exercise_id_seq'),
            log_id        INT REFERENCES gymtracker.training_log(id) ON DELETE CASCADE,
            exercise_name VARCHAR(100) NOT NULL,
            created_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
        );

        -- Tabela serii dla ćwiczeń w dzienniku w schemacie gymtracker
        CREATE TABLE gymtracker.log_series (
            id               INT PRIMARY KEY DEFAULT nextval('gymtracker.log_series_id_seq'),
            log_exercise_id  INT REFERENCES gymtracker.log_exercise(id) ON DELETE CASCADE,
            reps             INT CHECK (reps >= 0 OR reps IS NULL),
            weight           NUMERIC(5,2),
            created_at       TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at       TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
        );

        -- Tabela historii pomiarów użytkownika w schemacie gymtracker
        CREATE TABLE gymtracker.body_stat_history (
            id                  INT PRIMARY KEY DEFAULT nextval('gymtracker.body_stat_history_id_seq'),
            user_id             INT REFERENCES gymtracker.users(id) ON DELETE CASCADE,
            date                DATE NOT NULL, 
            weight              NUMERIC(5,2),
            arm_circumference   NUMERIC(5,2),
            waist_circumference NUMERIC(5,2),
            hip_circumference   NUMERIC(5,2),
            created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            updated_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
            CONSTRAINT body_stat_history_user_date_uq UNIQUE (user_id, date)
        );

        -- Indeksy dla przyspieszenia zapytań w schemacie gymtracker
        CREATE INDEX idx_training_log_user_date
            ON gymtracker.training_log (user_id, date);

        CREATE INDEX idx_exercise_name
            ON gymtracker.exercises (name);

        CREATE INDEX idx_log_exercise_log
            ON gymtracker.log_exercise (log_id);

        CREATE INDEX idx_log_series_exercise
            ON gymtracker.log_series (log_exercise_id);

        CREATE INDEX idx_body_stat_history_user_date
            ON gymtracker.body_stat_history (user_id, date);
        ```
        **Uwaga:** Właściwość `spring.jpa.hibernate.ddl-auto` w `application.properties` jest ustawiona na `validate`, co oznacza, że Hibernate sprawdzi, czy schemat bazy danych pasuje do definicji encji, ale nie będzie automatycznie tworzyć ani modyfikować tabel. Dlatego **musisz ręcznie utworzyć strukturę bazy danych** za pomocą powyższego skryptu SQL.
        Alternatywnie, dla celów deweloperskich, możesz ustawić `spring.jpa.hibernate.ddl-auto=update` (Hibernate spróbuje zaktualizować schemat) lub `spring.jpa.hibernate.ddl-auto=create-drop` (schemat jest tworzony przy starcie i usuwany przy zamknięciu - **uwaga: usuwa dane!**), ale dla produkcji zalecane jest `validate` lub `none` i zarządzanie schematem przez migracje (np. Flyway, Liquibase).
        Możesz także rozważyć `spring.sql.init.mode=always` i umieszczenie skryptu w `schema.sql` (dla DDL) i `data.sql` (dla DML - początkowych danych), aby Spring Boot automatycznie je wykonał przy starcie.

3.  **Otwórz projekt w IntelliJ IDEA (lub innym IDE).**
4.  **Zbuduj projekt** (Maven automatycznie pobierze zależności).
5.  **Uruchom aplikację** (np. poprzez główną klasę `GymtrackerbackendApplication.java`).
6.  Serwer powinien uruchomić się domyślnie na porcie `8080`.

## Struktura Projektu

*   `/src/main/java/pl/gymtracker/gymtrackerbackend/`: Główne pakiety aplikacji:
    *   `config/`: Konfiguracja Spring (np. SecurityConfig).
    *   `controller/`: Kontrolery REST API.
    *   `dto/`: Data Transfer Objects (obiekty do przesyłania danych między warstwami).
    *   `entity/`: Encje JPA reprezentujące tabele bazy danych.
    *   `repository/`: Interfejsy Spring Data JPA do operacji na bazie danych.
    *   `service/`: Logika biznesowa aplikacji.
*   `/src/main/resources/`:
    *   `application.properties`: Główny plik konfiguracyjny aplikacji.
*   `pom.xml`: Plik konfiguracyjny Maven.

## Technologie

*   Java
*   Spring Boot (Spring Web, Spring Data JPA, Spring Security)
*   Hibernate
*   PostgreSQL (baza danych)
*   Maven (narzędzie budowania)
*   JWT (JSON Web Tokens do autoryzacji)

## API Endpoints

Główne endpointy API są dostępne pod bazowym adresem `/api/v1/`.
Przykładowe ścieżki:
*   `/auth/register`
*   `/auth/login`
*   `/users/{userId}/...`
*   `/exercises`
*   `/training-logs`

---
