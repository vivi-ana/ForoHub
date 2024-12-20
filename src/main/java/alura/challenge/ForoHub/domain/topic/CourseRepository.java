package alura.challenge.ForoHub.domain.topic;

import alura.challenge.ForoHub.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing courses.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
}
