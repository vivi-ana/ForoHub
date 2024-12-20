package alura.challenge.ForoHub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing topics.
 */
public interface TopicRepository extends JpaRepository<Topic, Long> {
    /**
     * Checks if a topic with the given title and message already exists.
     *
     * @param title   the title of the topic
     * @param message the message of the topic
     * @return true if such a topic exists, false otherwise
     */
    Boolean existsByTitleAndMessage(String title, String message);

    /**
     * Finds topics by course name and year.
     * @param courseName the name of the course * @param year the year of topic creation
     * @param pageable pagination and sorting information
     * @return a page of topics filtered by course name and year
     */
    @Query("SELECT t FROM Topic t JOIN t.course c WHERE c.name = :courseName AND YEAR(t.creationDate) = :year")
    Page<Topic> findByCourseNameAndYear(@Param("courseName") String courseName, @Param("year") Integer year, Pageable pageable);
}
