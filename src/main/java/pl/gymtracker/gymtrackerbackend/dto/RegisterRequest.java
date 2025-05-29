package pl.gymtracker.gymtrackerbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    // Dane użytkownika
    @NotBlank
    private String username;

    @NotBlank
    private String surname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String gender;

    private Integer height;

    // Dane body_stat
    private Double weight;
    private Double waistCircumference;
    private Double armCircumference;
    private Double hipCircumference;

    // Gettery i settery
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getWaistCircumference() { return waistCircumference; }
    public void setWaistCircumference(Double waistCircumference) { this.waistCircumference = waistCircumference; }

    public Double getArmCircumference() { return armCircumference; }
    public void setArmCircumference(Double armCircumference) { this.armCircumference = armCircumference; }

    public Double getHipCircumference() { return hipCircumference; }
    public void setHipCircumference(Double hipCircumference) { this.hipCircumference = hipCircumference; }
}
