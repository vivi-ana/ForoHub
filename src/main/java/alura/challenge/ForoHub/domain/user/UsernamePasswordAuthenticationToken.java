package alura.challenge.ForoHub.domain.user;

import jakarta.validation.constraints.NotBlank;

/**
 * Record representing the username and password for authentication.
 */
public record UsernamePasswordAuthenticationToken(
        @NotBlank(message = "{userName.required}")
        String userName,
        @NotBlank(message = "{pass.required}")
        String pass) {
}
