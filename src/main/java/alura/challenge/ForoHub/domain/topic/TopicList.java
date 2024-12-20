package alura.challenge.ForoHub.domain.topic;

import alura.challenge.ForoHub.domain.Course;
import alura.challenge.ForoHub.domain.user.User;

import java.time.LocalDate;

/**
 * Record representing a summary of a topic.
 */
public record TopicList(String title,
                        String message,
                        LocalDate creationDate,
                        boolean status,
                        User user,
                        Course course) {
    /**
     * Constructs a TopicList from a Topic entity.
     * @param topic the topic entity to convert to TopicList
     */
    public TopicList(Topic topic) {
        this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(),
                topic.getStatus(), topic.getAuthor(), topic.getCourse());
    }
}
