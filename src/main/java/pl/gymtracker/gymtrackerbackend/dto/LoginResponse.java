package pl.gymtracker.gymtrackerbackend.dto;

public class LoginResponse {

    private String token;

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    public LoginResponse(String token, Integer id, String email, String firstName, String lastName) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // gettery
    public String getToken() { return token; }
    public Integer getId() { return id; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
