package alura.challenge.ForoHub.infra.security;

import alura.challenge.ForoHub.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service for managing JWT tokens.
 */
@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String apiSecret;

    @Value("${jwt.expiration}")
    private int expirationTimeInSeconds;

    /**
     * Generates a JWT token for the given user.
     * @param user the user for whom the token is generated
     * @return the generated JWT token
     */
    public String generatedToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("ForoHub")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generatedExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }

    /**
     * Retrieves the subject (username) from the given token.
     * @param token the JWT token
     * @return the subject (username) from the token
     */
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("ForoHub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println("Error verifying the token: " + exception.getMessage());
            exception.printStackTrace();
        }
        if (verifier.getSubject() == null) throw new RuntimeException("Invalid verifier");
        return verifier.getSubject();
    }

    /**
     * Generates the expiration date for the JWT token.
     * @return the expiration date
     */
    private Instant generatedExpirationDate() {
        return LocalDateTime.now().plusHours(expirationTimeInSeconds).toInstant(ZoneOffset.of("-03:00"));
    }
}
