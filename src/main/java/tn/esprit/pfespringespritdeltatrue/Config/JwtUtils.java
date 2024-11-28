package tn.esprit.pfespringespritdeltatrue.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tn.esprit.pfespringespritdeltatrue.Entities.CustomUserDetails;
import tn.esprit.pfespringespritdeltatrue.Entities.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // Define secret key for signing JWTs and token expiration time
    private String secretKey = "yourSecretKey"; // You can replace with a more secure key or load it from properties
    private long expirationTime = 86400000L; // 1 day in milliseconds (24 hours)

    /**
     * Method to generate a JWT token for a given username.
     * @param userEmail the username for the token.
     * @return the JWT token.
     */
    public String generateToken(String userEmail) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject(userEmail)  // Set the subject of the JWT to the username
                .setIssuedAt(new Date())  // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Set expiration time
                .signWith(key)  // Sign the token using the HS256 algorithm and secret key
                .compact();
    }

    /**
     * Extract JWT token from the HTTP request.
     * @param request the HTTP request.
     * @return the JWT token if present, or null if not found.
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extract the token part by removing 'Bearer '
        }
        return null;  // Return null if no token is found
    }

    /**
     * Validate the JWT token.
     * @param token the JWT token.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)  // Use the secret key to parse the token
                    .parseClaimsJws(token);  // Parse the JWT claims
            return true;  // If no exceptions occur, the token is valid
        } catch (DecodingException e) {
            System.err.println("Invalid Base64 characters in JWT: " + e.getMessage());
            return false;  // Handle decoding exceptions
        } catch (Exception e) {
            System.err.println("JWT validation error: " + e.getMessage());
            return false;  // Catch all other exceptions related to validation
        }
    }

    /**
     * Get the email claim from the JWT token.
     * @param token the JWT token.
     * @return the email associated with the JWT token.
     */
    public String getUserEmailFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)  // Use the secret key to parse the token
                .parseClaimsJws(token)  // Parse the claims
                .getBody();  // Get the body of the JWT

        // Extract and return the email claim from the JWT
        return claims.get("email", String.class);  // Make sure the email claim exists in the token
    }

    // Optionally, you can add additional methods for handling other claims or user details.
}
