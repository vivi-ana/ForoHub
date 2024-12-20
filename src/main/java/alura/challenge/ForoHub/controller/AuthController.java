package alura.challenge.ForoHub.controller;

import alura.challenge.ForoHub.domain.user.User;
import alura.challenge.ForoHub.infra.security.DataJWTResponse;
import alura.challenge.ForoHub.domain.user.UsernamePasswordAuthenticationToken;
import alura.challenge.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user authentication.
 */
@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Processes user authentication requests.
     * @param usernamePasswordAuthenticationToken the authentication request containing username and password
     * @return a ResponseEntity containing the JWT token
     */
    @PostMapping
    public ResponseEntity authenticationUser(@RequestBody @Valid UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Authentication authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(usernamePasswordAuthenticationToken.userName(), usernamePasswordAuthenticationToken.pass());
        var userAuthenticated = authenticationManager.authenticate(authToken);
        var tokenJWT = tokenService.generatedToken((User) userAuthenticated.getPrincipal());
        return ResponseEntity.ok(new DataJWTResponse(tokenJWT));
    }
}