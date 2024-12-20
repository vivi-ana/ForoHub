package alura.challenge.ForoHub.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Record representing the data required to register a new topic.
 */
public record TopicRegister(
        @NotBlank(message = "{title.required}")
        String title,
        @NotBlank(message = "{message.required}")
        @Size(max = 250, message = "{message.size}")
        String message,
        @NotNull(message = "{user.required}")
        Long idUser,
        @NotNull(message = "{course.required}")
        Long idCourse
) {
}
