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
        CREATE DATABASE gymtracker_db;
        CREATE USER gymtracker_user WITH PASSWORD 'twoje_bezpieczne_haslo';
        GRANT ALL PRIVILEGES ON DATABASE gymtracker_db TO gymtracker_user;
        ```
    *   Dane konfiguracyjne połączenia z bazą danych znajdują się w pliku:
        `src/main/resources/application.properties`
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/gymtracker_db
        spring.datasource.username=gymtracker_user
        spring.datasource.password=twoje_bezpieczne_haslo
        spring.datasource.driver-class-name=org.postgresql.Driver
        
        spring.jpa.properties.hibernate.default_schema=gymtracker2
        ```
    *   **Dostosuj wartości** `spring.datasource.url`, `spring.datasource.username` oraz `spring.datasource.password` do swojej lokalnej konfiguracji PostgreSQL.
    *   **Schemat `gymtracker2`:** Aplikacja oczekuje, że tabele będą znajdować się w schemacie `gymtracker2`.
        *   Możesz utworzyć schemat ręcznie: `CREATE SCHEMA gymtracker2 AUTHORIZATION gymtracker_user;` i nadać uprawnienia.
        *   Alternatywnie, możesz zmodyfikować `spring.jpa.properties.hibernate.default_schema` lub usunąć tę linię, jeśli chcesz używać domyślnego schematu `public`.
        *   Skrypt inicjalizujący strukturę tabel (tworzący schemat `gymtracker2` i tabele) znajduje się w pliku `src/main/resources/data.sql` (lub `schema.sql` - dostosuj, jeśli używasz innego pliku). **Upewnij się, że ten skrypt został wykonany na Twojej bazie danych, aby utworzyć niezbędne tabele i sekwencje.**
        ```sql
        -- Plik: data.sql lub schema.sql
        -- Usunięcie istniejącego schematu (opcjonalne, jeśli zaczynasz od zera)
        DROP SCHEMA IF EXISTS gymtracker2 CASCADE;

        -- Utworzenie nowego schematu
        CREATE SCHEMA gymtracker2;
        SET search_path TO gymtracker2; -- Ważne dla reszty skryptu

        -- Sekwencje
        CREATE SEQUENCE gymtracker2.users_id_seq START 1;
        -- ... (reszta sekwencji i tabel jak w dostarczonym skrypcie) ...
        
        -- Tabela użytkowników
        CREATE TABLE gymtracker2.users (
            id            INT PRIMARY KEY DEFAULT nextval('gymtracker2.users_id_seq'),
            -- ... (reszta definicji tabel) ...
        );
        -- ... (reszta tabel i indeksów) ...
        ```
        **Uwaga:** Właściwość `spring.jpa.hibernate.ddl-auto` jest ustawiona na `validate`, co oznacza, że Hibernate sprawdzi, czy schemat bazy danych pasuje do definicji encji, ale nie będzie automatycznie tworzyć ani modyfikować tabel. Dlatego **musisz ręcznie utworzyć strukturę bazy danych** za pomocą powyższego skryptu SQL.

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
    *   `data.sql` lub `schema.sql`: Skrypt SQL do inicjalizacji bazy danych (jeśli używany).
*   `pom.xml`: Plik konfiguracyjny Maven.

## Technologie

*   Java
*   Spring Boot (Spring Web, Spring Data JPA, Spring Security)
*   Hibernate
*   PostgreSQL (baza danych)
*   Maven (narzędzie budowania)
*   JWT (JSON Web Tokens do autoryzacji)

## API Endpoints

Główne endpointy API są dostępne pod bazowym adresem `/api/v1/`. Szczegółowa dokumentacja endpointów (np. Swagger/OpenAPI) może zostać dodana w przyszłości. Przykładowe ścieżki:
*   `/auth/register`
*   `/auth/login`
*   `/users/{userId}/...`
*   `/exercises`
*   `/training-logs`

---

Pamiętaj, aby zastąpić `[URL_TWOJEGO_BACKEND_REPO]` i `[nazwa_folderu_backend]` oraz przykładowe dane do bazy danych (`gymtracker_db`, `gymtracker_user`, `twoje_bezpieczne_haslo`) rzeczywistymi wartościami, których użyjesz.

Dodałem sekcję o konfiguracji bazy danych PostgreSQL i konieczności wykonania skryptu SQL, co jest bardzo ważne dla kogoś, kto chciałby uruchomić Twój backend.
