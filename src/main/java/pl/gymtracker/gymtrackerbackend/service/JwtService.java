package pl.gymtracker.gymtrackerbackend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import pl.gymtracker.gymtrackerbackend.entity.User;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "tajnykluczGymTrackerJWT";
    private static final long EXPIRATION_MS = 86400000; // 1 dzień

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("surname", user.getSurname())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // subject = email
    }

}
