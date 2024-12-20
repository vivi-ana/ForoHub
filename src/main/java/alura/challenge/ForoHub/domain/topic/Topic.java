package alura.challenge.ForoHub.domain.topic;

import alura.challenge.ForoHub.domain.Answer;
import alura.challenge.ForoHub.domain.Course;
import alura.challenge.ForoHub.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a topic.
 */
@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private LocalDate creationDate;
    private Boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    /**
     * Updates the topic with new data.
     * @param topicUpdate the new data to update the topic with
     */
    public void updateData(TopicUpdate topicUpdate) {
        if (topicUpdate.title() != null) this.title = topicUpdate.title();
        if (topicUpdate.message() != null) this.message = topicUpdate.message();
    }
}