package alura.challenge.ForoHub.domain;

/**
 * Custom exception for validation errors.
 */
public class ValidatorException extends RuntimeException {
    /**
     * Constructs a new ValidatorException with the specified detail message.
     * @param message the detail message
     */
    public ValidatorException(String message) {
        super(message);
    }
}
