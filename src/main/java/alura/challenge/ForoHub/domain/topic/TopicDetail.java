package alura.challenge.ForoHub.domain.topic;

import alura.challenge.ForoHub.domain.Course;
import alura.challenge.ForoHub.domain.user.User;

import java.time.LocalDate;

/**
 * Record representing the details of a topic.
 */
public record TopicDetail(Long id,
                          String title,
                          String message,
                          LocalDate creationDate,
                          boolean status,
                          User user,
                          Course course) {
    /**
     * Constructs a TopicDetail from a Topic entity.
     * @param topic the topic entity to convert to TopicDetail
     */
    public TopicDetail(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate(),
                topic.getStatus(), topic.getAuthor(), topic.getCourse());
    }
}