package pl.gymtracker.gymtrackerbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "gymtracker2")
public class User extends BaseEntity {

    /* ---------- KLUCZ GŁÓWNY ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq_gen")
    @SequenceGenerator(name = "users_id_seq_gen",
            sequenceName = "gymtracker2.users_id_seq",
            allocationSize = 1)
    private Integer id;         // ← INT w bazie ⇒ Integer w encji

    /* ---------- POLA ---------- */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(length = 50)
    private String surname;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String passwordHash;

    /* ---------- KONSTRUKTORY ---------- */
    public User() { }           // JPA wymaga pustego konstruktora

    public User(Integer id,
                String username,
                String surname,
                String email,
                String passwordHash) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /* ---------- GETTERY / SETTERY ---------- */
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
