package pl.gymtracker.gymtrackerbackend.dto;

import java.math.BigDecimal;

public class UserProfileUpdateRequest {
    // Pola dla danych użytkownika (opcjonalne, frontend może ich nie wysyłać wszystkich)
    private String username;
    private String surname;
    private String email;
    private String newPassword; // Opcjonalne nowe hasło

    // Pola dla profilu (twoje istniejące)
    private String gender;
    private Integer height;
    private BigDecimal weight;
    private BigDecimal waistCircumference;
    private BigDecimal armCircumference;
    private BigDecimal hipCircumference;

    // Gettery i Settery dla nowych pól
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    // Istniejące Gettery i Settery
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    // ... reszta getterów i setterów dla pól profilu
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public BigDecimal getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(BigDecimal waistCircumference) { this.waistCircumference = waistCircumference; }
    public BigDecimal getArmCircumference() { return armCircumference; }
    public void setArmCircumference(BigDecimal armCircumference) { this.armCircumference = armCircumference; }
    public BigDecimal getHipCircumference() { return hipCircumference; }
    public void setHipCircumference(BigDecimal hipCircumference) { this.hipCircumference = hipCircumference; }
}