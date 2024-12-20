package alura.challenge.ForoHub.domain.topic;

import alura.challenge.ForoHub.domain.Course;
import alura.challenge.ForoHub.domain.user.User;
import alura.challenge.ForoHub.domain.ValidatorException;
import alura.challenge.ForoHub.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Service class for managing topics.
 */
@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;

    /**
     * Registers a new topic.
     * @param topicRegister the data required to register the topic
     * @return the details of the registered topic
     */
    @Transactional
    public TopicDetail registerTopic(TopicRegister topicRegister) {
        if (topicRepository.existsByTitleAndMessage(topicRegister.title(), topicRegister.message())) {
            throw new ValidatorException("Topic with the same title and message already exists.");
        }
        Optional<User> userOptional = userRepository.findById(topicRegister.idUser());
        if (userOptional.isPresent()) {
            var author = userOptional.get();
            Optional<Course> courseOptional = courseRepository.findById(topicRegister.idCourse());
            if (courseOptional.isPresent()) {
                var course = courseOptional.get();
                var topic = new Topic(null, topicRegister.title(), topicRegister.message(), LocalDate.now(),
                        true, author, course, new ArrayList<>());
                topicRepository.save(topic);
                return new TopicDetail(topic);
            } else {
                throw new ValidatorException("The course cannot be found.");
            }
        } else {
            throw new ValidatorException("The author cannot be found.");
        }
    }

    /**
     * Retrieves a topic by its id.
     * @param id the id of the topic to be retrieved
     * @return the details of the retrieved topic
     */
    public TopicDetail getTopicById(Long id) {
        try {
            var topic = topicRepository.getReferenceById(id);
            return new TopicDetail(topic);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Topic not found with id: " + id);
        }
    }

    /**
     * Updates an existing topic.
     * @param topicUpdate the updated data for the topic
     * @param id          the id of the topic to be updated
     * @return the details of the updated topic
     */
    @Transactional
    public TopicDetail updateTopic(TopicUpdate topicUpdate, Long id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isPresent()) {
            var topic = topicOptional.get();

            if (!topic.getAuthor().getId().equals(topicUpdate.idUser())) {
                throw new IllegalStateException("Cannot change the author.");
            }

            if (!topic.getCourse().getId().equals(topicUpdate.idCourse())) {
                throw new IllegalStateException("Cannot change the course.");
            }

            topic.updateData(topicUpdate);
            return new TopicDetail(topic);
        } else {
            throw new EntityNotFoundException("Topic not found with id: " + id);
        }
    }

    /**
     * Deletes a topic by its id.
     * @param id the id of the topic to be deleted
     */
    @Transactional
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }


    /**
     * Retrieves all topics, optionally filtered by course name and year.
     * @param pageable the pagination information
     * @param courseName the optional course name to filter topics
     * @param year the optional year to filter topics
     * @return a page of topics
     */
    public Page<TopicList> getAll(Pageable pageable, String courseName, Integer year) {
        if (courseName != null && year != null) {
            return topicRepository.findByCourseNameAndYear(courseName, year, pageable).map(TopicList::new);
        } else {
            return topicRepository.findAll(pageable).map(TopicList::new);
        }
    }
}
